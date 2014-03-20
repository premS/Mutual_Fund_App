//lily

package databeans;

public class PortfolioBean {
	private int cid;
	private int fid;
	private int chold;
	private double buyprice;
	private String fundname;
    	
	public int getCID () {
		return cid;
	}
	
	public void setCID (int cid) {
		this.cid = cid;
	}
	
	public int getFID () {
		return fid;
	}
	
	public void setFID (int cid) {
		this.fid = cid;
	}
	
	public int getCurrentHolding () {
		return chold;
	}
	
	public void setCurrentHolding (int cid) {
		this.chold = cid;
	}
	
	public String getFundName () {
		return fundname;
	}
	
	public void setFundName (String cid) {
		this.fundname = cid;
	}
	
	public double getBuyPrice () {
		return buyprice;
	}
	
	public void setBuyPrice (double cid) {
		this.buyprice = cid;
	}
	}
