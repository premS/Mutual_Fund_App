//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package formbeans;

import java.util.ArrayList;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

public class DepositForm extends FormBean {
	private String button     = "";
	private String uname    = "";
	private String amount    = "";

	
	public String       getButton()         { return button;         }
	public double       getAmount()         { return Double.parseDouble(amount);         }
	public String       getUname()        { System.out.println(uname);return uname;        }

	public void setButton(String s)         { button      = s;        }
	public void setUname(String s)        { uname     = trimAndConvert(s,"<>\""); System.out.println(s+" "+uname);}
	public void setAmount(String s)        { amount=s;}

	
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (uname == null || uname.length() == 0) {
			errors.add("You must provide a User Name");
			}
		if ( Double.parseDouble(amount) <= 0) {
			errors.add("You must provide a valid  deposit amount");
			}

	
		return errors;
	}
}
