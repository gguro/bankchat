package common;

import java.io.Serializable;

public class Account implements Serializable{
	private String accountNo;
	private String password;
	private String name;
	private int accountType;
	private int interestRate;
	private int accountStaus;
	private int balance;
	

	public Account(String accountNo, String password, String name) {
		super();
		this.accountNo = accountNo;
		this.password = password;
		this.name = name;
		this.accountType = AccountType.ACCOUNT_TYPE_NORMAL;
		this.interestRate = 0;
		this.balance = 0;
	 
	}

	public Account(String accountNo, String password, String name, int accountType, int accountRate) {
		super();
		this.accountNo = accountNo;
		this.password = password;
		this.name = name;
		this.accountType = accountType;
		this.interestRate = accountRate;
		this.balance = 0;
	}
	
	public Account(String accountNo, String password, String name, int accountType, int accountRate, int balance) {
		super();
		this.accountNo = accountNo;
		this.password = password;
		this.name = name;
		this.accountType = accountType;
		this.interestRate = accountRate;
		this.balance = balance;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public int getAccountType() {
		return accountType;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public int getAccountStaus() {
		return accountStaus;
	}

	public void setAccountStaus(int accountStaus) {
		this.accountStaus = accountStaus;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	// -------------------------------------
	// 입금(잔액 +=입금액)
	public boolean deposit(int inmoney) {
		//logger.log("deposit : " + balance + " + " + inmoney);
		// 입력->계산->출력(리턴값)
		boolean status = false; // 입금상태
		balance += inmoney;
		if (balance >= inmoney)// 잔액이 입금액이상
			status = true;
		
		//logger.log("balance : " + balance);
		return status;
	}

	// 출금(잔액-=출금액)
	public boolean withdraw(int outmoney) {
		//logger.log("deposit : " + balance + " - " + outmoney);
		
		boolean status = false; // 출금상태
		// 잔액이 0 아니고 출금액이상
		if (balance != 0 && outmoney <= balance) {
			balance -= outmoney;
			status = true;
		}
		
		//logger.log("balance : " + balance);
		return status;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", name=" + name + ", accountType=" + accountType + ", accountRate="
				+ interestRate + ", accountStaus=" + accountStaus + ", balance=" + balance + "]";
	}
	
}
