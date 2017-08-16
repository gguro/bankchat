package test;

import core.common.Account;
import server.ServerMgr;

public class ServerTest {
	public static void main(String[] args) {
		ServerMgr smgr = new ServerMgr();
		
		String accountNo = "12341234";
		
		smgr.deposit(accountNo, 200);
		smgr.deposit(accountNo, 100);
		
		smgr.shutDown();
		
		
	}
}
