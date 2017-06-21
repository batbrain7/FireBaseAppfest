package tech.mohitkumar.firebaseappfest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.mohitkumar.firebaseappfest.Activities.UsersActivity;
import tech.mohitkumar.firebaseappfest.MainActivity;
import tech.mohitkumar.firebaseappfest.Models.CardItem;
import tech.mohitkumar.firebaseappfest.R;

/**
 * Created by mohitkumar on 25/12/16.
 */

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.RecycViewHolder>{

    Context context;
    ArrayList<CardItem> arrayList = new ArrayList<CardItem>();

    public RecyclerCardAdapter(Context context, ArrayList<CardItem> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecycViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_card_fragments,parent,false);
        RecycViewHolder recycViewHolder = new RecycViewHolder(view,context,arrayList);
        return recycViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecycViewHolder holder, int position) {
        CardItem cardData = arrayList.get(position);
        holder.textView1.setText(cardData.getTitle());
        holder.textView2.setText(cardData.getText());

        holder.lat.setText(cardData.getLat());
        holder.lng.setText(cardData.getLng());

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UsersActivity.class);
                intent.putExtra("lat",holder.lat.getText().toString());
                intent.putExtra("lng",holder.lng.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecycViewHolder extends RecyclerView.ViewHolder {

        TextView textView1,textView2,lat,lng;
        Context context;
        ArrayList<CardItem> data = new ArrayList<CardItem>();
        String s;
        FrameLayout frame;

        public RecycViewHolder(View itemView, Context context, ArrayList<CardItem> data) {
            super(itemView);
            this.data = data;
            this.context = context;

            textView1 = (TextView) itemView.findViewById(R.id.titleTextView);
            textView2 = (TextView) itemView.findViewById(R.id.contentTextView);
            lat = (TextView) itemView.findViewById(R.id.lat);
            lng = (TextView) itemView.findViewById(R.id.lng);
            frame=(FrameLayout)itemView.findViewById(R.id.frame);
        }


    }
}
