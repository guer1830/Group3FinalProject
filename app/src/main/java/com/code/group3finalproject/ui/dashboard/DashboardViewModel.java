package com.code.group3finalproject.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.code.group3finalproject.db.dao.StockRepository;
import com.code.group3finalproject.db.model.Stock;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private StockRepository mStockRepository;
    private final LiveData<List<Stock>> mAllStocks;

    public DashboardViewModel(Application application) {
        super(application);
        mStockRepository = new StockRepository(application);
        mAllStocks = mStockRepository.getAllStocks();
    }

    LiveData<List<Stock>> getAllStocks() { return mAllStocks; }

    LiveData<List<Stock>> searchStocks(String stockSymbol) { return mStockRepository.searchValue(stockSymbol); }

    public void insert(Stock stock) { mStockRepository.insert(stock); }

    public void delete(Stock stock) { mStockRepository.delete(stock); }
}