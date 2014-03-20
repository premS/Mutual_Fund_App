//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.MyDAOException;
import model.UserDAO;
import model.FundDAO;

import org.genericdao.RollbackException;

import databeans.FundBean;
import databeans.FundDisplay;
import databeans.User;


/*
 * Sets up the request attributes for manage.jsp.
 * This is the way to enter "Manage Your Photos"
 * from someplace else in the site.
 * 
 * Sets the "userList" request attribute in order to display
 * the list of users on the navbar.
 * 
 * Sets the "photoList" request attribute in order to display
 * the list of user's photos for management.
 * 
 * Forwards to manage.jsp.
 */
public class TransactDayAction extends Action {

	private UserDAO  userDAO;
	private FundDAO  fundDAO;

	public TransactDayAction(Model model) {
    	userDAO  = model.getUserDAO();
    	fundDAO  = model.getFundDAO();
    	
	}

	public String getName() { return "transactday.do"; }

	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			ArrayList<FundBean> fundList;
			ArrayList<FundDisplay> funds=new ArrayList<>();
			try {
				fundList = fundDAO.listAllFund();
//				System.out.println(fundList);
				for(FundBean b:fundList){
				FundDisplay fund=fundDAO.getDisplayFund(b.getFundId());
				fund.setFundId(b.getFundId());	
				fund.setFundName(b.getFundName());	
				funds.add(fund);
				}
				
				request.setAttribute("fundList",funds);
			} catch (MyDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return "transactday.jsp";
        } catch (Exception e) {
        	errors.add(e.getMessage());
        	return "tderror.jsp";
        }
    }
}
