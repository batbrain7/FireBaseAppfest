package tech.mohitkumar.firebaseappfest.Interface;

import android.support.v7.widget.CardView;

/**
 * Created by mohitkumar on 15/06/17.
 */

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    int getCardViewAt(int position);

    int getCount();
}
