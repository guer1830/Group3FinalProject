
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

    public RSSManagerRecyclerViewAdapter(Context context, ArrayList<RSSNewsFeed> data){
        this.RSSInflater = LayoutInflater.from(context);
        this.recyclerData = data;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = RSSInflater.inflate(R.layout.rss_management_item_layout, parent, false);
        return new ViewHolder(view);
    }

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


    @Override
    public int getItemCount() {
        if(recyclerData == null){
            return 0;
        }
        return recyclerData.size();
    }

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

    RSSNewsFeed getItem(int id){
        return recyclerData.get(id);
    }

    public void setClickListener(OnItemClickListener itemClickListener){
        this.RSSClickListener = itemClickListener;
    }

    public void setData(ArrayList<RSSNewsFeed> data){
        this.recyclerData = data;
        notifyDataSetChanged();
    }

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
