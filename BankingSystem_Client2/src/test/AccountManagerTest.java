package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import core.common.Account;
import core.server.AccountMgr;
import core.server.ServerProperties;
import exception.BMSException;

public class AccountManagerTest {
	public static void main(String [] args) {
		
		ServerProperties props = new ServerProperties();
		try {
			props.loadProperties();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		AccountMgr accMgr = new AccountMgr(props);
		
		String accountNo = "12341234";
		String password = "1234";
		String name = "Customer-2";
				
		Account acc = new Account(accountNo, password, name);
		
		try {
			accMgr.addAccount(acc);
			accMgr.depositAccount(accountNo, 200);
			accMgr.withdrawAccount(accountNo, 100);
		} catch (BMSException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	
		System.out.println(accMgr);
		
		accMgr.saveAccountInfo();
		
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
