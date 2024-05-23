package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.chat.databinding.ActivityChatBinding;
import com.example.chat.databinding.ActivityViewAccountBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewAccount extends AppCompatActivity {
    DatabaseReference databaseReference;
    ActivityViewAccountBinding binding;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
        binding= ActivityViewAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

String userName= getIntent().getStringExtra("id");
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               userModel= snapshot.getValue(UserModel.class);
                binding.VAC.setText("Name : "+userModel.getUserName());
                binding.UpAC.setText("Email : "+userModel.getUserEmail());

                StorageReference ref = FirebaseStorage.getInstance().getReference().child("profile").child(userModel.getUserId());
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Picasso.with(getApplicationContext())
                                .load(uri)
                                .resize(100, 100)
                                .centerCrop()
                                .into(binding.Img);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}