package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import common.Account;
import common.Logger;
import common.Transaction;
import dialog.ServerFrame2;
import exception.BMSException;

public class ServerMgr {
	ServerProperties props;
	AccountMgr accMgr;
	TransactionMgr	transMgr;
	Server server;
	ServerFrame2 sf2;
	Logger logger;
	
	public ServerMgr(ServerFrame2 sf2) {
		super();
		// TODO Auto-generated constructor stub
		props = new ServerProperties();
		
		logger = Logger.getInstance();
		
		this.sf2 = sf2;
		
		logger.log(">> 뱅킹 서버 시작 " + LocalDate.now());
		
		try {
			logger.log("-- 설정 파일을 로드중입니다.");
			props.loadProperties();
			
		} catch (FileNotFoundException e) {
			logger.log("설정 파일을 찾을 수 없습니다. 새로 만듭니다.");
			props = new ServerProperties();
		} catch (IOException e) {
			logger.log("설정 파일을 읽는 데 실패했습니다. 새로 만듭니다.");
			props = new ServerProperties();
		}
		
		try {
			accMgr = new AccountMgr();
			transMgr = new TransactionMgr();
			
			logger.log("load account info");
			accMgr.loadAccountInfo();
			logger.log("account info loaded.");
			logger.log("load transaction info");
			transMgr.loadTransactionInfo();
			logger.log("transaction info loaded");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		logger.log("-- 설정 파일을 로드하였습니다.");
		server = new Server(this);
		
	}
	
	public void startServer() {
		server.start();
	}
		
	public boolean login(String accountNo, String password) throws BMSException {
		logger.log("Login : " + accountNo);
		
		boolean result = false;
		
		result = accMgr.loginAccount(accountNo, password);
		
		return result;
	}
	
	public boolean create(Account account) throws BMSException {
		logger.log(">> 계좌 생성 : " + account.getAccountNo() +", " + account.getName());
		transMgr.createTrans(account.getAccountNo());
		
		
		return accMgr.addAccount(account);

	}
	
	public Account getAccount(String accountNo) {
		return accMgr.getAccount(accountNo);
	}
	
	
	public boolean withdraw(String accountNo, int amount) throws BMSException {
		logger.log(">> 출금 : " + accountNo + ", " + amount);
		
		boolean result = accMgr.withdrawAccount(accountNo, amount);
		if (result == true) {
			transMgr.addTrans(accountNo, new Transaction(accountNo, accountNo, "", amount));
		}
		
		return result;
	}
	
	public boolean deposit(String accountNo, int amount) throws BMSException {
		logger.log(">> 입금 : " + accountNo + ", " + amount);
		
		boolean result = accMgr.depositAccount(accountNo, amount);
		if(result == true) {
			transMgr.addTrans(accountNo, new Transaction(accountNo, "", accountNo, amount));
		}
		
		return result;
	}
	
	public boolean transfer(String accountNo, String to, int amount) throws BMSException {
		logger.log(">> 이체 : " + accountNo + ", " + to + ", " + amount);
		
		
		boolean result = accMgr.transferAccount(accountNo, to, amount);
		if(result == true) {
			transMgr.addTrans(accountNo, new Transaction(accountNo, accountNo, to, amount));
			transMgr.addTrans(to, new Transaction(to, accountNo, to, amount));
		}
		return result;
	}
	
	public boolean remove(String accountNo) throws BMSException {
		logger.log(">> 계정삭제 : " + accountNo);
		
		boolean result = accMgr.removeAccount(accountNo); 
		
		return result;
	}
	
	public void shutDown() {
		accMgr.saveAccountInfo();
		transMgr.saveTransactionInfo();
		
		try {
			props.saveProperties();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Transaction> getTransaction(String account) throws BMSException {
		return transMgr.getList(account);
	}

	public Server getServer() {
		// TODO Auto-generated method stub
		return server;
		
	}
	
	public ServerFrame2 getFrame() {
		return sf2;
	}
}
