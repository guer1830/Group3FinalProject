package com.code.group3finalproject;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class TestAddStockActivity {

    @Test
    public void testEmptyStockQuotes () {
       JSONArray stock = new AddStockActivity.GetContacts("").doBGWork();
        assertEquals(stock, null);
    }

    @Test
    public void testValidStockQuotes () {
        JSONArray stock = new AddStockActivity.GetContacts("TM").doBGWork();
        assertTrue(stock.length() > 0);
    }

    @Test
    public void testInValidStockQuotes () {
        JSONArray stock = new AddStockActivity.GetContacts(":").doBGWork();
        assertTrue(stock.length() == 0);
    }

}
