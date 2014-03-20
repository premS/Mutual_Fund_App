//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databeans.User;

public class Model {

	private UserDAO  uDAO;
	private FundDAO  fundDAO;
	private CheckDAO checkDAO;
	private PortfolioDAO portfolioDAO;
	private TransactionDAO transactionDAO;
	private AdminDAO adminDAO;
	public Model(ServletConfig config) throws ServletException, MyDAOException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			uDAO  = new UserDAO(jdbcDriver, jdbcURL, "customer");
			fundDAO = new FundDAO(jdbcDriver, jdbcURL,"fund","fundprice");
			checkDAO = new CheckDAO(jdbcDriver, jdbcURL, "transaction");
			portfolioDAO = new PortfolioDAO(jdbcDriver, jdbcURL, "portfolio");
			transactionDAO = new TransactionDAO(jdbcDriver, jdbcURL, "transaction");
			adminDAO = new AdminDAO(jdbcDriver, jdbcURL, uDAO,fundDAO,portfolioDAO,transactionDAO);
		}
		catch (MyDAOException e) {
			throw new ServletException(e);
		}
	}
	public FundDAO getFundDAO() { return fundDAO; }	
	public PortfolioDAO getPortfolioDAO() { return portfolioDAO; }	
	public UserDAO  getUserDAO()  { return uDAO;  }
	public CheckDAO  getCheckDAO()  { return checkDAO; }
	public TransactionDAO  getTransactionDAO()  { return transactionDAO; }
	public AdminDAO  getAdminDAO()  { return adminDAO; }
}
