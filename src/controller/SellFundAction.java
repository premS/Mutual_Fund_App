//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import model.Model;
import model.FundDAO;
import model.MyDAOException;
import model.PortfolioDAO;
import model.UserDAO;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;





import databeans.Fund;
import databeans.FundBean;
import databeans.FundDisplay;
import databeans.FundPrice;
import databeans.Portfolio;
import databeans.User;
import formbeans.FavoriteForm;
import formbeans.IdForm;


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
public class SellFundAction extends Action {
	private FormBeanFactory<IdForm> idFormFactory = FormBeanFactory.getInstance(IdForm.class);

	private FundDAO fundDAO;
	private UserDAO  userDAO;
	
	private PortfolioDAO portfolioDAO;
	
	public SellFundAction(Model model) {
		fundDAO = model.getFundDAO();
    	userDAO  = model.getUserDAO();
    	portfolioDAO=model.getPortfolioDAO();
	}

	public String getName() { return "sellFund.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
      
		
		
       
			 try {
				IdForm form = idFormFactory.create(request);
				
				FundDisplay fund=fundDAO.getDisplayFund(form.getIdAsInt());
				fund.setFundName(fundDAO.getFundName(form.getIdAsInt()));
				System.out.println(fund.getFundName());
				 int customerId = user.getId();
					
				 Portfolio p=portfolioDAO.readByFundId(form.getIdAsInt());
				
				request.setAttribute("portfolio", p);
				request.setAttribute("fund",fund);
				
				
			} catch (FormBeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MyDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	
	  	
        	
	        return "sell.jsp";
    }
    }
