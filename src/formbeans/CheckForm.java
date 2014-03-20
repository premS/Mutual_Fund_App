//lily

package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CheckForm extends FormBean {
	private String amount;

	public String getAmount() {
		return amount;
	}
	
	public double getAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public void setAmount(String amount) {
		this.amount = amount.trim();
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (amount == null || amount.length() == 0) {
			errors.add("Please enter the amount you want to withdraw.");
			return errors;
		}

		try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			errors.add("Please enter a number.");
		}
		
		return errors;
	}
}
