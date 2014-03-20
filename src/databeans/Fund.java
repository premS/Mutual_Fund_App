
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

public class Fund  {
	private int    fundid          = -1;
	private String  fundname = null;

	private String  fundsymbol =null;

	
	public String  getFundSymbol() { return fundsymbol; }
	public String  getFundName()       { return fundname;       }
	public int     getFundId()           { return fundid;           }

	
	public void setFundSymbol(String x)  { fundsymbol = x; }
	public void setFundName(String s)        { fundname = s; }
	public void setFundId(int x)               { fundid = x;           }


}
