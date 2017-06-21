package tech.mohitkumar.firebaseappfest.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.mohitkumar.firebaseappfest.Interface.CardAdapter;
import tech.mohitkumar.firebaseappfest.Models.CardItem;
import tech.mohitkumar.firebaseappfest.R;

/**
 * Created by mohitkumar on 15/06/17.
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardItem> mData = new ArrayList<CardItem>();
    private float mBaseElevation;


    public CardPagerAdapter(ArrayList<CardItem> arrayList) {
        mData = arrayList;
    }

    public void addCardItem(CardItem item) {
        mData.add(item);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public int getCardViewAt(int position) {
        return position;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_card_fragments, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

}
