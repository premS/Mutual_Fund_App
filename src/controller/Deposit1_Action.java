//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.FundDAO;
import model.MyDAOException;
import model.UserDAO;
import model.CheckDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CheckBean;
import databeans.Fund;
import databeans.FundBean;
import databeans.FundDisplay;
import databeans.FundPrice;
import databeans.User;
import formbeans.DepositForm;
import formbeans.FavoriteForm;


/*
 * Uploads a file from the user.  If successful, sets the "userList"
 * and "photoList" request attributes, creates a new Photo bean with the
 * image, and forward (back to) manage.jsp.
 * 
 * Note that to upload a file, the multipart encoding type is used
 * in the HTML form.  This needs to be specially parsed.  The FormBeanFactory
 * can do this, but to do it, the FormBeanFactory uses the Jakarta Commons FileUpload
 * package (org.apache.commons.fileupload).
 * These classes are in the commons-fileupload-x.x.jar file in the webapp's
 * WEB-INF/lib directory.  See the User Guide on
 * http://jakarta.apache.org/commons/fileupload for details.
 */
public class Deposit1_Action extends Action {
	private FormBeanFactory<DepositForm> formBeanFactory = FormBeanFactory.getInstance(DepositForm.class);

	private FundDAO favoriteDAO;
	private UserDAO  userDAO;
	private CheckDAO  CheckDAO;
	
	public Deposit1_Action(Model model) {
    	favoriteDAO = model.getFundDAO();
    	userDAO  = model.getUserDAO();
    	CheckDAO  = model.getCheckDAO();
	}

	public String getName() { return "deposit1.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			
			User user = (User) request.getSession(false).getAttribute("user");
        
			DepositForm form = formBeanFactory.create(request);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) return "error.jsp";

	  	User user1 = new User();  // id & position will be set when created
	  	try {
	  		user1.setUserName(form.getUname());
			if(userDAO.checkuser(user1)){
				 CheckBean check = new CheckBean();
			        check.setAmount(form.getAmount());
			       
			        try {
			        	check.setCID(Integer.parseInt(userDAO.guid(user1.getUserName())));
			        	 check.setCash(userDAO.getcash(Integer.parseInt(userDAO.guid(user1.getUserName()))));
			        	 check.setAmount(form.getAmount());
			        	 System.out.println(check.getAmount()+" CCAsh"+check.getCash());
			        	CheckDAO.deposit_check(check);
				  	} catch (MyDAOException e) {
			        	errors.add(e.toString());
			        	
						return "deposit.jsp";
					}
	  	}
			else{
				errors.add("User Name does not exist!");
				return "error.jsp";
			}
				
	  	} catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
			// Update photoList (there's now one more on the list)
        	
	        return "deposit1.jsp";
	 	} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "deposit.jsp";
		}
    }
    }
