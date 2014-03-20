//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package formbeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

public class TransactDayForm extends FormBean {
	private String button     = "";
	private String d1    = "";

	
	public String       getButton()         { return button;         }
	public String       getD1()        { return d1;        }

	public void setButton(String s)         { button      = s;        }
	public void setD1(String s)        { d1     = trimAndConvert(s,"<>\"");}

	public boolean isThisDateValid(String dateToValidate, String dateFromat){
		 
		if(dateToValidate == null){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
 
		try {
 
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
 
		} catch (Exception e) {
 
			e.printStackTrace();
			return false;
		}
 
		return true;
	}
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (d1 == null || d1.length() == 0) {
			errors.add("You must provide a Date");
			}
		if (!isThisDateValid(d1,"yyyy-MM-dd")) {
			errors.add("Date format Invalid");
			}
	
		return errors;
	}
}
