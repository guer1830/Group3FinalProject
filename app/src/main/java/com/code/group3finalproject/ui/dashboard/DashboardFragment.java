package com.code.group3finalproject.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.code.group3finalproject.AddStockActivity;
import com.code.group3finalproject.StockRecycleAdapter;
import com.code.group3finalproject.databinding.FragmentDashboardBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private com.code.group3finalproject.StockRecycleAdapter StockRecycleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        StockDatabase db = StockDatabase.getInstance(root.getContext());

        List<Stock> stockList = db.getStockDAO().getAll();

        if (stockList.isEmpty()) {
            createInitialData(root.getContext());
            stockList = db.getStockDAO().getAll();
        }

        final RecyclerView recyclerView = binding.stockList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StockRecycleAdapter = new StockRecycleAdapter(stockList);
        recyclerView.setAdapter(StockRecycleAdapter);

        ImageButton addStock = binding.imgAddButton;
        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Dashboard Fragment", "Add Stock button clicked");
                Intent intent = new Intent(view.getContext(), AddStockActivity.class);
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout refreshLayout = binding.stockListSwipeContainer;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                StockRecycleAdapter.refreshData(db.getStockDAO().getAll());
                refreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    private void createInitialData(Context context) {
        StockDatabase db = StockDatabase.getInstance(context);
        Stock stock1 = new Stock("AAPL");
        db.getStockDAO().insert(stock1);
        Stock stock2 = new Stock("IBM");
        db.getStockDAO().insert(stock2);
        Stock stock3 = new Stock("MSFT");
        db.getStockDAO().insert(stock3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}