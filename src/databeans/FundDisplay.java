package databeans;

public class FundDisplay {
	private int fundId;
	private String fundName;
	private Double fundPrice;
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public Double getFundPrice() {
		return fundPrice;
	}
	public void setFundPrice(Double fundPrice) {
		this.fundPrice = fundPrice;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
}
