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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





import databeans.User;



public class UserDAO  {
	
	
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public UserDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
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

	public long getcash(int cid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT cash FROM " + tableName + " WHERE id=?");
        	pstmt.setInt(1,cid);
        	ResultSet rs = pstmt.executeQuery();
        	
long cash=0;
if (!rs.next()) {
        		cash = 0;
        	} else {
        		cash=rs.getLong("cash");
        		}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return cash;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
public boolean checkuser(User user)throws MyDAOException{
	boolean flag=false;
	Connection con = null;
    try {
    	con = getConnection();
    	PreparedStatement pstmt = con.prepareStatement("select count(*) from " + tableName + " where username=?");
    	
    	pstmt.setString(1,user.getUserName());
    	
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
	public String create(User user) throws MyDAOException {
		Connection con = null;
		String temp=null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (userName,password,firstname,lastname) VALUES (?,?,?,?)");
        	
        	pstmt.setString(1,user.getUserName());
        	//pstmt.setString(2,user.getPassword());
        	pstmt.setString(3,user.getFirstName());
        	pstmt.setString(4,user.getLastName());
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	
        	pstmt.close();
        	releaseConnection(con);
        	temp=guid(user.getUserName());
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
        return temp;
	}

	public User read(String username) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE userName=?");
        	pstmt.setString(1,username);
        	ResultSet rs = pstmt.executeQuery();
        	
        	User user;
        	if (!rs.next()) {
        		user = null;
        	} else {
        		user = new User();
        		user.setUserName(rs.getString("userName"));
        		user.setFirstName(rs.getString("firstName"));
        		user.setLastName(rs.getString("lastName"));
        		user.setCash(rs.getDouble("cash"));
        		user.setAddr_line1(rs.getString("addr_line1"));
        		user.setAddr_line2(rs.getString("addr_line2"));
        		user.setCity(rs.getString("city"));
        		user.setState(rs.getString("state"));
        		user.setZip(rs.getString("zip"));
        		user.setAddr_line2(rs.getString("addr_line2"));
        		user.setHashedPassword(rs.getString("hashedPassword"));
        		user.setSalt(rs.getInt("salt"));
        		user.setId(rs.getInt("id"));
        		//user.setUserId(rs.getString("id"));
        		//user.setPassword(rs.getString("password"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return user;
            
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
	public String getusernames() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT userName FROM " + tableName );
        	ResultSet rs = pstmt.executeQuery();
        	
        	StringBuilder sb=new StringBuilder();
        	while (rs.next()) {        	 
        		sb.append("'"+rs.getString("username")+"',");
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
	public void setCash(long cash,int cid){
        try {
    		Connection con = null;
        		con = getConnection();
			
        	String updateTableSQL = "UPDATE "+tableName+" set cash=? WHERE id= ?";
        	PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement(updateTableSQL);
			
            preparedStatement.setLong(1, cash);
        	preparedStatement.setInt(2, cid);
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
	public void setPassword(String userName, String password){
        try {
    		Connection con = null;
        		con = getConnection();
			
        	String updateTableSQL = "UPDATE "+tableName+" set hashedPassword=?,salt=? WHERE userName= ?";
        	PreparedStatement preparedStatement;
				preparedStatement = con.prepareStatement(updateTableSQL);
			
            User dbUser = read(userName);
            if (dbUser != null){
            dbUser.setPassword(password);
            preparedStatement.setString(1, dbUser.getHashedPassword());
        	preparedStatement.setInt(2, dbUser.getSalt());
        	preparedStatement.setString(3, userName);
        	// execute insert SQL stetement
        	preparedStatement .executeUpdate();
            }
            else  {
				throw new MyDAOException("User "+userName+" no longer exists");
			}
			
			
			
			
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
	/*
	public User[] getUsers() throws RollbackException {
		User[] users = match();
		Arrays.sort(users);  // We want them sorted by last and first names (as per User.compareTo());
		return users;
	}
	
	*/
}
