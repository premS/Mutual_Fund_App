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
import formbeans.CreateFundForm;
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
public class CreateFund1_Action extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);

	private FundDAO favoriteDAO;
	
	public CreateFund1_Action(Model model) {
    	favoriteDAO = model.getFundDAO();

	}

	public String getName() { return "createfund1.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			
			User user = (User) request.getSession(false).getAttribute("user");
        
			CreateFundForm form = formBeanFactory.create(request);
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) return "error.jsp";

	  	try {

			if(!favoriteDAO.checkfund_exists(form.getFname(),form.getFtick())){
				    try {
			        	favoriteDAO.createfunds(form.getFname(), form.getFtick());
				  	} catch (MyDAOException e) {
			        	errors.add(e.toString());
			        	
						return "deposit.jsp";
					}
	  	}
			else{
				errors.add("Fund already exists!..Provide some other Fund name/Ticker!");
				return "error.jsp";
			}
				
	  	} catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
			// Update photoList (there's now one more on the list)
        	
	        return "createfund1.jsp";
	 	} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createfund.jsp";
		}
    }
    }
