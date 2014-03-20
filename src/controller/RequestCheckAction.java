package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.CheckDAO;
import model.MyDAOException;
import model.UserDAO;
import model.FundDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CheckBean;
import databeans.User;

import formbeans.CheckForm;

public class RequestCheckAction extends Action {
	private FormBeanFactory<CheckForm> formBeanFactory = FormBeanFactory.getInstance(CheckForm.class);
	
	private CheckDAO checkDAO;
	private UserDAO userDAO;
	private FundDAO fundDAO;

	public RequestCheckAction(Model model) {
		checkDAO = model.getCheckDAO();
		userDAO = model.getUserDAO();
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return "requestcheck.do";
	}
	
	public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
			User user = (User) request.getSession(false).getAttribute("user");
        
			CheckForm form = formBeanFactory.create(request);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) 
				return "requestcheck.jsp";
	        
	        CheckBean check = new CheckBean();
	        check.setAmount(form.getAmountAsDouble());
	       
	        try {
	        	check.setCID(Integer.parseInt(userDAO.guid(user.getUserName())));
	        	 check.setCash(userDAO.getcash(Integer.parseInt(userDAO.guid(user.getUserName()))));
	        	checkDAO.creatCheck(check);
		  	} catch (MyDAOException e) {
	        	errors.add(e.toString());
	        	
				return "requestcheck.jsp";
			}
	        request.setAttribute("message", "Check Request Success!");
	        return "success.jsp";
        } catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestcheck.jsp";
		}
	}
}

