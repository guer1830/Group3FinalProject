package com.code.group3finalproject.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;
import com.code.group3finalproject.RSSFeedRecyclerViewAdapter;
import com.code.group3finalproject.StockRecycleAdapter;
import com.code.group3finalproject.databinding.FragmentDashboardBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.dao.StockDAO;
import com.code.group3finalproject.db.model.Stock;

import java.util.ArrayList;
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
        //StockRecycleAdapter.setClickListener(this);
        recyclerView.setAdapter(StockRecycleAdapter);

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