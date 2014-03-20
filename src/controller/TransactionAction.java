package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;
import model.TransactionDAO;
import model.UserDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import databeans.Transaction;
import databeans.User;
import formbeans.LoginForm;

public class TransactionAction extends Action{

	FundDAO fundDAO;
	TransactionDAO transactionDAO;
	
	public TransactionAction(Model model){
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO(); 
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "transaction.do";
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
		        	return "trasaction.jsp";
		        }
		
		      
		   List<Transaction> transaction = new ArrayList();
		   int customerId = user.getId();
		   
		   transaction = transactionDAO.read(customerId);
		 
		   for(int i = 0; i<transaction.size(); i++){
			
			   int fundId = transaction.get(i).getFundId();
			   if(fundId ==0)
				   transaction.get(i).setFundName("-");
			   else
			   transaction.get(i).setFundName(fundDAO.readById(fundId));
		   }

		   request.setAttribute("transaction", transaction);
		    return "transaction.jsp";
		 }
		 catch (Exception e) {
	        	errors.add(e.getMessage());
	        	return "error.jsp";
	        } 
		
	}

}
