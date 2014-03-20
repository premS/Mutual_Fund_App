package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;
import model.PortfolioDAO;
import model.UserDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import databeans.Portfolio;
import databeans.User;
import formbeans.LoginForm;

public class PortfolioAction extends Action{

	FundDAO fundDAO;
	PortfolioDAO portfolioDAO;
	public PortfolioAction(Model model){
		fundDAO = model.getFundDAO();
		portfolioDAO = model.getPortfolioDAO();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "portfolio.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList();
		request.setAttribute("errors", errors);
		 try{
		      //  request.setAttribute("userList",userDAO.getUsers());
		    } catch (Exception e) {
		    	errors.add(e.getMessage());
		    	return "error.jsp";
		    }
		 try {
			 	User user = (User) request.getSession().getAttribute("user");
		        if(user==null){
		        	
		        	errors.add("Please first log in");
		        	return "portfolio.jsp";
		        }
		

		   int customerId = user.getId();
	
		   ArrayList<Portfolio> portfolio = new ArrayList();
		   ArrayList<String> price = new ArrayList();
		   
		   portfolio = portfolioDAO.read(customerId);
		   for(int i = 0; i<portfolio.size();i++){
			   Portfolio p = portfolio.get(i);
			   price.add(fundDAO.readfundprice(p.getFundId()));
		   }
		   request.setAttribute("portfolio", portfolio);
		   request.setAttribute("price", price);
		        return "portfolio.jsp";
		 }
		 catch (Exception e) {
	        	errors.add(e.getMessage());
	        	return "error.jsp";
	        } 
		
	}

}
