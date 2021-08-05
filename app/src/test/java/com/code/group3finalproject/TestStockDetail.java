package com.code.group3finalproject;

import android.util.Pair;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestStockDetail {

    @Test
    public void testEmptyStockQuotes () {
        JSONObject stock = new StockDetailActivity.StockQuotesAPI("").doWork();
        assertEquals(stock, null);
    }

    @Test
    public void testInvalidStockQuotes () {
        JSONObject stock = new StockDetailActivity.StockQuotesAPI("testt").doWork();
        assertEquals(stock.length(), 0);
    }

    @Test
    public void testValidStockQuotes () {
        JSONObject stock = new StockDetailActivity.StockQuotesAPI("IBM").doWork();
        assertTrue(stock.length() > 0);
        assertTrue(stock.has("02. open"));
        assertTrue(stock.has("03. high"));
    }

    @Test
    public void testStockHistories() {
        List<Pair<Date, Double>> histories = (List<Pair<Date, Double>>) new StockDetailActivity.StockAPITask("IBM", StockDetailActivity.STOCK_COMMAND.HISTORY).doWork();
        assertTrue(histories.size() > 0);
    }

    @Test
    public void testStockIntraday() {
        List<Pair<Date, Double>> intraday = (List<Pair<Date, Double>>) new StockDetailActivity.StockAPITask("IBM", StockDetailActivity.STOCK_COMMAND.INTRADAY).doWork();
        assertTrue(intraday.size() > 0);
    }
}
