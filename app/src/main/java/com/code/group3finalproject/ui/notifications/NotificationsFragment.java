package com.code.group3finalproject.ui.notifications;

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
import com.code.group3finalproject.RSSFeedRecyclerViewAdapter;
import com.code.group3finalproject.databinding.FragmentNotificationsBinding;
import com.code.group3finalproject.fetchRSSFeeds;
import com.code.group3finalproject.guideObject;
import com.code.group3finalproject.guideRecyclerViewAdapter;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private guideRecyclerViewAdapter guideRecycleFeed;
    private ArrayList<guideObject> descData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        guideObject newsObject = new guideObject("News","Scroll", "example_gif");
        //Add data for each section
        descData = new ArrayList<guideObject>();
        descData.add(newsObject);


        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.guideFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        guideRecycleFeed = new guideRecyclerViewAdapter(getActivity(), descData);
        guideRecycleFeed.setClickListener(this);
        recyclerView.setAdapter(guideRecycleFeed);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}