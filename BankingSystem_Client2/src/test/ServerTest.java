package test;

import common.Account;
import exception.BMSException;
import server.ServerMgr;

public class ServerTest {
	public static void main(String[] args) {
		ServerMgr smgr = new ServerMgr(null);
		
		String accountNo = "12341234";
		
		try {
			smgr.deposit(accountNo, 200);
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try {
			smgr.deposit(accountNo, 100);
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		smgr.shutDown();
		
		
	}
}
