
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

public class Portfolio  {
	private int    fundId          = -1;
	private String  fundName = null;

	private String  fundSymbol =null;
	private long currentHolding = 0;
	
	public String  getFundSymbol() { return fundSymbol; }
	public String  getFundName()       { return fundName;       }
	public int     getFundId()           { return fundId;           }
	public long getCurrentHolding() {return currentHolding;}
	
	
	public void setFundSymbol(String x)  { fundSymbol = x; }
	public void setFundName(String s)        { fundName = s; }
	public void setFundId(int x)               { fundId = x;           }
	public void setCurrentHolding(long x)	{currentHolding = x;}
	
}
