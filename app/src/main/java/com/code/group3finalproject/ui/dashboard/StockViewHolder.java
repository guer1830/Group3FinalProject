package com.code.group3finalproject.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;

public class StockViewHolder extends RecyclerView.ViewHolder {
    public TextView stockSymbol;

    public StockViewHolder(View itemView) {
        super(itemView);
        stockSymbol = itemView.findViewById(R.id.stockSymbol);
    }

    public void bind(String text) { stockSymbol.setText(text); }

    static StockViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_item_layout, parent, false);
        return new StockViewHolder(view);
    }
}