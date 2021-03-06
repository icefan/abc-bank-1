package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
	@Test
	public void testMaxiInterestPaidByDay(){

        Account maxiAcct = new Account(Account.MAXI_SAVINGS);

        Customer bob = new Customer("Bob").openAccount(maxiAcct);

        maxiAcct.deposit(4000.0);
        maxiAcct.withdraw(200.0);

        assertEquals(0.136986301369863, bob.totalInterestEarnedByDay());   
    }
	
    @Test
    public void testTransferBetweenAccounts(){

        Account checkingAcct = new Account(Account.CHECKING);
        Account savingsAcct = new Account(Account.SAVINGS);

        Customer alex = new Customer("Alex").openAccount(checkingAcct).openAccount(savingsAcct);

        checkingAcct.deposit(1000.0);
        savingsAcct.deposit(4000.0);
        alex.transferBetweenAccounts(checkingAcct,savingsAcct,200.0);

       assertEquals("Statement for Alex: Checking Account\n  deposit $1000.00\n"
       				+ "Statement for Alex: Savings Account\n  deposit $4000.00\n"
       				+ "Statement for Alex: Savings Account\n  withdrawal $200.00\n"
       				+ "\nTotal In All Accounts $5000.00", alex.getStatement());
        
    }
    
}
