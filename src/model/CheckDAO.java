//lily

package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import databeans.CheckBean;

public class CheckDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public CheckDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
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
	
	public void creatCheck(CheckBean check) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (Customerid,fundid,executedate,executionamount,transactiontype,noofshares,state,employeename,balancebefore,balanceafter,shareprice) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        	
        	Date date1 = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(date1));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	pstmt.setString(1,check.getCID()+"");
        	pstmt.setInt(2, 0);
         	pstmt.setDate(3,null);
         	long amount=(long) ((Math.round(check.getAmount()*100.0)/100.0)*100);
        	pstmt.setDouble(4,amount);
        	pstmt.setString(5,"check");
        	pstmt.setInt(6, 0);
        	pstmt.setString(7,"pending");
        	pstmt.setString(8,"");
        	 
           	long balanceb4=(long) ((Math.round(check.getCash()*100.0)/100.0)*100);
      		
        	pstmt.setDouble(9,balanceb4);
        	pstmt.setDouble(10,0);
        	pstmt.setDouble(11,0);
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	
        	pstmt.close();
        	releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	public void deposit_check(CheckBean check) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (Customerid,fundid,executedate,executionamount,transactiontype,noofshares,state,employeename,balancebefore,balanceafter,shareprice) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        	
        	pstmt.setString(1,check.getCID()+"");
        	pstmt.setInt(2, 0);
         	pstmt.setDate(3,null);
         	long amount=(long) ((Math.round(check.getAmount()*100.0)/100.0)*100);
        	pstmt.setDouble(4,amount);
        	pstmt.setString(5,"deposit");
        	pstmt.setInt(6, 0);
        	pstmt.setString(7,"pending");
        	pstmt.setString(8,"");
        	 
           	long balanceb4=(long) (check.getCash());
      		
        	pstmt.setDouble(9,balanceb4);
        	pstmt.setDouble(10,0);
        	pstmt.setDouble(11,0);
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	
        	pstmt.close();
        	releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
}
