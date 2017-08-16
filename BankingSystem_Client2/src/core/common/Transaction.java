package core.common;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction implements Serializable {
	String accountNo;	
	String from; 
	String to;
	int amount;
	LocalDate date;
	LocalTime time;
	
	public Transaction(String accountNo, String from, String to, int amount) {
		super();
		this.accountNo = accountNo;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.date = LocalDate.now();
		this.time = LocalTime.now();
	}
	
	public Transaction(String accountNo, String from, String to, int amount, LocalDate date, LocalTime time) {
		super();
		this.accountNo = accountNo;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.date = date;
		this.time = time;
	}


	@Override
	public String toString() {
		return "[accountNo=" + accountNo + ", from=" + from + ", to=" + to + ", amount=" + amount
				+ ", date=" + date + ", time=" + time + "]";
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getAccountNo() {
		return accountNo;
	}

	
	
}
