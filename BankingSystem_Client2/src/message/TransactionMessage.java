package message;

import java.util.List;

import common.Transaction;

public class TransactionMessage extends Message {
	private String accountNo;
	private int totalCount;
	
	private List<Transaction> list;
	
	public TransactionMessage(String accountNo, int totalCount, List<Transaction> list) {
		this.accountNo = accountNo;
		this.totalCount = totalCount;
		this.list = list;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public List<Transaction> getList() {
		return list;
	}

	@Override
	public String toString() {
		return "TransactionMessage [accountNo=" + accountNo + ", totalCount=" + totalCount + ", list=" + list + "]";
	}
	
}
