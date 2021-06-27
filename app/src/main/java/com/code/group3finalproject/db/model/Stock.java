package com.code.group3finalproject.db.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Stock {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "symbol")
    private String symbol;

    public Stock(@NonNull String symbol) {
        this.symbol = symbol;
    }

    @NotNull
    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(@NonNull String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Stock))
            return false;

        Stock stock = (Stock) obj;
        return this.symbol.equalsIgnoreCase(stock.symbol);
    }

    @NonNull
    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                '}';
    }
}
