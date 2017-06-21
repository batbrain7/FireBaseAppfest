package tech.mohitkumar.firebaseappfest.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import tech.mohitkumar.firebaseappfest.MainActivity;
import tech.mohitkumar.firebaseappfest.R;

public class SelectPhotoActivity extends AppCompatActivity {

    CircleImageView imageView;

    FirebaseDatabase database;
    DatabaseReference reference;
    StorageReference mStorageRef;
    FirebaseUser firebaseUser;

    Button uploadBtn;

    String encodedImage;

    public static final String TAG = "SelectPhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_photo);

        database = FirebaseDatabase.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        reference = database.getReference();

        uploadBtn = (Button) findViewById(R.id.upload);

        imageView = (CircleImageView) findViewById(R.id.profile_image);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(encodedImage))    {
                    Intent i = new Intent(SelectPhotoActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//

            if (data.getData() != null) {
                //Log.v("uri",data.getData().toString());
                Bitmap photo = null;
                try {

                    photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                            , data.getData());
                    imageView.setImageBitmap(photo);
                    imageView.setBorderWidth(8);

                    getStringImage(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            Log.d("Image URI", "in here");

        } else {
            Toast.makeText(getApplicationContext(), "Image not uploaded", Toast.LENGTH_LONG).show();
        }
    }

    public void getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Log.d("COMPRESS", "COmpressing");
        //    progressDialog.show();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.d(TAG, "getStringImage: " + encodedImage);

        reference.child("Users").child(firebaseUser.getUid()).child("imageData").setValue(encodedImage);

    }

}
