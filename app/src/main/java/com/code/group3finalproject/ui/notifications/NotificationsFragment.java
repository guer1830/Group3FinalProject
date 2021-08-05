package com.code.group3finalproject.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;
import com.code.group3finalproject.databinding.FragmentNotificationsBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        StockDatabase db = StockDatabase.getInstance(root.getContext());
        List<Stock> stockList = new ArrayList<>();
        NotificationRecycleAdapter notificationRecycleAdapter = new NotificationRecycleAdapter(stockList);

        db.getStockDAO().getAll().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                stockList.addAll(stocks);
                notificationRecycleAdapter.notifyDataSetChanged();
            }
        });


        final RecyclerView recyclerView = binding.rvNotifs;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //StockRecycleAdapter.setClickListener(this);
        recyclerView.setAdapter(notificationRecycleAdapter);

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}