//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

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
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;









import databeans.FundBean;
import databeans.FundDisplay;
import databeans.User;
import databeans.Fund;
import databeans.FundPrice;



public class FundDAO  {
	
	
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	private String tableName1;
	
	public FundDAO(String jdbcDriver, String jdbcURL, String tableName,String tableName1) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = tableName;
		this.tableName1  = tableName1;
		
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

	public ArrayList<FundBean> listAllFund() throws MyDAOException {
		Connection con = null;
		try {
			con = getConnection();

			// PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " +
			// tableName1 + " WHERE fundid=?");
			// pstmt.setInt(1,fundid);

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
					+ tableName);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<FundBean> fundList = new ArrayList<FundBean>();
			FundBean fund =null;

			while (rs.next()) {
				fund = new FundBean();
				fund.setFundId(rs.getInt("fundId"));
				
				fund.setFundName(rs.getString("fundName"));
				System.out.println(fund.getFundName());
				fund.setFundSymbol(rs.getString("fundSymbol"));
				fundList.add(fund);
			}

			rs.close();
			pstmt.close();
			releaseConnection(con);
			return fundList;

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e2) { /* ignore */
			}
			throw new MyDAOException(e);
		}
	}

	@SuppressWarnings("null")
	public FundDisplay getDisplayFund(int fundId) throws MyDAOException {
		Connection con = null;
		try {
			con = getConnection();

			// PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " +
			// tableName1 + " WHERE fundid=?");
			// pstmt.setInt(1,fundid);
			System.out.println(fundId);
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
					+ tableName1+ " WHERE fundid=? ORDER BY pricedate DESC LIMIT 0,1" );
			pstmt.setString(1, String.valueOf(fundId));
			ResultSet rs = pstmt.executeQuery();
	//		ArrayList<FundPrice> fundList = new ArrayList<FundPrice>();
			FundDisplay fund =new FundDisplay();

			while (rs.next()) {
				fund.setFundPrice(rs.getLong("price")/100.00);
			
			}

			rs.close();
			pstmt.close();
			releaseConnection(con);
			return fund;

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e2) { /* ignore */
			}
			throw new MyDAOException(e);
		}
	}


	public String getFundName(int fundId) throws MyDAOException {
		Connection con = null;
		try {
			con = getConnection();

			// PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " +
			// tableName1 + " WHERE fundid=?");
			// pstmt.setInt(1,fundid);
			System.out.println(fundId);
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
					+ tableName+ " WHERE fundid=?" );
			pstmt.setString(1, String.valueOf(fundId));
			ResultSet rs = pstmt.executeQuery();
	//		ArrayList<FundPrice> fundList = new ArrayList<FundPrice>();
			String name=new String();
			while (rs.next()) {
				name=rs.getString("fundname");
			
			}
			
			rs.close();
			pstmt.close();
			releaseConnection(con);
			return name;

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e2) { /* ignore */
			}
			throw new MyDAOException(e);
		}
	}
	public String readById(int fundId) throws MyDAOException {
		Connection con = null;
		try {
			con = getConnection();

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
					+ tableName + " WHERE fundId=?");
			pstmt.setInt(1, fundId);
			ResultSet rs = pstmt.executeQuery();

			Fund fund = null;
			if (!rs.next()) {
				fund = null;
			} else {
				fund = new Fund();
				fund.setFundId(rs.getInt("fundid"));
				fund.setFundSymbol(rs.getString("fundsymbol"));
				fund.setFundName(rs.getString("fundName"));
			}

			rs.close();
			pstmt.close();
			releaseConnection(con);
			return fund.getFundName();

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e2) { /* ignore */
			}
			throw new MyDAOException(e);
		}
	}
