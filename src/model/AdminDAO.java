package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import databeans.Portfolio;
import databeans.Transaction;

public class AdminDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private UserDAO userdao;
	private FundDAO funddao;
	private PortfolioDAO portfoliodao;
	private TransactionDAO transactiondao;
	
	public AdminDAO(String jdbcDriver, String jdbcURL, UserDAO udao,FundDAO funddao,PortfolioDAO pf,TransactionDAO td) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.userdao  = udao;
		this.funddao  = funddao;
		this.portfoliodao=pf;
		this.transactiondao=td;
		}
	
	private synchronized Connection getConnection() throws MyDAOException {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}
	
	public void transactday(Date d) throws MyDAOException{
		
		ArrayList<Transaction> pending_transactions=transactiondao.getpending();
	for(Transaction tr:pending_transactions){
		long cash=0;
	System.out.println(tr.getTransactionId());
	if (tr.getOperation().equals("check"))  {
		 cash=userdao.getcash(tr.getCustomerId());
	     if(cash-tr.getAmount()>0) {
	            cash-=tr.getAmount();
	            userdao.setCash(cash, tr.getCustomerId());
	            try {
					transactiondao.setcheckexecute(d,cash,"executed",tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	     else{
	    	    try {
					transactiondao.setcheckexecute(d,cash,"cancelled",tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
	       }
	}
	else if (tr.getOperation().equals("deposit"))  {
		 cash=userdao.getcash(tr.getCustomerId());
	     if(cash+tr.getAmount()>0) {
	            cash+=tr.getAmount();
	            userdao.setCash(cash, tr.getCustomerId());
	            try {
					transactiondao.setcheckexecute(d,cash,"executed",tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	     else{
	    	    try {
					transactiondao.setcheckexecute(d,cash,"cancelled",tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
	       }
	}
	else if(tr.getOperation().equals("buy")){
		long fundprice=funddao.getlatestfundprice(tr.getFundId());
		 cash=userdao.getcash(tr.getCustomerId());
		 double nsh=(tr.getAmount()* 1.0 )/fundprice;
		 nsh=Math.round(nsh*1000.0)/1000.0;
     	System.out.println("No of shares:"+nsh);
		 long noofshares=(long) (nsh*1000);
         if (cash-tr.getAmount()>0){
	            cash-=tr.getAmount();
	            userdao.setCash(cash, tr.getCustomerId());
	            try {
					transactiondao.setbuyexecute(d,cash,"executed",noofshares,fundprice,tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Portfolio portfolio=portfoliodao.readByFundCId(tr.getCustomerId(),tr.getFundId());
	            if(portfolio!=null){
	            long new_total=portfolio.getCurrentHolding()+noofshares;
	            portfoliodao.updateBuy(tr.getCustomerId(),tr.getFundId(),new_total);
	            }
	            else
	            	  portfoliodao.createBuy(tr.getCustomerId(),tr.getFundId(),noofshares);
		            	
         }         
		
 else{
	    	    try {
					transactiondao.setcheckexecute(d,cash,"cancelled",tr.getTransactionId());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
	       }
		}
else if(tr.getOperation().equals("sell")){
	long fundprice=funddao.getlatestfundprice(tr.getFundId());
	 cash=userdao.getcash(tr.getCustomerId());
   	
	 long noofshares=tr.getNumberOfShares();
   	Portfolio portfolio=portfoliodao.readByFundCId(tr.getCustomerId(),tr.getFundId());
if(portfolio!=null){   	
	long currentholding=portfolio.getCurrentHolding();
    if ((currentholding-noofshares)>=0){
    	double ca=(noofshares /1000.0)*(fundprice /100.0);
    	 ca=Math.round(ca*100.0)/100.0;
      	System.out.println("Sell Cash"+ca);
      	long execute_price=(long) (ca*100);
 		 cash+=execute_price;
        userdao.setCash(cash, tr.getCustomerId());
    try {
		transactiondao.setsellexecute(d,cash,"executed",(execute_price),fundprice,tr.getTransactionId());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    long new_total=portfolio.getCurrentHolding()-noofshares;
    portfoliodao.updateBuy(tr.getCustomerId(),tr.getFundId(),new_total);
    
    }
    else{
        try {
			transactiondao.setcheckexecute(d,cash,"cancelled",tr.getTransactionId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         }

    }
else{
    try {
		transactiondao.setcheckexecute(d,cash,"cancelled",tr.getTransactionId());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
     }
   	
	}
	
    
}
	
	 
	}
	}

