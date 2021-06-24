package com.code.group3finalproject.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.code.group3finalproject.RSSClasses.RSSNewsObject;
import com.code.group3finalproject.RSSFeedRecyclerViewAdapter;
import com.code.group3finalproject.databinding.FragmentHomeBinding;
import com.code.group3finalproject.fetchRSSFeeds;


import java.util.ArrayList;

public class HomeFragment extends Fragment implements  RSSFeedRecyclerViewAdapter.OnItemClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RSSFeedRecyclerViewAdapter RSSRecycleFeed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recycleFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RSSRecycleFeed = new RSSFeedRecyclerViewAdapter(getActivity(), new ArrayList<>());
        RSSRecycleFeed.setClickListener(this);
        recyclerView.setAdapter(RSSRecycleFeed);

        new fetchRSSFeeds(RSSRecycleFeed).execute((Void) null);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("message","clicked in main");
    }



}