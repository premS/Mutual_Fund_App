
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class FundBean  {
	private int    fundId      ;
	private String  fundName ;

	private String  fundSymbol ;

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	


}
