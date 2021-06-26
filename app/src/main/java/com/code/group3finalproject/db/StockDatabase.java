package com.code.group3finalproject.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.code.group3finalproject.db.dao.StockDAO;
import com.code.group3finalproject.db.model.Stock;

@Database(entities = {Stock.class}, version = 1)
public abstract class StockDatabase extends RoomDatabase {
    public abstract StockDAO getStockDAO();

    private static StockDatabase stockDB;

    public static StockDatabase getInstance(Context context) {
        if (null == stockDB) {
            stockDB = buildDatabaseInstance(context);
        }
        return stockDB;
    }

    private static StockDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, StockDatabase.class, "StockDB.db")
                .enableMultiInstanceInvalidation().build();
    }

    public void cleanUp() {
        stockDB = null;
    }
}
