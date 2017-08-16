package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import core.common.Logger;
import core.server.AccountMgr;
import core.server.ServerProperties;
import core.server.TransactionMgr;
import exception.BMSException;

public class ServerMgr {
	ServerProperties props;
	AccountMgr accMgr;
	TransactionMgr	transMgr;
	
	Logger logger;
	
	public ServerMgr() {
		super();
		// TODO Auto-generated constructor stub
		props = new ServerProperties();
		
		logger = Logger.getInstance();
		
		logger.log(">> 뱅킹 서버 시작 " + LocalDate.now());
		
		try {
			logger.log("-- 설정 파일을 로드중입니다.");
			props.loadProperties();
			logger.log("-- 설정 파일을 로드하였습니.");
		} catch (FileNotFoundException e) {
			System.out.println("설정 파일을 찾을 수 없습니다. 새로 만듭니다.");
			props = new ServerProperties();
		} catch (IOException e) {
			System.out.println("설정 파일을 읽는 데 실패했습니다. 새로 만듭니다.");
			props = new ServerProperties();
		}
		
		try {
			accMgr = new AccountMgr(props);
			transMgr = new TransactionMgr(props);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public boolean withdraw(String accountNo, int amount) {
		try {
			accMgr.withdrawAccount(accountNo, amount);
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	public boolean deposit(String accountNo, int amount) {
		try {
			accMgr.depositAccount(accountNo, amount);
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}
		return true;
	}
	
	
	public void shutDown() {
		accMgr.saveAccountInfo();
		transMgr.saveTransactionInfo(props);
		
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

}
