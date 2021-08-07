package com.code.group3finalproject.db;

import static org.mockito.Mockito.verify;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.code.group3finalproject.LiveDataTestUtil;
import com.code.group3finalproject.db.dao.StockDAO;
import com.code.group3finalproject.db.model.Stock;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class StockDatabaseTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private StockDatabase db;
    private StockDAO stockDAO;
    String stockSymbol = "TSLA";

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, StockDatabase.class).build();
        stockDAO = db.getStockDAO();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void insertAndReadStock() throws Exception {
        Stock stock = new Stock(stockSymbol);

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.insert(stock);
        });

        List<Stock> allStocks = LiveDataTestUtil.getValue(stockDAO.searchValue(stockSymbol));
        assertEquals(allStocks.get(0).getSymbol(), stock.getSymbol());
    }

    @Test
    public void insertAndDeleteStock() throws Exception {
        Stock stock = new Stock(stockSymbol);

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.insert(stock);
        });

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.delete(stock);
        });

        List<Stock> allStocksAfterDelete = LiveDataTestUtil.getValue(stockDAO.getAll());

        assertFalse(allStocksAfterDelete.contains(stock));
    }

    @Test
    public void insertAndDeleteMultipleStocks() throws Exception {
        Stock stock1 = new Stock("MSFT");
        Stock stock2 = new Stock("TSLA");
        Stock stock3 = new Stock("AAPL");

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.insert(stock1, stock2, stock3);
        });

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.delete(stock1, stock2, stock3);
        });

        List<Stock> allStocksAfterDelete = LiveDataTestUtil.getValue(stockDAO.getAll());

        assertFalse(allStocksAfterDelete.contains(stock1));
        assertFalse(allStocksAfterDelete.contains(stock2));
        assertFalse(allStocksAfterDelete.contains(stock3));
    }

    @Test
    public void itemCount() {
        Stock stock = new Stock(stockSymbol);

        int countBeforeInsert = stockDAO.count();

        StockDatabase.databaseWriteExecutor.execute(() -> {
            stockDAO.insert(stock);
        });

        int countAfterInsert = stockDAO.count();
        assertEquals(countBeforeInsert+1, countAfterInsert);
    }

}