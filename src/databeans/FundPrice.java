
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.*;
import org.genericdao.PrimaryKey;

public class FundPrice  {
	private int    fundid          = -1;
	private Date  priceDate = null;

	private long  price =0;

	
	public Date  getPriceDate() { return priceDate; }
	public long  getFundPrice()       { return price;       }
	public int     getFundId()           { return fundid;           }

	
	public void setPriceDate(Date x)  { priceDate = x; }
	public void setPrice(long s)        { price = s; }
	public void setFundId(int x)               { fundid = x;           }


}
