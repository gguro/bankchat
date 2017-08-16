package test;

import core.common.Account;

public class AccountTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String accountNo = "12341234";
		String password = "1234";
		String name = "Montana";
		
				
		Account acc = new Account(accountNo, password, name);
		acc.deposit(100);
		System.out.println(acc);
		System.out.println(acc.getBalance());
		
	}

}
