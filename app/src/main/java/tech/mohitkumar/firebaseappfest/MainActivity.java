package tech.mohitkumar.firebaseappfest;

import android.content.Context;
import android.content.SharedPreferences;
import android.*;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tech.mohitkumar.firebaseappfest.Adapter.CardPagerAdapter;
import tech.mohitkumar.firebaseappfest.Adapter.RecyclerCardAdapter;
import tech.mohitkumar.firebaseappfest.Models.CardItem;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Locale;

import tech.mohitkumar.firebaseappfest.Fragments.CardFragments;
import tech.mohitkumar.firebaseappfest.GeoClient.BackendClient;
import tech.mohitkumar.firebaseappfest.GeoClient.FileUploadService;
import tech.mohitkumar.firebaseappfest.GeoClient.GeoResponse;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 3;
    private Button mButton;
    private ViewPager mViewPager;
    ArrayList<CardItem> list = new ArrayList<CardItem>();
    FloatingActionButton fab;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    AVLoadingIndicatorView indicatorView;

    // private ShadowTransformer mCardShadowTransformer;
    //, private CardPagerFragmentAdapter mFragmentCardAdapter;


    DatabaseReference mDatabasechecked;
    // private ShadowTransformer mFragmentCardShadowTransformer;
    ArrayList<CardItem> arrayList = new ArrayList<CardItem>();
    public static final String TAG = "MainActivity";


    FirebaseUser firebaseUser;

    private boolean mShowingFragments = false;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mViewPager = (ViewPager) findViewById(R.id.viewPager);
//         mViewPager.setPageMargin(-50);
//        mViewPager.setHorizontalFadingEdgeEnabled(true);
//        mViewPager.setFadingEdgeLength(30);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getCurrentLocation();

        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        indicatorView.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = View.inflate(MainActivity.this, R.layout.dialog_event, null);
                AlertDialog.Builder db = new AlertDialog.Builder(MainActivity.this);
                db.setView(v);

                final AlertDialog show = db.show();

                final EditText eventName = (EditText) v.findViewById(R.id.event_name);
                final EditText eventVenue = (EditText) v.findViewById(R.id.venue);
                Button create = (Button) v.findViewById(R.id.create);
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FileUploadService service;
                        service = BackendClient.getService();
                        final String venue = eventVenue.getText().toString();
                        final String name = eventName.getText().toString();

                        Call<GeoResponse> call = service.getLatLong(venue, "AIzaSyAkM2PH9AyJG9IFvk-deHIx6OnPJjh-9SQ");
                        call.enqueue(new Callback<GeoResponse>() {
                            @Override
                            public void onResponse(Call<GeoResponse> call, Response<GeoResponse> response) {

                                if (response.isSuccessful()) {
                                    GeoResponse geoResponse = response.body();
                                    ArrayList<GeoResponse.Results> resultses = geoResponse.getResults();
                                    lat = resultses.get(0).getGeometery().getLocation().getLat();
                                    lng = resultses.get(0).getGeometery().getLocation().getLng();
                                    Log.i("----", "Lat " + lat + " Lng " + lng);
                                    EventDetailsModel model = new EventDetailsModel(name, venue, lat, lng, "1");
                                    reference.child("Events").child(UUID.randomUUID().toString()).setValue(model);
                                    Toast.makeText(MainActivity.this, "Event Created !", Toast.LENGTH_SHORT).show();

                                    show.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(Call<GeoResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });


            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mDatabasechecked = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //String AgentID= prefs.getString("AgentID","");
        mDatabasechecked.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot file : dataSnapshot.getChildren()) {
                    String name = file.child("poi_nick_name").getValue().toString();
                    String venue = file.child("venue").getValue().toString();
                    String lat = file.child("lat").getValue().toString();
                    String lng = file.child("lng").getValue().toString();

                    Log.d("NAME", name);

                    CardItem cardData = new CardItem(name, venue, lat, lng);
                    list.add(cardData);


                }
                recyclerView.setHasFixedSize(true);
                adapter = new RecyclerCardAdapter(getApplicationContext(), list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
                //ArrayAdapter arrayAdapter = new ArrayAdapter(AssignmentChooseAct.this, android.R.layout.simple_list_item_1,fi);

                //fileslv.setAdapter(arrayAdapter);
                indicatorView.hide();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Unable to contact the server", Toast.LENGTH_LONG).show();
            }
        });


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

                if (firebaseUser != null) {
                    Log.d(TAG, "onCreate: " + Globals.latitude + " " + Globals.longitude + "  " + firebaseUser.getUid());
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
