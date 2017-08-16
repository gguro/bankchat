package core.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import core.common.Account;
import exception.BMSException;

public class AccountMgr {
	Map<String, Account> accMap;
	Properties prop;

	public AccountMgr() {
		super();
		// TODO Auto-generated constructor stub
		accMap = new HashMap<String, Account>();
	}
	
	public AccountMgr(Properties prop) {
		this.prop = prop;
		accMap = new HashMap<String, Account>();
		
		if(loadAccountInfo()) {
			
		} 
	}
	
	public Account getAccount(String accountNo) {
		return accMap.get(accountNo);
	}
	
	public boolean addAccount(Account acc) throws BMSException {

		if(accMap.containsKey(acc.getAccountNo())) {
			throw new BMSException("WRN : Duplicate account number!");
		} 
		
		Account result = accMap.put(acc.getAccountNo(), acc);
		
		return result != null;
	}
	
	public boolean depositAccount(String accountNo, int amount) throws BMSException {
		if(!accMap.containsKey(accountNo)) {
			throw new BMSException("WRN: Invaild account!");
		}
		return accMap.get(accountNo).deposit(amount);
	}
	
	public boolean withdrawAccount(String accountNo, int amount) throws BMSException {
		if(!accMap.containsKey(accountNo)) {
			throw new BMSException("WRN : Invaild account!");
		}
		
		return accMap.get(accountNo).withdraw(amount);
	}
	
	public boolean saveAccountInfo() {
		System.out.println("-- Account 정보 저장 ");
		
		String accountIDs = accMap.keySet().toString();
		prop.setProperty("accountIDs", accountIDs);
		
		for(Account acc : accMap.values()) {
			prop.setProperty("accounts." + acc.getAccountNo()+".accountNo", acc.getAccountNo());
			prop.setProperty("accounts." + acc.getAccountNo()+".password", acc.getPassword());
			prop.setProperty("accounts." + acc.getAccountNo()+".name", acc.getName());
			prop.setProperty("accounts." + acc.getAccountNo()+".type", ""+acc.getAccountType());
			prop.setProperty("accounts." + acc.getAccountNo()+".rate", ""+acc.getInterestRate());
			prop.setProperty("accounts." + acc.getAccountNo()+".balance", ""+acc.getBalance());
		}
		
		System.out.println("-- Account 저장 완료 ");
		return true;
	}
	
	public boolean loadAccountInfo() {
		boolean result = false;
		String accountIDs = prop.getProperty("accountIDs");
		if(accountIDs != null) {
			StringTokenizer  stk = new StringTokenizer (accountIDs, "[], ");
			
			System.out.println("-- Account 정보 로드 ");
			while(stk.hasMoreTokens()) {
				String accountNo = stk.nextToken();
				try {
					String password = prop.getProperty("accounts." + accountNo+".password");
					String name = prop.getProperty("accounts." + accountNo+".name");
					int accountType = Integer.parseInt(prop.getProperty("accounts." + accountNo+".type"));
					int interestRate = Integer.parseInt(prop.getProperty("accounts." + accountNo+".rate"));
					int balance = Integer.parseInt(prop.getProperty("accounts." + accountNo+".balance"));
					
					Account acc = new Account(accountNo, password, name, accountType, interestRate, balance);
					addAccount(acc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		}
		
		
		System.out.println("-- Account 등록 완료 ");
		System.out.println();
		
		return result;
	}
}
