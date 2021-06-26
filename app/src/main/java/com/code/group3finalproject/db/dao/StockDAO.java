package com.code.group3finalproject.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.code.group3finalproject.db.model.Stock;

import java.util.List;

@Dao
public interface StockDAO {
    @Query("SELECT * from Stock")
    List<Stock> getAll();

    /*
     * Insert the object in database
     * @param stock, object to be inserted
     */
    @Insert
    void insert(Stock stock);

    /*
     * update the object in database
     * @param stock, object to be updated
     */
    @Update
    void update(Stock stock);

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
}
