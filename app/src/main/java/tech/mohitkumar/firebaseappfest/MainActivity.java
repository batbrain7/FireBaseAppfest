package tech.mohitkumar.firebaseappfest;

import android.app.Notification;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import tech.mohitkumar.firebaseappfest.Adapter.CardPagerFragmentAdapter;
import tech.mohitkumar.firebaseappfest.CustomAnimations.ShadowTransformer;
import tech.mohitkumar.firebaseappfest.Fragments.CardFragments;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ViewPager mViewPager;

    private ShadowTransformer mCardShadowTransformer;
    private CardPagerFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setPageMargin(-50);

        mFragmentCardAdapter = new CardPagerFragmentAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        mFragmentCardAdapter.addCardFragment(new CardFragments());
        mFragmentCardAdapter.addCardFragment(new CardFragments());
        mFragmentCardAdapter.addCardFragment(new CardFragments());
        mFragmentCardAdapter.addCardFragment(new CardFragments());

        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mFragmentCardShadowTransformer.enableScaling();
        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
