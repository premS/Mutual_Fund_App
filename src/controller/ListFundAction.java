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

import org.genericdao.RollbackException;
import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import databeans.FundBean;
import databeans.FundDisplay;
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
public class ListFundAction extends Action {
	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);

	private FundDAO fundDAO;
	private UserDAO  userDAO;
	public ListFundAction(Model model) {
		fundDAO = model.getFundDAO();
    	userDAO  = model.getUserDAO();
	}

	public String getName() { return "listFund.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		ArrayList<FundBean> fundList;
		ArrayList<FundDisplay> funds=new ArrayList<>();
		try {
			fundList = fundDAO.listAllFund();
//			System.out.println(fundList);
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
		
	  	
        	
	        return "buyList.jsp";
    }
    }
