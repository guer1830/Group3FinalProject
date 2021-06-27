package com.code.group3finalproject.ui.dashboard;

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
import com.code.group3finalproject.db.dao.StockDAO;
import com.code.group3finalproject.db.model.Stock;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private com.code.group3finalproject.StockRecycleAdapter StockRecycleAdapter;

    ArrayList<String> stocks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        stocks = new ArrayList<>();
        stocks.add("AAPL");
        stocks.add("IBM");
        stocks.add("MSFT");

        final RecyclerView recyclerView = binding.stockList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StockRecycleAdapter = new StockRecycleAdapter(stocks);
        //StockRecycleAdapter.setClickListener(this);
        recyclerView.setAdapter(StockRecycleAdapter);

       /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}