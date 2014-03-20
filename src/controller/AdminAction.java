//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import model.AdminDAO;
import model.Model;
import model.FundDAO;
import model.MyDAOException;
import model.UserDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Fund;
import databeans.FundBean;
import databeans.FundDisplay;
import databeans.FundPrice;
import databeans.User;
import formbeans.TransactDayForm;


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
public class AdminAction extends Action {
	private FormBeanFactory<TransactDayForm> formBeanFactory = FormBeanFactory.getInstance(TransactDayForm.class);

	private FundDAO favoriteDAO;
	private UserDAO  userDAO;
	private AdminDAO  adminDAO;
	
	public AdminAction(Model model) {
    	favoriteDAO = model.getFundDAO();
    	userDAO  = model.getUserDAO();
    	adminDAO  = model.getAdminDAO();
	}

	public String getName() { return "admin.do"; }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			
			User user = (User) request.getSession(false).getAttribute("user");
        
			TransactDayForm form = formBeanFactory.create(request);
			System.out.println("Date:"+form.getD1());
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) return "tderror.jsp";
	        Enumeration en = request.getParameterNames();
	        String gd = request.getParameter("d1");
	        Date gd1=new Date();
			try {
				gd1 = new SimpleDateFormat("yyyy-MM-dd").parse(gd);
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	        try {
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	System.out.println("poipoi");
				Date d=favoriteDAO.getlatestfunddate();
				
				 System.out.println("Latest Date:"+d+" Given Date:"+gd1+"Compare "+gd1.compareTo(d));
				 if(gd1.compareTo(d)<=0)
				 {
					 errors.add("Please Specify a Trading Day greater than "+d);
			        if (errors.size() > 0) return "tderror.jsp";
	              }
	        } catch (MyDAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       
            // enumerate through the keys and extract the values 
            // from the keys! 
            while (en.hasMoreElements()) {
                String parameterName = (String) en.nextElement();
                String parameterValue = request.getParameter(parameterName);
                System.out.println(parameterName+":"+parameterValue);double dd=0;
                if (parameterValue == null || parameterValue.length() == 0) {
        			try {
						errors.add("Specify Closing Price for "+favoriteDAO.getFundName(Integer.parseInt(parameterName)));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MyDAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			}
                else if(!parameterName.equals("d1")){
                	try {
                        Double.parseDouble(parameterValue);
                        
                    }
                    catch (Exception e)
                    {
                        try {
							errors.add("Couldn't parse input, please provide valid amount for "+favoriteDAO.getFundName(Integer.parseInt(parameterName)));
							 // return "tderror.jsp";
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (MyDAOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                      
                    }
                	
                }
            }
            if (errors.size() > 0) return "tderror.jsp";
			// Update photoList (there's now one more on the list)
            Enumeration en1 = request.getParameterNames();
            while (en1.hasMoreElements()) {
                String parameterName = (String) en1.nextElement();
                String parameterValue = request.getParameter(parameterName);
            if(!parameterName.equals("d1")){
                	FundPrice fp=new FundPrice();
                	fp.setFundId(Integer.parseInt(parameterName));
                	double val=Math.round(Double.parseDouble(parameterValue)*100.0)/100.0;
                	System.out.println(val);
                	long x = (long) (val*100);
                	fp.setPrice(x);
                	fp.setPriceDate(gd1);
                	try {
						favoriteDAO.createfundprice(fp);
					} catch (MyDAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
                	}
            }
        	try {
				adminDAO.transactday(gd1);
			} catch (MyDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return "execute.jsp";
	 	} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "tderror.jsp";
		}
    }
    }
