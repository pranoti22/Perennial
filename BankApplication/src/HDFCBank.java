import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HDFCBank implements Bank {
	private List<Account> accountList = new ArrayList<Account>();
	private int accno=11111;
	SaveData sd=new SaveData();
	@Override
	public String openAccount(String name, String address, int amount) throws InsufficientBalanceException {
		String ans;
		if(amount < Bank.MIN_BALANCE)
			throw new InsufficientBalanceException("Min balance required to open Account is "+Bank.MIN_BALANCE);
		int acc=sd.getAccno();
		if(acc==0)
		{
		 ans=sd.addAccount(accno, address, name, amount);
		}
		else{ ans=sd.addAccount(++acc, address, name, amount);}
	            
		
		return ans; //"HDFC Thanks u for opening acc new accno is "+acct.getAccno();
	}

	@Override
	public int withdraw(int accno, int amount) throws InsufficientBalanceException, InvalidAccountException {
		int acct = sd.searchAccount(accno);
		if(acct==0)
			throw new InvalidAccountException();
		int oldBalance = sd.getBalance(acct);
		int currentbal=oldBalance-amount;
		if(oldBalance-amount > Bank.MIN_BALANCE) {
			System.out.println(sd.updateBalc(acct, currentbal));
			sd.updateTransaction(acct,amount,new Date(),"DEBIT");
			sd.commit();
		}else {
			throw new InsufficientBalanceException("Min balance requires is"+Bank.MIN_BALANCE);
		}
		return sd.getBalance(acct);
	}

	private Account searchAccount(int accno2) {
		// TODO Auto-generated method stub
		for(Account acct : accountList) {
			if(acct.getAccno()==accno2)
				return acct;
		}
		return null;
	}

	@Override
	public int deposit(int accno, int amount) throws InvalidAccountException {
		int acct = sd.searchAccount(accno);
		if(acct==0)
			throw new InvalidAccountException();
		int oldBalance = sd.getBalance(acct);
		System.out.println(sd.updateBalc(acct, oldBalance+amount));
		sd.updateTransaction(acct,amount, new Date(),"CREDIT");
		sd.commit();
		return sd.getBalance(acct);
	}

	@Override
	public int transfer(int accfrom, int accto, int amount)
			throws InvalidAccountException, InsufficientBalanceException { 
		int acct = sd.searchAccount(accfrom);
		if(acct==0)
			throw new InvalidAccountException();
		int oldBalance = sd.getBalance(acct);
		int currentbal=oldBalance-amount;
		if(oldBalance-amount > Bank.MIN_BALANCE) {
			System.out.println(sd.updateBalc(acct, currentbal));
			sd.updateTransaction(acct,amount,new Date(),"DEBIT");
		}else {
			throw new InsufficientBalanceException("Min balance requires is"+Bank.MIN_BALANCE);
		}
		try {
		int acct2 = sd.searchAccount(accto);
		if(acct2==0)
			throw new InvalidAccountException();
		int oldBalance2 = sd.getBalance(acct2);
		System.out.println(sd.updateBalc(acct2, oldBalance2+amount));
		sd.updateTransaction(acct2,amount, new Date(),"CREDIT");
		sd.commit();
		}catch(Exception e) {System.out.println(" deposite Account not found! "); sd.rollback();}
		
		
		
		/*int newbalance = withdraw(accfrom,amount);
		try {
			deposit(accto,amount);			
		}catch(Exception ex) {
			sd.rollback();			
		}
		sd.commit();*/
		return sd.getBalance(acct);

	}

	@Override
	public int closeAccount(int accno) throws InvalidAccountException {
		int acct = sd.searchAccount(accno);
		if(acct==0)
			throw new InvalidAccountException();
		System.out.println(sd.deleteAccount(acct));
		return sd.getBalance(acct);
	}

	@Override
	public String printRecentTransaction(int accno, int notxns) throws InvalidAccountException {
		// TODO Auto-generated method stub
		int acct = sd.searchAccount(accno);
	    if(acct==0) throw new InvalidAccountException();
		ArrayList<Transaction> txns = sd.retriveTxns(accno);
		Collections.sort(txns,(txns1,txns2)->{return txns2.getDate().compareTo(txns1.getDate());});
		StringBuilder builder = new StringBuilder();
		for(int i=0; i < notxns; i++) {
			builder.append(txns.get(i).toString());
		}
		return  builder.toString();
	}

}
