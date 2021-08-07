package com.code.group3finalproject.ui.notifications;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;
import com.code.group3finalproject.StockDetailActivity;
import com.code.group3finalproject.db.model.Stock;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NotificationRecycleAdapter extends RecyclerView.Adapter<NotificationRecycleAdapter.ViewHolder> {

    private List<Stock> stocks;

    public NotificationRecycleAdapter(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_item_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.stockSymbol.setText("User added a new stock ("+stocks.get(position).getSymbol()+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(view.getClass().getSimpleName(),
                        holder.stockSymbol.getText().toString());
                Intent intent = new Intent(view.getContext(), StockDetailActivity.class);
                intent.putExtra("StockSymbol", holder.stockSymbol.getText().toString());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stockSymbol;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stockSymbol = itemView.findViewById(R.id.stockSymbol);
        }
    }
}