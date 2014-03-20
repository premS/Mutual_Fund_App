package databeans;

import java.util.Date;

public class Transaction {
	private Date date = new Date();
	private String operation = null;
	private String fundName;
	private int fundId;
	private int customerId;
	private int transactionId;
	private long numberOfShares = 0;
	private long sharePrice=0;
	private long balanceafter=0;
	private long balancebefore=0;
	private long amount=0;
	private String state = null;
	
	public Date getDate() 				{return date;}
	public String getOperation() 			{return operation;}
	public String getFundName() 			{return fundName;}
	public long getNumberOfShares() 			{return numberOfShares;}
	public long getSharePrice()				{return sharePrice;}
	public long getBalanceAfter()				{return balanceafter;}
	public long getBalanceBefore()				{return balancebefore;}
	public long getAmount() 				{return amount;}
	public String getState() 				{return state;}
	public int getFundId()					{return fundId;}
	public int getCustomerId()					{return customerId;}
	public int getTransactionId()					{return transactionId;}
	
	public void setDate(Date s) 			{date = s;}
	public void setOperation(String s) 		{operation = s;}
	public void setFundName(String s) 		{fundName = s;}
	public void setNumberOfShares(long s) 	{numberOfShares = s;}
	public void setSharePrice(long s) 			{sharePrice = s;}
	public void setBalanceAfter(long s) 			{balanceafter = s;}
	public void setBalanceBefore(long s) 			{balancebefore = s;}
	public void setAmount(long s) 		{amount = s;}
	public void setState(String s)			{state = s;}
	public void setFundId(int s)			{fundId =s;}
	public void setCustomerId(int s)			{customerId =s;}
	public void setTransactionId(int s)			{transactionId =s;}
}
