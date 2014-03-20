//ID:palwarsa
//Course:08-600 JAVA-J2EE Programming
//1-Dec-2013

package formbeans;

import java.util.ArrayList;

import org.mybeans.form.FileProperty;
import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
	private String button     = "";
	private String fund    = "";

	
	public String       getButton()         { return button;         }
	public String       getUrl()        { System.out.println(fund);return fund;        }

	public void setButton(String s)         { button      = s;        }
	public void setUrl(String s)        { fund     = trimAndConvert(s,"<>\""); System.out.println(s+" "+fund);}

	
	public ArrayList<String> getValidationErrors() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if (fund == null || fund.length() == 0) {
			errors.add("You must provide a Fund Name");
			}

	
		return errors;
	}
}
