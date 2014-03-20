package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import databeans.User;
import model.FundDAO;
import model.Model;
import model.UserDAO;

public class CreateFund_Action extends Action {
	
	public CreateFund_Action(Model model) {
    	
	}

	public String getName() { return "createfund.do"; }
	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
    return "createfund.jsp";    
    }
}
