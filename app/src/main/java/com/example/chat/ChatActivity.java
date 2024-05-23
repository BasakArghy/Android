package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.chat.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
ActivityChatBinding binding;
String receiverId;
String senderRoom,receiverRoom,name;
MessageAdapter messageAdapter;
String img="0";
    public Uri filePath=null;

    private final int PICK_IMAGE_REQUEST = 22;
DatabaseReference databaseReferenceSender,databaseReferenceReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("color") )
        {
            //Toast.makeText(this,getIntent().getStringExtra("color"), Toast.LENGTH_SHORT).show();
            if(getIntent().getStringExtra("color").equals("1")){
                binding.chatActivity.setBackground(getResources().getDrawable(R.drawable.a1));

            } else if (getIntent().getStringExtra("color").equals("2")) {
                binding.chatActivity.setBackground(getResources().getDrawable(R.drawable.a2));
            }
            else if (getIntent().getStringExtra("color").equals("3")) {
                binding.chatActivity.setBackground(getResources().getDrawable(R.drawable.a3));
            }
            else if (getIntent().getStringExtra("color").equals("4")) {
                binding.chatActivity.setBackground(getResources().getDrawable(R.drawable.a5));
            }
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiverId=getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);

        senderRoom = FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom = receiverId+FirebaseAuth.getInstance().getUid();

        messageAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(FirebaseAuth.getInstance().getUid()).child(receiverId);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverId).child(FirebaseAuth.getInstance().getUid());
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                messageAdapter.clear();
                long s=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                    messageAdapter.notifyDataSetChanged();
                    binding.recycler.scrollToPosition((int) s);
                    s++;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });











        binding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.edtMessage.getText().toString();
                Date date = new Date();
                String messageId = date.toString();
                if(message.trim().length()>0){
                    sendMessage(message,messageId);
                    binding.edtMessage.getText().clear();
                }
            }
        });

    }

    private void sendMessage(String message,String messageId) {


        Calendar now = Calendar.getInstance();
String time = now.get(Calendar.HOUR_OF_DAY) + ":"+now.get(Calendar.MINUTE);
        String DT= Integer.toString(now.get(Calendar.YEAR) )+ Integer.toString(now.get(Calendar.MONTH)) + Integer.toString(now.get(Calendar.DAY_OF_MONTH))+ Integer.toString(now.get(Calendar.HOUR_OF_DAY)) + Integer.toString(now.get(Calendar.MINUTE))+Integer.toString(now.get(Calendar.SECOND));
        MessageModel messageModel = new MessageModel(messageId,FirebaseAuth.getInstance().getUid(),message,time,receiverId,img);
        messageAdapter.add(messageModel);

        databaseReferenceSender
                .child(messageId)
                .setValue(messageModel);
        databaseReferenceReceiver
                .child(messageId)
                .setValue(messageModel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.chat_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
           Intent intent = new Intent(ChatActivity.this,MainActivity.class);
           startActivity(intent);


        }
      else  if(item.getItemId()==R.id.view)
        {
            Intent iabout = new Intent(ChatActivity.this,ViewAccount.class);
            iabout.putExtra("id",getIntent().getStringExtra("id"));
            startActivity(iabout);
        } else if (item.getItemId()==R.id.shareCon) {try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage="ID : " +receiverId+", Name :"+name;

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }

        } else  if(item.getItemId()==R.id.theme)
        {
            Intent ith= new Intent(ChatActivity.this,Theme2.class);
            ith.putExtra("id",receiverId);
            ith.putExtra("name",name);
            startActivity(ith);
            this.finish();
        }
       else if(item.getItemId()==R.id.Del)
        {
            AlertDialog.Builder builder =new AlertDialog.Builder(this)
                    .setTitle("Delete Message")
                    .setMessage("Delete all messages ?")
                    .setIcon(R.drawable.baseline_delete_24)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference delData1= FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid()).child(receiverId);
                            DatabaseReference delData2= FirebaseDatabase.getInstance().getReference().child("chats").child(receiverId).child(FirebaseAuth.getInstance().getUid());
                            delData1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                            delData2.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








}