public boolean checkfund_exists(String fn,String fs)throws MyDAOException{
	boolean flag=false;
	Connection con = null;
    try {
    	con = getConnection();//SELECT * FROM webapp.fund where fundname='Apple' or fundsymbol='GOOG';
    	PreparedStatement pstmt = con.prepareStatement("select * from " + tableName + " where fundname=? or fundsymbol=?");
    	
    	pstmt.setString(1,fn);
    	pstmt.setString(2,fs);
    	
    	ResultSet rs = pstmt.executeQuery();
    	
    	if (rs.next()) {
    		if(Integer.parseInt(rs.getString(1))>0)
    			flag=true;
    	}     	
    	rs.close();
    	pstmt.close();
    	releaseConnection(con);

    	pstmt.close();
    	releaseConnection(con);
    	
    } catch (Exception e) {
        try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
    	throw new MyDAOException(e);
    }
    return flag;
}
	public void createfundprice(FundPrice fp) throws MyDAOException {
		Connection con = null;
		String temp=null;
        try {
        	con = getConnection();
        	con.setAutoCommit(false);
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName1 + "  VALUES (?,?,?)");
        	
        	pstmt.setInt(1,fp.getFundId());
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");	
        	Date parsed = format.parse(format.format(fp.getPriceDate()));
            java.sql.Date sqldate = new java.sql.Date(parsed.getTime());
        	pstmt.setDate(2,sqldate);
        	pstmt.setLong(3,fp.getFundPrice());
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	con.commit();
        	pstmt.close();
        	releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) {con.rollback();con.close();} } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
  }
	public void createfunds(String fn,String ft) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	con.setAutoCommit(false);
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (fundname,fundsymbol) VALUES (?,?)");
        	
        	pstmt.setString(1,fn);
        	pstmt.setString(2,ft);
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new Exception("Insert updated "+count+" rows");
        	con.commit();
        	pstmt.close();
        	releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) {con.rollback();con.close();} } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
  }

	public Fund read(String fundname) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE fundName=?");
        	pstmt.setString(1,fundname);
        	ResultSet rs = pstmt.executeQuery();
        	
        	Fund fund=null;
        	if (!rs.next()) {
        		fund = null;
        	} else {
        		fund = new Fund();
        		fund.setFundId(rs.getInt("fundid"));
        		fund.setFundSymbol(rs.getString("fundsymbol"));
        		fund.setFundName(rs.getString("fundName"));
        		}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return fund;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public String getfundnames() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT fundname FROM " + tableName );
        	ResultSet rs = pstmt.executeQuery();
        	
        	StringBuilder sb=new StringBuilder();
        	while (rs.next()) {        	 
        		sb.append("'"+rs.getString("fundname")+"',");
        	}
        	sb.substring(0, sb.length()-1);
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return sb.toString();
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public long getlatestfundprice(int fundid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT price FROM " + tableName1 + " WHERE fundid=? order by pricedate DESC Limit 0,1");
        	pstmt.setInt(1,fundid);
        	ResultSet rs = pstmt.executeQuery();
        	long price=0;
        	while (rs.next()) {        	 
        		price=rs.getLong("price");
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return price;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
	public Date getlatestfunddate() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT pricedate FROM " + tableName1 + " order by pricedate DESC Limit 0,1");
        	ResultSet rs = pstmt.executeQuery();
        	Date priced=new Date();
        	while (rs.next()) {        	 
        		priced=rs.getDate("pricedate");
        	}
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
        	System.out.print("Latest Date"+priced);
            return priced;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	public String readfundprice(int fundid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName1 + " WHERE fundid=?");
        	pstmt.setInt(1,fundid);
        	ResultSet rs = pstmt.executeQuery();
        	
        	FundPrice fund=new FundPrice();
        	StringBuilder sb=new StringBuilder();
        	sb.append("[");
        	while (rs.next()) {        	 
        		fund.setFundId(rs.getInt("fundid"));
        		fund.setPrice(rs.getLong("price"));
        		fund.setPriceDate(rs.getDate("pricedate"));
        		sb.append("["+fund.getPriceDate().getTime()+","+fund.getFundPrice()+"],");
        	}
        	sb.substring(0, sb.length()-1);
        	sb.append("]");
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return sb.toString();
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public String readfundstats(int fundid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName1 + " WHERE fundid=?");
        	pstmt.setInt(1,fundid);
        	ResultSet rs = pstmt.executeQuery();
        	
        	FundPrice fund=new FundPrice();
        	StringBuilder sb=new StringBuilder();
        	sb.append("[");
        	double max=0,min=0,count=0,avg=0,current=0;
        	Date td=new Date(),maxd=new Date(),mind=new Date(),currd=new Date();
        	while (rs.next()) {        	 
        		current=rs.getDouble("price");
        		avg+=current;
        		count++;
        		if(count==1){
        			min=current;
        			mind=currd;
        		}
        		currd=rs.getDate("pricedate");
        		if(current>=max){
        			max=current;
        			maxd=currd;
        		}
        		if(current<min){
        			min=current;
        			mind=currd;
        		}
        		}
avg=avg/count;
avg=(double) Math.round(avg * 100) / 100;
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

    		sb.append("['Maximum High','"+maxd+"',"+max+"],");
    		sb.append("['Minimum Low','"+mind+"',"+min+"],");
         	sb.append("['Average Price','"+sdf.format(td)+"',"+avg+"],");
    	  	
        	sb.substring(0, sb.length()-1);
        	sb.append("]");
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return sb.toString();
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}


	public String readfunddateprice(int fundid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName1 + " WHERE fundid=?");
        	pstmt.setInt(1,fundid);
        	ResultSet rs = pstmt.executeQuery();
        	
        	FundPrice fund=new FundPrice();
        	StringBuilder sb=new StringBuilder();
        	sb.append("[");
        	while (rs.next()) {        	 
        		fund.setFundId(rs.getInt("fundid"));
        		fund.setPrice(rs.getLong("price"));
        		fund.setPriceDate(rs.getDate("pricedate"));
        		sb.append("['"+(fund.getPriceDate()).toString()+"',"+fund.getFundPrice()+"],");
        		System.out.println(fund.getPriceDate());
        	}
        	sb.substring(0, sb.length()-1);
        	sb.append("]");
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return sb.toString();
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public String guid(String username) throws MyDAOException {
		Connection con = null;
		String temp=null;

		try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("SELECT id FROM " + tableName + " WHERE username=?");
        	pstmt.setString(1,username);
        	ResultSet rs = pstmt.executeQuery();
        	
        	if (rs.next()) {
        
        		temp=(rs.getString("id"));
        	    	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
                    } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
		System.out.println(temp);
        return temp;

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
            stmt.executeUpdate("create table "+tableName+" (id int not null auto_increment," +
            		" lastname text, firstname text, userName text,password text," +
            		" primary key(id))");
            stmt.close();
        	
        	releaseConnection(con);

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }
	public User[] getUsers() throws MyDAOException {
		User[] users=null ;
		Arrays.sort(users);  // We want them sorted by last and first names (as per User.compareTo());
		return users;
	}
	
}
