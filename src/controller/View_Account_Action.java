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
import model.PortfolioDAO;
import model.UserDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import databeans.FundPrice;
import databeans.User;
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
public class View_Account_Action extends Action {
	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);

	private FundDAO favoriteDAO;
	private UserDAO  userDAO;
	private PortfolioDAO  portfolioDAO;
	
	public View_Account_Action(Model model) {
    	favoriteDAO = model.getFundDAO();
    	userDAO  = model.getUserDAO();
    	portfolioDAO=model.getPortfolioDAO();
	}

	public String getName() { return "view.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
	        // Set up user list for nav bar
			
			User user = (User) request.getSession(false).getAttribute("user");
        
			if (errors.size() > 0) return "error.jsp";

	  	String userdetails="[['"+user.getUserName()+"','"+user.getFirstName()+" "+user.getLastName()+"','"+user.getAddr_line1()+" "+user.getAddr_line2()+"','"+user.getCity()+"','"+user.getState()+"','"+user.getZip()+"','"+user.getCash()/100+"']]";
	  	request.setAttribute("accountList",userdetails);
	  	try {
	  		System.out.println(user.getId());
			request.setAttribute("portfolioList",portfolioDAO.getPortfolio(user.getId(),favoriteDAO));
		} catch (MyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	  
			// Update photoList (there's now one more on the list)
        	
	        return "view.jsp";
	}
    }

