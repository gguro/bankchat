package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import core.common.Transaction;
import core.server.ServerProperties;
import core.server.TransactionMgr;

public class TransactionManagerTest {
	public static void main(String[] args) {
		ServerProperties props = new ServerProperties();
		try {
			props.loadProperties();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TransactionMgr tmgr = new TransactionMgr(props);
		
		//System.out.println(tmgr.getList());
		String accountNo = "22222222";
		
		Transaction trans = new Transaction(accountNo, "",  accountNo, 100);
		tmgr.addTrans(accountNo, trans);
		trans = new Transaction(accountNo, "", accountNo, 200);
		tmgr.addTrans(accountNo, trans);
		tmgr.saveTransactionInfo(props);
		
		try {
			props.saveProperties();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
