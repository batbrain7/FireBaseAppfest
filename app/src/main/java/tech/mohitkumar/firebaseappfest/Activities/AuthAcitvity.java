package tech.mohitkumar.firebaseappfest.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tech.mohitkumar.firebaseappfest.MainActivity;
import tech.mohitkumar.firebaseappfest.R;
import tech.mohitkumar.firebaseappfest.UserDetailsModel;

public class AuthAcitvity extends AppCompatActivity {

    public static final String TAG = "AuthAcitvity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseUser user;
    String uid;

    EditText passwordEt, emailEt;
    Button signUp;

    String email, password, name, phone, company,profileLink;

    EditText nameEt, phoneEt, linkedinEt, companyEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_acitvity);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        attachViews();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

//        startSignIn();

    }

    private void attachViews() {

        passwordEt = (EditText) findViewById(R.id.password);
        emailEt = (EditText) findViewById(R.id.email);
        signUp = (Button) findViewById(R.id.signup_button);
        nameEt = (EditText) findViewById(R.id.name);
        phoneEt = (EditText) findViewById(R.id.pno);
        linkedinEt = (EditText) findViewById(R.id.linkedin);
        companyEt = (EditText) findViewById(R.id.company);

    }

//    private void startSignIn() {
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail:failed", task.getException());
//                            Toast.makeText(AuthAcitvity.this, "Can't sign in",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }

    private void startSignUp() {

        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        company = companyEt.getText().toString();
        profileLink = linkedinEt.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(company) && !TextUtils.isEmpty(profileLink)) {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Toast.makeText(AuthAcitvity.this, "Sign up fail",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else    {
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                UserDetailsModel model = new UserDetailsModel(name, email, phone, profileLink, company);
                                reference.child("Users").child(user.getUid()).setValue(model);
                                Log.d(TAG, "onComplete: Details pushed in firebase");
                                Intent i = new Intent(AuthAcitvity.this, MainActivity.class);
                                startActivity(i);
                            }

                        }
                    });
        } else {
            Toast.makeText(this, "Please Enter Your Details", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
