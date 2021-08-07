package com.code.group3finalproject.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;

public class StockViewHolder extends RecyclerView.ViewHolder {
    public TextView stockSymbol;
    public TextView stockPrice;

    public StockViewHolder(View itemView) {
        super(itemView);
        stockSymbol = itemView.findViewById(R.id.stockSymbol);
        stockPrice = itemView.findViewById(R.id.stockPrice);
    }

    public void bind(String text) {
        stockSymbol.setText(text);
        stockPrice.setText(text);
    }

    static StockViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_item_layout, parent, false);
        return new StockViewHolder(view);
    }
}