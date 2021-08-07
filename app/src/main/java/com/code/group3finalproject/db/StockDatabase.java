package com.code.group3finalproject.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.code.group3finalproject.db.dao.StockDAO;
import com.code.group3finalproject.db.model.Stock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Stock.class}, version = 1)
public abstract class StockDatabase extends RoomDatabase {

    public abstract StockDAO getStockDAO();
    private static volatile StockDatabase stockDB;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized StockDatabase getInstance(Context context) {
                if (stockDB == null) {
                    stockDB = Room.databaseBuilder(context, StockDatabase.class, "StockDB.db")
                            .enableMultiInstanceInvalidation().build();
                    stockDB.populateInitialData();
                }
        return stockDB;
    }

    /**
     * Inserts initial data into the database if it is currently empty.
     */
    private void populateInitialData() {

            databaseWriteExecutor.execute(() -> {
                if (this.getStockDAO().count() == 0) {
                    getStockDAO().insert(new Stock("AAPL"), new Stock("FB"), new Stock("MSFT"));
                }
            });
    }
}
