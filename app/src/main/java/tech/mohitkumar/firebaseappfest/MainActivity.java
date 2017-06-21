package tech.mohitkumar.firebaseappfest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import tech.mohitkumar.firebaseappfest.Adapter.CardPagerFragmentAdapter;
import tech.mohitkumar.firebaseappfest.CustomAnimations.ShadowTransformer;
import tech.mohitkumar.firebaseappfest.Fragments.CardFragments;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ViewPager mViewPager;

    private FloatingActionButton fab;

    private ShadowTransformer mCardShadowTransformer;
    private CardPagerFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = View.inflate(MainActivity.this,R.layout.dialog_event,null);
                AlertDialog.Builder db = new AlertDialog.Builder(MainActivity.this);
                db.setView(v);
                db.create().show();
            }
        });
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
