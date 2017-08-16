package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import core.common.Transaction;
import core.server.ServerProperties;
import core.server.TransactionMgr;

public class TransactionManagerTest {
	public static void main(String[] args) {
		
		
		TransactionMgr tmgr = new TransactionMgr();
		tmgr.loadTransactionInfo();
		
		Set <String> accountList = tmgr.getAllAcountNo();
		
		for(String acc : accountList) {
			System.out.println(">> AccountNo : " + acc);
			List<Transaction> tl = tmgr.getList(acc);
			for(Transaction ts : tl) {
				System.out.println(ts.toString());
			}
		}
		
		
		//System.out.println(tmgr.getList());
		String accountNo = "22222222";
		
		Transaction trans = new Transaction(accountNo, "",  accountNo, 100);
		tmgr.addTrans(accountNo, trans);
		trans = new Transaction(accountNo, "", accountNo, 200);
		tmgr.addTrans(accountNo, trans);
		tmgr.saveTransactionInfo();
		
	}
}
