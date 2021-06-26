package com.code.group3finalproject.db.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class Stock {

    @PrimaryKey
    private String symbol;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
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
    @NotNull
    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                '}';
    }
}
