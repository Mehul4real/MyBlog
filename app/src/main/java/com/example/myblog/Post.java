package com.example.myblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.TwoStatePreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Post extends AppCompatActivity {

    private ImageButton mImgSelect;
    private static final int Gallery_Request=1;
    private EditText mTitle;
    private EditText mDesc;
    private Button mSubmit;
    private Uri mImgUri= null;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImgSelect= findViewById(R.id.imgSelect);
        mTitle= findViewById(R.id.title);
        mDesc= findViewById(R.id.desc);

        /*StorageRefrence sirf file store krne k kaam aata h
        current root directory ka address*/
        mStorage= FirebaseStorage.getInstance().getReference();

        /*FirebaseDatabase sirf data store krne k kaam aata h
        current root directory ka address usme blog naam ka child*/
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mSubmit= findViewById(R.id.submit);

        mProgress=new ProgressDialog(this);
        mImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Request);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

            private void startPosting() {

                mProgress.setMessage("Posting...");
                mProgress.show();
                final String title=mTitle.getText().toString().trim();
                final String desc=mDesc.getText().toString().trim();

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && mImgUri != null){

                    /*StorageRefrence ka object bnaya usko kese or kaha store krna h ye btaya
                    * mStorage k andar ek child bna(folder), us foldar k andar ek or child h or yha pr apan uska naam de rahe h as we dont want to store every thing in root
                    *mImgUri.getLastPathSegment()-> mImgUri ka naam read kr k deta h*/
                    StorageReference filepath =mStorage.child("Blog_Images").child(mImgUri.getLastPathSegment());

                    /* filepath object k andar file rakhi */
                    filepath.putFile(mImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            /*TO get file URL after uploading it in storage*/
                            Uri dounloadUri = taskSnapshot.getUploadSessionUri();

                            /*DatabaseReference ka object bnaya
                            * push()-> generates a random id*/
                            DatabaseReference newPost = mDatabase.push();

                            /*us randam id k andar ye values store hogi*/
                            newPost.child("title").setValue(title);
                            newPost.child("desc").setValue(desc);
                            newPost.child("image").setValue(dounloadUri.toString());

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            mProgress.dismiss();
                        }
                    });

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== Gallery_Request && resultCode==RESULT_OK){

            mImgUri=data.getData();
            mImgSelect.setImageURI(mImgUri);

        }
    }
}
