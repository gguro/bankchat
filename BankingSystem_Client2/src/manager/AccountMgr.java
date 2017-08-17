package manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import common.Account;
import exception.BMSException;

public class AccountMgr {
	Map<String, Account> accMap;
	Properties prop;

	public AccountMgr() {
		super();
		// TODO Auto-generated constructor stub
		accMap = new HashMap<String, Account>();
	}
	
	public Account getAccount(String accountNo) {
		return accMap.get(accountNo);
	}
	
	public Set <String> getAllAccountNo () {
		return accMap.keySet();
	}

	public List<Account> getAllAccounts() {
		return new ArrayList(accMap.values());
	}
	
	public boolean addAccount(Account acc) throws BMSException {

		if(accMap.containsKey(acc.getAccountNo())) {
			throw new BMSException("WRN : Duplicate account number!");
		} 
		
		accMap.put(acc.getAccountNo(), acc);
		
		return true;
	}
	
	public boolean removeAccount(String accountNo) throws BMSException {
		if(accMap.containsKey(accountNo)) {
			accMap.remove(accountNo);
		} else {
			throw new BMSException("WRN : Invalid account! Remove failed.");
		}
		
		return true;
	}
	
	public boolean loginAccount(String accountNo, String password) throws BMSException{
		if(!accMap.containsKey(accountNo)) {
			throw new BMSException("WRN : Invalid account! Login failed");
		}
		
		if(accMap.get(accountNo).getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean depositAccount(String accountNo, int amount) throws BMSException {
		if(!accMap.containsKey(accountNo)) {
			throw new BMSException("WRN: Invaild account! Deposit failed");
		}
		return accMap.get(accountNo).deposit(amount);
	}
	
	public boolean withdrawAccount(String accountNo, int amount) throws BMSException {
		if(!accMap.containsKey(accountNo)) {
			throw new BMSException("WRN : Invaild account! Withdraw failed");
		}
		
		return accMap.get(accountNo).withdraw(amount);
	}
	
	public boolean transferAccount(String accountNo, String to, int amount) throws BMSException {
		if(!accMap.containsKey(accountNo) || !accMap.containsKey(to)) {
			throw new BMSException("WRN : Invalid account! Transfer failed");
		}
		
		if(accMap.get(accountNo).getBalance() < amount) {
			throw new BMSException("WRN : Not enough money");
		}
		
		accMap.get(accountNo).withdraw(amount);
		accMap.get(to).deposit(amount);
		
		return true;
	}
	
	//계좌정보를 파일에 저장
	public boolean saveAccountInfo() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("accountInfo.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(accMap);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//계좌정보를 파일에서 불러옴
	public boolean loadAccountInfo() {
		
		FileInputStream fis;
		try {
			fis = new FileInputStream("accountInfo.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			accMap = (HashMap<String, Account>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AccountMgr [accMap=" + accMap + "]";
	}
	
	

	/*
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
	*/
	
}
