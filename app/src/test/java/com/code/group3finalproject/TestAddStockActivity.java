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
       JSONArray stock = new AddStockActivity().new GetContacts("","6U3CR4XTV8H80PTS").doBGWork();
        assertEquals(stock, null);
    }

    @Test
    public void testValidStockQuotes () {
        JSONArray stock = new AddStockActivity().new GetContacts("TM","6U3CR4XTV8H80PTS").doBGWork();
        assertTrue(stock.length() > 0);
    }

    @Test
    public void testInValidStockQuotes () {
        JSONArray stock = new AddStockActivity().new GetContacts(":","6U3CR4XTV8H80PTS").doBGWork();
        assertTrue(stock.length() == 0);
        }
}
