//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package formbeans;

import java.util.ArrayList;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String button     = "";
	private String fname    = "";
	private String ftick    = "";

	
	public String       getButton()         { return button;         }
	public String       getFname()         { return fname;         }
	public String       getFtick()        { return ftick;        }

	public void setButton(String s)         { button      = s;        }
	public void setFname(String s)        { fname     = trimAndConvert(s,"<>\""); }
	public void setFtick(String s)        { ftick=s;}

	
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (fname == null || fname.length() == 0) {
			errors.add("You must provide a Fund Name");
			}

		if (ftick == null || ftick.length() == 0) {
			errors.add("You must provide a Fund Ticker");
			}
		if ( ftick.length() > 5) {
			errors.add("You must provide a Fund Ticker of 5 characters or less!");
			}
	
		return errors;
	}
}
