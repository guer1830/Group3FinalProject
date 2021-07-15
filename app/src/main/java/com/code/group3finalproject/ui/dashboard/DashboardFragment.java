package com.code.group3finalproject.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.code.group3finalproject.AddStockActivity;
import com.code.group3finalproject.R;
import com.code.group3finalproject.StockRecycleAdapter;
import com.code.group3finalproject.databinding.FragmentDashboardBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private StockRecycleAdapter StockRecycleAdapter;
    StockDatabase db;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Stock Dashboard", "requestCode: " + requestCode + "resultCode: " + resultCode);
        if (requestCode == 10) {
            StockRecycleAdapter.refreshData(db.getStockDAO().getAll());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = StockDatabase.getInstance(root.getContext());

        List<Stock> stockList = db.getStockDAO().getAll();

        if (stockList.isEmpty()) {
            createInitialData(root.getContext());
            stockList = db.getStockDAO().getAll();
        }

        final RecyclerView recyclerView = binding.stockList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StockRecycleAdapter = new StockRecycleAdapter(stockList);
        recyclerView.setAdapter(StockRecycleAdapter);

        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.START) {
                    int position = viewHolder.getAdapterPosition();
                    Log.i("Stock Dashboard", "position to delete:" + position);
                    db.getStockDAO().delete(StockRecycleAdapter.getItem(position));
                    StockRecycleAdapter.deleteStock(position);
                }
            }

            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlag = ItemTouchHelper.START;
                return makeMovementFlags(dragFlags,swipeFlag);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        final SwipeRefreshLayout refreshLayout = binding.stockListSwipeContainer;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                StockRecycleAdapter.refreshData(db.getStockDAO().getAll());
                refreshLayout.setRefreshing(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        Log.i("Stock List", "Creating Menu");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.stock_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.stock_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("Stock Search", "text: " + newText);
                StockRecycleAdapter.filter(db.getStockDAO().getAll(), newText);
                return true;
            }
        });

        MenuItem addStock = menu.findItem(R.id.addStock);
        addStock.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d("Dashboard Fragment", "Add Stock button clicked");
                Intent intent = new Intent(getContext(), AddStockActivity.class);
                startActivityForResult(intent, 10);
                return true;
            }
        });
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