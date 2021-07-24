package com.code.group3finalproject.db.dao;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import java.util.List;

public class StockRepository {
    private StockDAO mStockDAO;
    private LiveData<List<Stock>> mAllStocks;

    public StockRepository(Application application) {
        StockDatabase db = StockDatabase.getInstance(application);
        mStockDAO = db.getStockDAO();
        mAllStocks = mStockDAO.getAll();
    }

    public LiveData<List<Stock>> getAllStocks() {
        return mAllStocks;
    }

    public LiveData<List<Stock>> searchValue(String stockSymbol) { return mStockDAO.searchValue(stockSymbol); }

    public void insert(Stock stock) {
        StockDatabase.databaseWriteExecutor.execute(() -> {
            mStockDAO.insert(stock);
        });
    }

    public void insert(Stock... stock) {
        StockDatabase.databaseWriteExecutor.execute(() -> {
            mStockDAO.insert(stock);
        });
    }

    public void delete(Stock stock) {
        StockDatabase.databaseWriteExecutor.execute(() -> {
            mStockDAO.delete(stock);
        });
    }
}
