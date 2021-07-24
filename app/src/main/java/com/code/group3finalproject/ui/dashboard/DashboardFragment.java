package com.code.group3finalproject.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.code.group3finalproject.AddStockActivity;
import com.code.group3finalproject.R;
import com.code.group3finalproject.databinding.FragmentDashboardBinding;
import com.code.group3finalproject.db.StockDatabase;
import com.code.group3finalproject.db.model.Stock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {

    private static final int NEW_STOCK_ACTIVITY_REQUEST_CODE = 10;
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private StockRecycleAdapter StockRecycleAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Stock Dashboard", "requestCode: " + requestCode + "resultCode: " + resultCode);
        if (requestCode == NEW_STOCK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String stockSymbol = data.getStringExtra(AddStockActivity.EXTRA_REPLY);
            if (!stockSymbol.isEmpty()) {
                Stock mStock = new Stock(stockSymbol);
                dashboardViewModel.insert(mStock);
                return;
            }
        }
            Toast.makeText(
                    this.getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.stockList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StockRecycleAdapter = new StockRecycleAdapter();
        recyclerView.setAdapter(StockRecycleAdapter);
        dashboardViewModel.getAllStocks().observe(getViewLifecycleOwner(), stocks -> StockRecycleAdapter.refreshData(stocks));

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
                    dashboardViewModel.delete(StockRecycleAdapter.getItem(position));
                    StockRecycleAdapter.deleteStock(position);
                }
            }

            @Override
            public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlag = ItemTouchHelper.START;
                return makeMovementFlags(dragFlags,swipeFlag);
            }

            @Override
            public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int itemHeight = itemView.getHeight();

                boolean isCancelled = dX == 0 && !isCurrentlyActive;

                if (isCancelled) {
                    Paint mClearIcon = new Paint();
                    mClearIcon.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    c.drawRect(itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), mClearIcon);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                ColorDrawable mBackground = new ColorDrawable(Color.RED);
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                mBackground.draw(c);

                Drawable deleteDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_delete_24);
                int intrinsicWidth = deleteDrawable.getIntrinsicWidth();
                int intrinsicHeight = deleteDrawable.getIntrinsicHeight();
                int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop + intrinsicHeight;


                deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                deleteDrawable.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public float getSwipeThreshold(@NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
                return 0.8f;
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeCallback);
        touchHelper.attachToRecyclerView(recyclerView);

        final SwipeRefreshLayout refreshLayout = binding.stockListSwipeContainer;
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO Refresh quote endpoint
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
                dashboardViewModel.searchStocks(newText.toUpperCase()).observe(getViewLifecycleOwner(), stocks -> StockRecycleAdapter.refreshData(stocks));
                return true;
            }
        });

        MenuItem addStock = menu.findItem(R.id.addStock);
        addStock.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Log.d("Dashboard Fragment", "Add Stock button clicked");
                Intent intent = new Intent(getContext(), AddStockActivity.class);
                startActivityForResult(intent, NEW_STOCK_ACTIVITY_REQUEST_CODE);
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}