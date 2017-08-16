package test;

import core.common.Transaction;

public class TransactionTest {
	public static void main(String [] args) {
		Transaction ts = new Transaction("12341234", null, "12341234", 100);
		System.out.println(ts);
	}
}
