package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    @Test
    public void transactionDate() {
        Transaction t = new Transaction(1000.0);
        Date date = new Date();
        assertEquals(date.getTime(),t.getTransactionDate());
    }
   
}
