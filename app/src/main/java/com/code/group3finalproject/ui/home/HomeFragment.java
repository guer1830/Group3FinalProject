package com.code.group3finalproject.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.R;
import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.code.group3finalproject.RSSFeedManager;
import com.code.group3finalproject.RSSFeedRecyclerViewAdapter;
import com.code.group3finalproject.databinding.FragmentHomeBinding;
import com.code.group3finalproject.fetchRSSFeeds;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements  RSSFeedRecyclerViewAdapter.OnItemClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RSSFeedRecyclerViewAdapter RSSRecycleFeed;
    private RSSManagedClasses feedManager = null;
    private View root;

    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
         root = binding.getRoot();


        //Load the saved preferences for the feed manager
        try {
            Log.d("IO", "Loading File");
            FileInputStream fis = getContext().openFileInput("RSS.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            feedManager = (RSSManagedClasses) is.readObject();
            is.close();
            fis.close();
            Log.d("IO", "Loaded File");

            if(feedManager == null){
                feedManager = new RSSManagedClasses();
            }
        } catch (FileNotFoundException e) {
            Log.d("IO", e.toString());
            e.printStackTrace();
            feedManager = new RSSManagedClasses();
        } catch (IOException e) {
            e.printStackTrace();
            feedManager = new RSSManagedClasses();
            Log.d("IO", e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            feedManager = new RSSManagedClasses();
            Log.d("IO", e.toString());
        }

        feedManager.printFeedNames();

        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recycleFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RSSRecycleFeed = new RSSFeedRecyclerViewAdapter(getActivity(), new ArrayList<>());
        RSSRecycleFeed.setClickListener(this);
        recyclerView.setAdapter(RSSRecycleFeed);

        Log.d("IO", "Running Background tasks");
        new fetchRSSFeeds(RSSRecycleFeed, feedManager, root.findViewById(R.id.loadingRSSFeed)).execute((Void) null);

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


    @Override
    public void onPause() {
        super.onPause();
        FileOutputStream fos = null;
        try {
            fos = getContext().openFileOutput("RSS.dat", Activity.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(feedManager);
            os.close();
            fos.close();
            Log.d("Saving", "Saved to output file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}