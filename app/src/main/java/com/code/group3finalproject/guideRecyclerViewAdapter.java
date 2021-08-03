package com.code.group3finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.group3finalproject.RSSClasses.RSSNewsObject;
import com.code.group3finalproject.ui.notifications.NotificationsFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class guideRecyclerViewAdapter extends RecyclerView.Adapter<guideRecyclerViewAdapter.guideViewHolder> {
    private ArrayList<guideObject> recyclerData;
    private LayoutInflater RSSInflater;
    private RSSFeedRecyclerViewAdapter.OnItemClickListener RSSClickListener;
    private Context currentContext;

    public guideRecyclerViewAdapter(Context context, ArrayList<guideObject> data){
        this.RSSInflater = LayoutInflater.from(context);
        this.recyclerData = data;
        this.currentContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public guideViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = RSSInflater.inflate(R.layout.guide_item_layout, parent, false);
        return new guideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull guideViewHolder holder, int position) {
        //Set the data
        String desc = recyclerData.get(position).getTitle();

        Log.d("guide", desc);
        holder.descriptionTextView.setText(desc);

    }

    @Override
    public int getItemCount() {
        if(recyclerData == null){
            return 0;
        }
        return recyclerData.size();
    }

    public void setClickListener(NotificationsFragment notificationsFragment) {
        Log.d("guide", "clicked");
    }


    public class guideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView descriptionTextView;
        public guideViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.guide_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.d("onclick", "onClick " + getLayoutPosition());
            Intent intent = new Intent(currentContext, guide_details.class);
            intent.putExtra("text", recyclerData.get(getLayoutPosition()).getDetails());
            intent.putExtra("gif", recyclerData.get(getLayoutPosition()).getDrawable());
            currentContext.startActivity(intent);
        }
    }
}
