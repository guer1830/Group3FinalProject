package com.code.group3finalproject.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.code.group3finalproject.db.model.Stock;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface StockDAO {
    /*
     * Return all stock symbols sorted alphabetically
     */
    @Query("SELECT * from Stock ORDER BY symbol ASC")
    LiveData<List<Stock>> getAll();

    @Query("SELECT * FROM Stock WHERE symbol LIKE '%' || :stockSymbol || '%'")
    LiveData<List<Stock>> searchValue(String stockSymbol);

    /*
     * Insert the object in database
     * @param stock, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock stock);

    /*
     * Insert list of objects in database
     * @param stock, array of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock... stock);

    /*
     * delete the object from database
     * @param stock, object to be deleted
     */
    @Delete
    void delete(Stock stock);

    /*
     * delete list of objects from database
     * @param stock, array of objects to be deleted
     */
    @Delete
    void delete(Stock... stock);

    /**
     * Counts number of stocks in the table.
     *
     * @return The number of stocks.
     */
    @Query("SELECT COUNT(*) FROM Stock ")
    int count();
}
