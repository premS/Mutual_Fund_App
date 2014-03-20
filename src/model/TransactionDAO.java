package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import databeans.CheckBean;
import databeans.Transaction;

public class TransactionDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public TransactionDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = tableName;
		
		if (!tableExists()) createTable();
	}
	
	private synchronized Connection getConnection() throws MyDAOException {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}
	
	public void setcheckexecute(Date d,long cash,String state,int tid) throws ParseException{
        try {
    		Connection con = null;
        		con = getConnection();
			
        	String updateTableSQL = "UPDATE "+tableName+" set executedate=?,balanceafter=?,state=? WHERE transactionid= ?";
        	PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement(updateTableSQL);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
	        	Date parsed = format.parse(format.format(d));
	            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
	        	
			preparedStatement.setDate(1,sqldate);
            preparedStatement.setLong(2, cash);
            preparedStatement.setString(3, state);
            preparedStatement.setInt(4, tid);
        	preparedStatement .executeUpdate();
        	preparedStatement.close();
        	releaseConnection(con);    
        }		
	
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
			
		}
	}
	
	public void setbuyexecute(Date d,long cash,String state,long nsh,long sp,int tid) throws ParseException{
        try {
    		Connection con = null;
        		con = getConnection();
			
        	String updateTableSQL = "UPDATE "+tableName+" set balanceafter=?,state=?,noofshares=?,shareprice=?,executedate=? WHERE transactionid= ?";
        	PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement(updateTableSQL);
			
            preparedStatement.setLong(1, cash);
            preparedStatement.setString(2, state);
            preparedStatement.setLong(3, nsh);
            preparedStatement.setLong(4, sp);
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(d));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	preparedStatement.setDate(5, sqldate);
            preparedStatement.setInt(6, tid);
        	preparedStatement .executeUpdate();
        	preparedStatement.close();
        	releaseConnection(con);    
        }		
	
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
			
		}
	}
	public void setsellexecute(Date d,long cash,String state,long amount,long sp,int tid) throws ParseException{
        try {
    		Connection con = null;
        		con = getConnection();
			
        	String updateTableSQL = "UPDATE "+tableName+" set balanceafter=?,state=?,executionamount=?,shareprice=?,executedate=? WHERE transactionid= ?";
        	PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement(updateTableSQL);
			
            preparedStatement.setLong(1, cash);
            preparedStatement.setString(2, state);
            preparedStatement.setLong(3, amount);
            preparedStatement.setLong(4, sp);
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(d));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	preparedStatement.setDate(5, sqldate);
            preparedStatement.setInt(6, tid);
        	preparedStatement .executeUpdate();
        	preparedStatement.close();
        	releaseConnection(con);    
        }		
	
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
			
		}
	}
	private boolean tableExists() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	DatabaseMetaData metaData = con.getMetaData();
        	ResultSet rs = metaData.getTables(null, null, tableName, null);
        	
        	boolean answer = rs.next();
        	
        	rs.close();
        	releaseConnection(con);
        	
        	return answer;

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }

	private void createTable() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("create table "+tableName+" (transactionid int not null," +
            		"Customerid, fundid,executedate,amount,transactiontype,shares,state,employeename," +
            		" primary key(transactionid))");
            stmt.close();
        	
        	releaseConnection(con);

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }
	
	public ArrayList<Transaction> getpending()throws MyDAOException{
Connection con = null;
    	
    	ArrayList<Transaction> results = new ArrayList();
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("select * from " + tableName + " where state='pending' order by customerid, transactionid asc");
        	
        	Date date1 = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(date1));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()){
        		Transaction t = new Transaction();
        		t.setTransactionId(rs.getInt("transactionId"));
        		t.setFundId(rs.getInt("fundId"));
        		t.setCustomerId(rs.getInt("customerId"));
        		t.setDate(rs.getDate("executeDate"));
        		t.setAmount(rs.getLong("executionAmount"));
        		t.setOperation(rs.getString("transactionType"));
        		t.setNumberOfShares(rs.getLong("noOfShares"));
        		t.setState(rs.getString("state"));
        		t.setBalanceBefore(rs.getLong("balancebefore"));
        		t.setBalanceAfter(rs.getLong("balanceafter"));
        		t.setSharePrice(rs.getLong("shareprice"));
        		results.add(t);
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        	
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
        
		return results;
	
	} 
	public ArrayList<Transaction> read(int customerId) throws MyDAOException {
		Connection con = null;
    	
    	ArrayList<Transaction> results = new ArrayList();
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("select * from " + tableName + " where customerid =?");
        	
        	Date date1 = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(date1));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	pstmt.setInt(1,customerId);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()){
        		Transaction t = new Transaction();
        		t.setDate((rs.getDate("executeDate")));
        		t.setNumberOfShares(rs.getInt("noOfShares"));
        		t.setOperation(rs.getString("transactionType"));
        		t.setSharePrice(rs.getLong("Shareprice"));
        		//t.setPrice(rs.getLong("initialAmount"));
        		t.setState(rs.getString("state"));
        		t.setFundId(rs.getInt("fundId"));
        		t.setAmount(rs.getLong("executionAmount"));
        		results.add(t);
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        	
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
        
		return results;
	}
}
