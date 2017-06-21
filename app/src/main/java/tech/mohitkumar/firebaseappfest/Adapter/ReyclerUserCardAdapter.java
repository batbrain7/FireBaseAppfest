package tech.mohitkumar.firebaseappfest.Adapter;

/**
 * Created by mohitkumar on 21/06/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.mohitkumar.firebaseappfest.Models.UserItems;
import tech.mohitkumar.firebaseappfest.R;

/**
 * Created by mohitkumar on 25/12/16.
 */

public class ReyclerUserCardAdapter extends RecyclerView.Adapter<ReyclerUserCardAdapter.RecycViewHolder>{

    Context context;
    ArrayList<UserItems> arrayList = new ArrayList<UserItems>();

    public ReyclerUserCardAdapter(Context context, ArrayList<UserItems> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public RecycViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_layout,parent,false);
        RecycViewHolder recycViewHolder = new RecycViewHolder(view,context,arrayList);
        return recycViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecycViewHolder holder, int position) {
        UserItems cardData = arrayList.get(position);
        holder.textView1.setText(cardData.getName());
        holder.textView2.setText(cardData.getOccupation());

        holder.profile.setText(cardData.getProfile());
        holder.pno.setText(cardData.getPno());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecycViewHolder extends RecyclerView.ViewHolder {

        TextView textView1,textView2,profile,pno;
        Context context;
        ArrayList<UserItems> data = new ArrayList<UserItems>();
        String s;
        FrameLayout frame;

        public RecycViewHolder(View itemView, Context context, ArrayList<UserItems> data) {
            super(itemView);
            this.data = data;
            this.context = context;

            textView1 = (TextView) itemView.findViewById(R.id.titleTextView);
            textView2 = (TextView) itemView.findViewById(R.id.contentTextView);
            profile = (TextView) itemView.findViewById(R.id.profile_link);
            pno = (TextView) itemView.findViewById(R.id.p_no);
            frame=(FrameLayout)itemView.findViewById(R.id.frame);
        }


    }
}

