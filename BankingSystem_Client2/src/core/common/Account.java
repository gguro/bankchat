package core.common;

public class Account {
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
	// �Ա�(�ܾ� +=�Աݾ�)
	public boolean deposit(int inmoney) {
		// �Է�->���->���(���ϰ�)
		boolean status = false; // �Աݻ���
		balance += inmoney;
		if (balance >= inmoney)// �ܾ��� �Աݾ��̻�
			status = true;
		return status;
	}

	// ���(�ܾ�-=��ݾ�)
	public boolean withdraw(int outmoney) {
		boolean status = false; // ��ݻ���
		// �ܾ��� 0 �ƴϰ� ��ݾ��̻�
		if (balance != 0 && outmoney <= balance) {
			balance -= outmoney;
			status = true;
		}
		return status;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", name=" + name + ", accountType=" + accountType + ", accountRate="
				+ interestRate + ", accountStaus=" + accountStaus + ", balance=" + balance + "]";
	}

	
	
	
}
