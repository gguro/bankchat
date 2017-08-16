package core.server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import core.common.Transaction;

public class TransactionMgr {
	private Map<String, List<Transaction>> transMap;

	public TransactionMgr() {
		super();
		//this.transList = new HashMap<String, List<Transaction>>();
		transMap = new HashMap<String, List<Transaction>>();
	}
	
	
	
	public TransactionMgr(Properties prop) {
		transMap = new HashMap<String, List<Transaction>>();
		
		loadTransactionInfo(prop);
	}
	
	public boolean loadTransactionInfo(Properties prop) {
		boolean result = false;
		String accountIDs = prop.getProperty("accountIDs");
		if(accountIDs != null) {
			StringTokenizer  stk = new StringTokenizer (accountIDs, "[], ");
			
			System.out.println("-- Transaction 정보 로드 ");
			while(stk.hasMoreTokens()) {
				String accountNo = stk.nextToken();
				try {
					String strList = prop.getProperty("trans." + accountNo);
					StringTokenizer stk2 = new StringTokenizer(strList, "[]");
					while(stk2.hasMoreTokens()) {
						//System.out.println(stk2.nextToken());
						String strTrans = stk2.nextToken();
						if(strTrans.length() < 3) 
							continue;
						//System.out.println(strTrans);
						String [] pairs = strTrans.split(", ");
						Map <String, String> tempMap = new HashMap<String, String>();
						
						for(int i=0; i<pairs.length; i++) {
							//System.out.println(pairs[i]);
							String[] pair = pairs[i].split("=");
							if(pair.length == 1) {
								tempMap.put(pair[0], "");
							} else {
								tempMap.put(pair[0], pair[1]);
							}
						}
						
						LocalDate date = LocalDate.parse(tempMap.get("date"));
						//System.out.println("date = " + date);
						LocalTime time = LocalTime.parse(tempMap.get("time"));
						//System.out.println("time = " + time);
						
						
						Transaction trans = new Transaction(tempMap.get("accountNo"), 
														tempMap.get("from"), tempMap.get("to"), 
														Integer.parseInt(tempMap.get("amount")), 
														date, time);
						addTrans(accountNo, trans);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		}
		
		return result;
	}
	
	public boolean saveTransactionInfo (Properties prop) {
		boolean result = false;
		System.out.println("-- Transaction 정보 저장 ");
		
		String accountIDs = transMap.keySet().toString();
		//prop.setProperty("accountIDs", accountIDs);
		
		for(String accountNo : transMap.keySet()) {
			List<Transaction> tlist = transMap.get(accountNo);
			
			String strList = tlist.toString();
			prop.setProperty("trans."+accountNo, strList);
		}
		
		System.out.println("-- Transaction 저장 완료 ");
		
		return result;
	}
	
	public List<Transaction> getList(String accountNo) {
		return transMap.get(accountNo);
	}
	
	public boolean removeTrans(String accountNo) {
		transMap.remove(accountNo);
		return true;
	}
	
	public boolean addTrans(String accountNo, Transaction trans) {
		
		if(transMap.containsKey(accountNo)) {
			transMap.get(accountNo).add(trans);
		} else {
			List<Transaction> tList = new ArrayList<Transaction> ();
			transMap.put(accountNo, tList);
			transMap.get(accountNo).add(trans);
		}
		
		return true;
	}
	
	
	
	
	
}
