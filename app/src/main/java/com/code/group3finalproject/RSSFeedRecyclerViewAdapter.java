package com.code.group3finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.RSSClasses.RSSNewsObject;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RSSFeedRecyclerViewAdapter extends RecyclerView.Adapter<RSSFeedRecyclerViewAdapter.ViewHolder> {
    private ArrayList<RSSNewsObject>  recyclerData;
    private LayoutInflater RSSInflater;
    private OnItemClickListener RSSClickListener;

    public RSSFeedRecyclerViewAdapter(Context context, ArrayList<RSSNewsObject> data){
        this.RSSInflater = LayoutInflater.from(context);
        this.recyclerData = data;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = RSSInflater.inflate(R.layout.rss_news_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RSSNewsObject myObj = recyclerData.get(position);
        holder.TitleTextView.setText(myObj.getNewsTitle());
        holder.descriptionTextView.setText(myObj.getNewsDescription());
        //holder.RecyclerImageView.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        if(recyclerData == null){
            return 0;
        }
        return recyclerData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView descriptionTextView;
        TextView TitleTextView;
        ImageView RecyclerImageView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.recycleObjectDescription);
            TitleTextView = itemView.findViewById(R.id.recycleObjectTitle);
            RecyclerImageView = itemView.findViewById(R.id.recycleObjectImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.d("onclick", "onClick " + getLayoutPosition());

            Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( recyclerData.get(getLayoutPosition()).getArticleURL() ) );

            v.getContext().startActivity( browse );
        }
    }

    RSSNewsObject getItem(int id){
        return recyclerData.get(id);
    }

    public void setClickListener(OnItemClickListener itemClickListener){
        this.RSSClickListener = itemClickListener;
    }

    public void setData(ArrayList<RSSNewsObject> data){
        this.recyclerData = data;
        notifyDataSetChanged();
    }

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
