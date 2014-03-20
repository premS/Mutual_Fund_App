package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import databeans.User;
import model.FundDAO;
import model.Model;
import model.UserDAO;

public class Deposit_Action extends Action {
	private UserDAO  userDAO;
	private FundDAO  fundDAO;

	public Deposit_Action(Model model) {
    	userDAO  = model.getUserDAO();
    	fundDAO  = model.getFundDAO();
    	
	}

	public String getName() { return "deposit.do"; }
	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			

			User user = (User) request.getSession(false).getAttribute("user");
			request.setAttribute("user_name",userDAO.getusernames());
	        return "deposit.jsp";
        } catch (Exception e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
