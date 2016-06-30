package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        synchronized(transactions){
        	this.transactions = new ArrayList<Transaction>();
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        		transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		synchronized(transactions){
    			transactions.add(new Transaction(-amount));
    		}
    	}
    }
    
    private boolean checkWithdrawFromMaxiSavings() {
    	 for (Transaction trans: transactions){
    		 if (trans.amount < 0){
    			 long diff=(DateProvider.getInstance().now().getTime()-trans.getTransactionDate().getTime())/(1000*24*60*60);
    			 if(accountType==MAXI_SAVINGS && diff>10)
    				 return true;
    		 }
    	 }
    	 	return false;
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
        	case CHECKING:
        		if (amount > 0)
        			return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                else if(amount > 2000)
                	return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }
    
    public double interestEarnedByDay() {
        double amount = sumTransactions();
        switch(accountType){
        	case CHECKING:
        		if (amount > 0)
        			return amount * 0.001/365;
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001/365;
                else
                    return 1 + (amount-1000) * 0.002/365;
            case MAXI_SAVINGS:
            	if(checkWithdrawFromMaxiSavings())
            		return amount*0.05/365;
            	else
            		return amount*0.001/365;
            default:
                return amount * 0.001/365;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        	for (Transaction t: transactions)
        		amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
