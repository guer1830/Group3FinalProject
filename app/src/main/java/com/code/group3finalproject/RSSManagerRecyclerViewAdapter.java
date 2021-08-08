
package com.code.group3finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.code.group3finalproject.RSSClasses.RSSNewsFeed;
import com.code.group3finalproject.RSSClasses.RSSNewsObject;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RSSManagerRecyclerViewAdapter extends RecyclerView.Adapter<RSSManagerRecyclerViewAdapter.ViewHolder> {
    private ArrayList<RSSNewsFeed> recyclerData;
    private LayoutInflater RSSInflater;
    private OnItemClickListener RSSClickListener;
    private Context currentContext;

    /**
     *Constructor
     * @param context
     * Reference to the calling context
     * @param data
     * Data that will be loaded into the RSS Manager recycler view
     */
    public RSSManagerRecyclerViewAdapter(Context context, ArrayList<RSSNewsFeed> data){
        this.RSSInflater = LayoutInflater.from(context);
        this.recyclerData = data;
        this.currentContext = context;
    }

    /**
     *Function that inflates the item layout
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = RSSInflater.inflate(R.layout.rss_management_item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     *Function that loads data into the item layout
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RSSNewsFeed myObj = recyclerData.get(position);
        holder.feedTitleTestView.setText(myObj.getRSSFeedName());



        if (myObj.isIncluded()){
            holder.inclusionButton.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.inclusionButton.setBackgroundColor(Color.RED);
        }


    }

    /**
     *Returns the number of rows in the recycler view
     * @return
     */
    @Override
    public int getItemCount() {
        if(recyclerData == null){
            return 0;
        }
        return recyclerData.size();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView feedTitleTestView;
        Button inclusionButton;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            feedTitleTestView = itemView.findViewById(R.id.managerRSSFeedName);
            inclusionButton = itemView.findViewById(R.id.includeFeedButton);
            //itemView.setOnClickListener(this);
            inclusionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.d("onclick", "onClick " + getLayoutPosition());
            RSSNewsFeed myObj = recyclerData.get(getLayoutPosition());
            myObj.toggleInclusion();
            Button currentButton = v.findViewById(R.id.includeFeedButton);

            if (myObj.isIncluded()){
                currentButton.setBackgroundColor(Color.GREEN);
            }
            else{
                currentButton.setBackgroundColor(Color.RED);
            }
        }
    }

    /**
     *Returns the item at index id
     * @param id
     * Index of object
     * @return
     * RRS News Feed object is returned
     */
    RSSNewsFeed getItem(int id){
        return recyclerData.get(id);
    }

    /**
     *Sets click listener
     * @param itemClickListener
     */
    public void setClickListener(OnItemClickListener itemClickListener){
        this.RSSClickListener = itemClickListener;
    }

    /**
     * Sets recycler view data to the inputted data
     * @param data
     */
    public void setData(ArrayList<RSSNewsFeed> data){
        this.recyclerData = data;
        notifyDataSetChanged();
    }

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
