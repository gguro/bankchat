package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import common.Transaction;
import exception.BMSException;
import server.ServerProperties;
import server.TransactionMgr;

public class TransactionManagerTest {
	public static void main(String[] args) {
		
		
		TransactionMgr tmgr = new TransactionMgr();
		tmgr.loadTransactionInfo();
		
		Set <String> accountList = tmgr.getAllAcountNo();
		
		for(String acc : accountList) {
			System.out.println(">> AccountNo : " + acc);
			List<Transaction> tl;
			
			try {
				tl = tmgr.getList(acc);
				for(Transaction ts : tl) {
					System.out.println(ts.toString());
				}
				
			} catch (BMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.println(tmgr.getList());
		String accountNo = "22222222";
		
		Transaction trans;
		try {
			trans = new Transaction(accountNo, "",  accountNo, 100);
			tmgr.addTrans(accountNo, trans);
			trans = new Transaction(accountNo, "", accountNo, 200);
			tmgr.addTrans(accountNo, trans);
			
		} catch (BMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmgr.saveTransactionInfo();
		
		
	}
}
