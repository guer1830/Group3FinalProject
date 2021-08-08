package com.code.group3finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.RSSClasses.RSSNewsObject;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class RSSFeedRecyclerViewAdapter extends RecyclerView.Adapter<RSSFeedRecyclerViewAdapter.ViewHolder> {
    private ArrayList<RSSNewsObject>  recyclerData;
    private LayoutInflater RSSInflater;
    private OnItemClickListener RSSClickListener;
    private Context currentContext;

    /**
     * Constructor
     * @param context
     * Sets the context variable
     * @param data
     * An Arraylist of RRSNewsOjects that will be displayed in the recycler view
     */
    public RSSFeedRecyclerViewAdapter(Context context, ArrayList<RSSNewsObject> data){
        this.RSSInflater = LayoutInflater.from(context);
        this.recyclerData = data;
        this.currentContext = context;
    }

    /**
     * Function that inflates row item layouts
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = RSSInflater.inflate(R.layout.rss_news_item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Function that binds the row layout elements to the
     * appropriate values
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RSSNewsObject myObj = recyclerData.get(position);
        holder.TitleTextView.setText(myObj.getNewsTitle());
        holder.descriptionTextView.setText(myObj.getNewsDescription());

        //If the news item has no image use a random placeholder image
        //holder.RecyclerImageView.setImageBitmap();
        holder.RecyclerImageView.setImageBitmap(getRandomImage());
    }

    /**
     * Function that returns the number of rows in the recycler view
     * @return
     * An integer value
     */
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

        /**
         * Handle row item click
         * @param v
         */
        @Override
        public void onClick(View v) {

            Log.d("onclick", "onClick " + getLayoutPosition());

            Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( recyclerData.get(getLayoutPosition()).getArticleURL() ) );

            v.getContext().startActivity( browse );
        }
    }

    /**
     * Gets the data item at the inputted index
     * @param id
     * @return
     */
    RSSNewsObject getItem(int id){
        return recyclerData.get(id);
    }

    public void setClickListener(OnItemClickListener itemClickListener){
        this.RSSClickListener = itemClickListener;
    }

    /**
     * Sets the recycler view data
     * @param data
     */
    public void setData(ArrayList<RSSNewsObject> data){
        this.recyclerData = data;
        notifyDataSetChanged();
    }

    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }


    /**
     * Function that loads a random bitmap image from the resources folder
     * @return
     */
    public Bitmap getRandomImage(){
        ArrayList<Object> images = new ArrayList<>(
                Arrays.asList(R.drawable.big_city,
                        R.drawable.cityscape,
                        R.drawable.desk,
                        R.drawable.graph_laptop,
                        R.drawable.laptop,
                        R.drawable.meeting,
                        R.drawable.notepad,
                        R.drawable.skyscraper,
                        R.drawable.suit));

        int index = (int)(Math.random() * images.size());


        Bitmap b = BitmapFactory.decodeResource(this.currentContext.getResources(), (Integer) images.get(index));

        return Bitmap.createScaledBitmap(b, 120, 120, false);
    }
}
