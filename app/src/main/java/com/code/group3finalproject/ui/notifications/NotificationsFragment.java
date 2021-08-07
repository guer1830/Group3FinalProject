package com.code.group3finalproject.ui.notifications;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;
import com.code.group3finalproject.databinding.FragmentNotificationsBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import org.jetbrains.annotations.NotNull;

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
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.basic_help, menu);

        MenuItem helpMenu = menu.findItem(R.id.appHelpBasic);
        helpMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d("Dashboard Fragment", "Help menu button clicked");
                AlertDialog.Builder help_dialog_builder = new AlertDialog.Builder(getContext());
                LayoutInflater help_dialog_inflater = getActivity().getLayoutInflater();
                View content = help_dialog_inflater.inflate(R.layout.help_dialog,null);
                help_dialog_builder.setView(content);
                //EditText dialogText = (EditText) content.findViewById(R.id.dialog_snack_text);
                //dialogText.setText(action_one_message);
                help_dialog_builder.setTitle(R.string.help_dialog_title);
                help_dialog_builder.setPositiveButton(R.string.button_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog help_dialog = help_dialog_builder.create();
                help_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                help_dialog.show();
                return true;
            }
        });
    }
}