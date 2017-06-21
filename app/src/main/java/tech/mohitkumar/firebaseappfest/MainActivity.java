package tech.mohitkumar.firebaseappfest;

import android.*;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import tech.mohitkumar.firebaseappfest.Adapter.CardPagerFragmentAdapter;
import tech.mohitkumar.firebaseappfest.CustomAnimations.ShadowTransformer;
import tech.mohitkumar.firebaseappfest.Fragments.CardFragments;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 3;
    private Button mButton;
    private ViewPager mViewPager;

    private ShadowTransformer mCardShadowTransformer;
    private CardPagerFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    public static final String TAG = "MainActivity";

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseUser firebaseUser;

    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getCurrentLocation();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

//        if(firebaseUser != null) {
//            Log.d(TAG, "onCreate: " + Globals.latitude + " " + Globals.longitude  + "  " + firebaseUser.getUid());
//            reference.child("Users").child(firebaseUser.getUid()).child("latitude").setValue("2");
//            reference.child("Users").child(firebaseUser.getUid()).child("longitude").setValue("2");
//        }

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

    public void getCurrentLocation() {
        LocationManager locationManager;
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                String result = "\nlatitude = " + location.getLatitude() +
//                                "\nlongitude = " + location.getLongitude();
                Globals.latitude = String.valueOf(location.getLatitude());
                Globals.longitude = String.valueOf(location.getLongitude());

                if(firebaseUser != null) {
                    Log.d(TAG, "onCreate: " + Globals.latitude + " " + Globals.longitude  + "  " + firebaseUser.getUid());
                    reference.child("Users").child(firebaseUser.getUid()).child("latitude").setValue(Globals.latitude);
                    reference.child("Users").child(firebaseUser.getUid()).child("longitude").setValue(Globals.longitude);
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }
}
