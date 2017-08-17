package test;

import java.util.Set;

import common.Account;
import exception.BMSException;
import manager.AccountMgr;

public class AccountManagerTest {
	public static void main(String [] args) {
		
		AccountMgr accMgr = new AccountMgr();
		accMgr.loadAccountInfo();
		
		Set <String> accountList = accMgr.getAllAccountNo();
		
		for(String accNo : accountList) {
			System.out.println(">> AccountNo : " + accNo);
			Account acc = accMgr.getAccount(accNo);
			System.out.println(acc.toString());
		}
		
		String accountNo = "11111111";
		String password = "1234";
		String name = "Customer-2";
				
		Account acc = new Account(accountNo, password, name);
		
		try {
			accMgr.addAccount(acc);

		} catch (BMSException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	
		try {
			accMgr.depositAccount(accountNo, 200);
			accMgr.withdrawAccount(accountNo, 100);
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		System.out.println(accMgr);
		
		accMgr.saveAccountInfo();
		
		
	}
}
