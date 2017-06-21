package tech.mohitkumar.firebaseappfest.Activities;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
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

import tech.mohitkumar.firebaseappfest.Adapter.RecyclerCardAdapter;
import tech.mohitkumar.firebaseappfest.MainActivity;
import tech.mohitkumar.firebaseappfest.Models.CardItem;
import tech.mohitkumar.firebaseappfest.R;

public class UsersActivity extends AppCompatActivity {


    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    private Button mButton;
    private ViewPager mViewPager;
    ArrayList<CardItem> list = new ArrayList<CardItem>();
    // private ShadowTransformer mCardShadowTransformer;
    //, private CardPagerFragmentAdapter mFragmentCardAdapter;


    DatabaseReference mDatabasechecked;
    // private ShadowTransformer mFragmentCardShadowTransformer;
    ArrayList<CardItem> arrayList = new ArrayList<CardItem>();
    String lat, longi;
    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // mViewPager = (ViewPager) findViewById(R.id.viewPager);
//         mViewPager.setPageMargin(-50);
//        mViewPager.setHorizontalFadingEdgeEnabled(true);
//        mViewPager.setFadingEdgeLength(30);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mDatabasechecked = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //String AgentID= prefs.getString("AgentID","");
        mDatabasechecked.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot file : dataSnapshot.getChildren()) {
                    String name = file.child("name").getValue().toString();
                    String venue = file.child("occupation").getValue().toString();

                    Log.d("NAME",name);

                    CardItem cardData = new CardItem(name, venue,"","");
                    list.add(cardData);


                }
                recyclerView.setHasFixedSize(true);
                adapter = new RecyclerCardAdapter(getApplicationContext(), list);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UsersActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);
                //ArrayAdapter arrayAdapter = new ArrayAdapter(AssignmentChooseAct.this, android.R.layout.simple_list_item_1,fi);

                //fileslv.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Unable to contact the server", Toast.LENGTH_LONG).show();
            }
        });


    }

}
