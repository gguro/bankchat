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
		
		logger.log(">> ��ŷ ���� ���� " + LocalDate.now());
		
		try {
			logger.log("-- ���� ������ �ε����Դϴ�.");
			props.loadProperties();
			logger.log("-- ���� ������ �ε��Ͽ�����.");
		} catch (FileNotFoundException e) {
			System.out.println("���� ������ ã�� �� �����ϴ�. ���� ����ϴ�.");
			props = new ServerProperties();
		} catch (IOException e) {
			System.out.println("���� ������ �д� �� �����߽��ϴ�. ���� ����ϴ�.");
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
