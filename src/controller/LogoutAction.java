//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.UserDAO;


/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {

	public LogoutAction(Model model) { 	userDAO = model.getUserDAO();
}
	private UserDAO userDAO;

	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("user",null);
       
		request.setAttribute("message","You are now logged out");
        return "success.jsp";
    }
}
