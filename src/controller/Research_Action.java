package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;


import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import formbeans.IdForm;

public class Research_Action extends Action {

	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);

	
    public Research_Action(Model model) {
    }
    
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "research.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
        	IdForm form = formBeanFactory.create(request);
        	
            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "error.jsp";
            }

        	
    		// Note: "/image" is mapped (in the web.xml file) to the ImageServlet
    		// which looks at the "photo" request attribute and sends back the bytes.
    		// If there is no "photo" attribute, it sends back HTTP Error 404 (resource not found).
    		return "image";
    	} catch (Exception e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	} 
        }
	}


