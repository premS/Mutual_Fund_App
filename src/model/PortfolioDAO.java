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

import model.FundDAO;
import databeans.FundPrice;
import databeans.Portfolio;
import databeans.PortfolioBean;

public class PortfolioDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public PortfolioDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = tableName;
		
		
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
	
	public ArrayList<Portfolio> read(int customerId) throws MyDAOException{
		Connection con = null;
		ArrayList<Portfolio> portfolio = new ArrayList();
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE customerid=?");
        	pstmt.setInt(1,customerId);
        	ResultSet rs = pstmt.executeQuery();
    
        	while(rs.next()){
        		Portfolio p = new Portfolio();
        		p.setFundId(rs.getInt("fundid"));
        		p.setFundName(rs.getString("fundName"));
        		p.setCurrentHolding(rs.getInt("currentholding"));
        		//p.setBuyingPrice(rs.getDouble("buyingPrice"));
        		portfolio.add(p);
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        }catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
		
		
		return portfolio;
	}
	public Portfolio readByFundId(int fundId) throws MyDAOException{
		Connection con = null;
		
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE fundid=?");
        	pstmt.setInt(1,fundId);
        	ResultSet rs = pstmt.executeQuery();
        	Portfolio p=null;
        	
        	while(rs.next()){
        		 p = new Portfolio();
        		p.setFundId(rs.getInt("fundid"));
        		p.setFundName(rs.getString("fundName"));
        		p.setCurrentHolding(rs.getLong("currentholding"));
        		//p.setBuyingPrice(rs.getLong("buyingPrice"));
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        	return p;
        }catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
		
	
	}
	public Portfolio readByFundCId(int cid,int fundId) throws MyDAOException{
		Connection con = null;
		
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE fundid=? and customerid=?");
        	pstmt.setInt(1,fundId);
        	pstmt.setInt(2,cid);
        	ResultSet rs = pstmt.executeQuery();
        	Portfolio p=null;
        	
        	while(rs.next()){
        		 p = new Portfolio();
        		p.setFundId(rs.getInt("fundid"));
        		p.setFundName(rs.getString("fundName"));
        		p.setCurrentHolding(rs.getLong("currentholding"));
        		//p.setBuyingPrice(rs.getLong("buyingPrice"));
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        	return p;
        }catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
		
	
	}
	public void createBuy(int cid,int fid,long nsh) throws MyDAOException {
		Connection con = null;
		String temp=null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + "  VALUES (?,?,?,?)");
        	
        	pstmt.setInt(1,cid);
        	pstmt.setInt(2,fid);
        	pstmt.setString(3,"");
        	pstmt.setLong(4,nsh);
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	
        	pstmt.close();
        	releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
  }
	public void updateBuy(int cid,int fid,long nsh){
		  try {
	    		Connection con = null;
	        		con = getConnection();
				
	        	String updateTableSQL = "UPDATE "+tableName+" set currentholding=? WHERE customerid= ? and fundid=?";
	        	PreparedStatement preparedStatement;
					preparedStatement = con.prepareStatement(updateTableSQL);
				
	            preparedStatement.setLong(1, nsh);
	        	preparedStatement.setInt(2, cid);
	        	preparedStatement.setInt(3, fid);
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
	public String getPortfolio(int customerid,FundDAO fdao) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("select * from " + tableName + " where customerid= ?");
        
        	pstmt.setInt(1,customerid);
            ResultSet rs = pstmt.executeQuery();
        StringBuilder sb=new StringBuilder("[");	
        	while (rs.next()) {
        		int id=rs.getInt("fundid");
        		long fprice=fdao.getlatestfundprice(id);
        		double cp=(fprice*1.0)/100.0;
        sb.append("['"+id+"','"+rs.getString("fundname")+"','"+rs.getInt("currentholding")+"','"+cp+"'],");
        	}        
        	sb.append("]");
        	
        	pstmt.close();
        	releaseConnection(con);
        	System.out.println(sb.toString());
        	 return sb.toString();
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
       
	}
}
