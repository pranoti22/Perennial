import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaveData {
	 ResultSet rs;
	 Statement statement;
	 PreparedStatement ps;
	 static Connection con;
	static 
	{
		 try{
	            Class.forName("org.postgresql.Driver");
	            con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/bank","postgres","1234");
	            con.setAutoCommit(false);
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		 

	}
	
	public String addAccount(int acc,String address,String name,int amount) {
		 try{
	            
	            String sql="insert into accounts (acc_no,addr,name,balance) values(?,?,?,?)";
	             ps = con.prepareStatement(sql);
	                        
	            ps.setInt(1,acc);
	            ps.setString(2,address);
	            ps.setString(3,name);
	            ps.setInt(4,amount);
	            
	            ps.executeUpdate();
	            commit();
	            }catch(Exception e) {System.out.println(e);}
		 return "data added successfully!";
	}
	public String deleteAccount(int acc) {
		 try{
	            
	            statement = con.createStatement();
				String query = "delete from accounts WHERE acc_no = " + acc;
				statement.executeQuery(query);
				commit();
	            }catch(Exception e) {}
		 return "data deleted successfully!";
	}
	public String updateBalc(int acc,int current) {
		 try{
	           
	            
	            statement = con.createStatement();
				String query = "UPDATE accounts SET balance = " + current + " WHERE acc_no = " + acc;
				statement.executeQuery(query);
			
	           }catch(Exception e) {}
		 return "data updated successfully!";
	}
	
	public String updateTransaction(int acc,int amt,Date date,String type) {
		
		 try{
	        
	            String sql="insert into transactions (accno,amount,txnstype,date) values(?,?,?,?)";
	             ps = con.prepareStatement(sql);
	                        
	            ps.setInt(1,acc);
	            ps.setInt(2,amt);
	            ps.setString(3,type);
	            ps.setString(4,date.toString());
	            
	            ps.executeUpdate();
	            
	            }catch(Exception e) {System.out.println(e);}
		 return "data updated successfully!";
	}
	public ArrayList<Transaction> retriveTxns(int acc) {
		ArrayList<Transaction> view=new ArrayList<Transaction>();
		 try{
	           
	            statement = con.createStatement();
				String query = "SELECT * from transactions  WHERE accno = " + acc;
				rs=statement.executeQuery(query);
				while(rs.next())
				{
					
					Date date1=new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(rs.getString("date"));
					Transaction t=new Transaction(date1,rs.getString("txnstype"),rs.getInt("amount"));
					view.add(t);
					
				}
				
				
				
	           }catch(Exception e) {System.out.println(e);}
		 return view;
	}
	
	public int getAccno()
	{
		int accno=0;
		 try{
	            
	            statement = con.createStatement();
				String query = "select acc_no from accounts order by acc_no desc limit 1 ";
				rs=statement.executeQuery(query);
			while(rs.next()) {
				accno=rs.getInt("acc_no");
			
				
		 }
			return accno;
		 }catch(Exception e) {System.out.println(e);}
		 
		return 0;
	
	}
	public int searchAccount(int accno) {
		int acc = 0;
		try {
			statement=con.createStatement();
			String query = "select acc_no from accounts where acc_no = " + accno;
			rs = statement.executeQuery(query);
			if(rs.next())
				acc = rs.getInt("acc_no");
			if(acc == accno)
				return acc;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	public int getBalance(int accno) {
		int balance = 0;
		try {	
			String query = "select balance from accounts where acc_no = " + accno;
			rs = statement.executeQuery(query);
			
			if(rs.next())
				balance = rs.getInt("balance");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return balance;
	}
	public boolean commit()
	{
		 try{ 
			 con.commit();
			 
	           }catch(Exception e) {e.printStackTrace();}
		
		return true;
	}
	public boolean rollback()
	{
		 try{ 
			 con.rollback();
	           }catch(Exception e) {e.printStackTrace();}
		return true;
		
	}
	
	
	}


