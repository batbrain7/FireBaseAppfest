package tech.mohitkumar.firebaseappfest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import tech.mohitkumar.firebaseappfest.Adapter.CardPagerFragmentAdapter;
import tech.mohitkumar.firebaseappfest.CustomAnimations.ShadowTransformer;
import tech.mohitkumar.firebaseappfest.Fragments.CardFragments;
import tech.mohitkumar.firebaseappfest.GeoClient.BackendClient;
import tech.mohitkumar.firebaseappfest.GeoClient.FileUploadService;
import tech.mohitkumar.firebaseappfest.GeoClient.GeoResponse;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ViewPager mViewPager;

    private FloatingActionButton fab;

    private ShadowTransformer mCardShadowTransformer;
    private CardPagerFragmentAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    String lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = View.inflate(MainActivity.this,R.layout.dialog_event,null);
                AlertDialog.Builder db = new AlertDialog.Builder(MainActivity.this);
                db.setView(v);

                final AlertDialog show = db.show();

                final EditText eventName = (EditText)v.findViewById(R.id.event_name);
                final EditText eventVenue = (EditText)v.findViewById(R.id.venue);
                Button create = (Button) v.findViewById(R.id.create);
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FileUploadService service;
                        service = BackendClient.getService();
                        final String venue = eventVenue.getText().toString();
                        final String name = eventName.getText().toString();

                        Call<GeoResponse> call = service.getLatLong(venue,"AIzaSyAkM2PH9AyJG9IFvk-deHIx6OnPJjh-9SQ");
                        call.enqueue(new Callback<GeoResponse>() {
                            @Override
                            public void onResponse(Call<GeoResponse> call, Response<GeoResponse> response) {

                                if(response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                    GeoResponse geoResponse = response.body();
                                    ArrayList<GeoResponse.Results> resultses = geoResponse.getResults();
                                    lat = resultses.get(0).getGeometery().getLocation().getLat();
                                    lng = resultses.get(0).getGeometery().getLocation().getLng();
                                    Log.i("----", "Lat " + lat + " Lng " + lng);
                                    EventDetailsModel model = new EventDetailsModel(name,venue,lat,lng,"1");
                                    reference.child("Events").child(UUID.randomUUID().toString()).setValue(model);
                                    Toast.makeText(MainActivity.this,"Event Created !",Toast.LENGTH_SHORT).show();

                                    show.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(Call<GeoResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this,"Failure! "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });


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
