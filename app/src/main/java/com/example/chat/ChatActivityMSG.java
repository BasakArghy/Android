package com.example.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivityMSG extends AppCompatActivity {
    String receiverId;
    String senderRoom,receiverRoom;
    MessageAdapterMSG messageAdapterMSG;
   MyDBhelper databaseReferenceSender;
    RecyclerView recycler;
    ImageView sendMsg;
    EditText edtMessage;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final  String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    SMSReceiver smsReceiver =new SMSReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);


               /* databaseReferenceSender
                        .addContact("me", msg);*/
                ArrayList<ContactModel> arrContact = databaseReferenceSender.fetchContct();
                messageAdapterMSG.clear();
                for (int i = 0; i < arrContact.size(); i++) {
                    MessageModelMsg model = new MessageModelMsg();
                    model.setMsgId(String.valueOf(arrContact.get(i).id));
                    model.setSenderId(arrContact.get(i).name);
                    model.setMessage(arrContact.get(i).phone_no);

                    messageAdapterMSG.add(model);

                }



        }

    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(smsReceiver,new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiverId=getIntent().getStringExtra("id");

        senderRoom = "me"+receiverId;

        recycler = findViewById(R.id.recyclermsg);
        sendMsg=findViewById(R.id.sendMsg);
        edtMessage=findViewById(R.id.edtMessage);

        messageAdapterMSG = new MessageAdapterMSG(this);
       recycler.setAdapter(messageAdapterMSG);
      recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender = new MyDBhelper(this,senderRoom);


        ArrayList<ContactModel> arrContact = databaseReferenceSender.fetchContct();
        messageAdapterMSG.clear();
        for(int i=0;i<arrContact.size();i++)
        {
            MessageModelMsg model = new MessageModelMsg();
            model.setMsgId(String.valueOf(arrContact.get(i).id));
            model.setSenderId(arrContact.get(i).name);
            model.setMessage(arrContact.get(i).phone_no);

            messageAdapterMSG.add(model);

        }

       sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();

                if(message.trim().length()>0){
                    sendMessage(message);
                    edtMessage.getText().clear();
                }
            }
        });

    }

    private void sendMessage(String message) {


        databaseReferenceSender
                .addContact(receiverId,message);
        ArrayList<ContactModel> arrContact = databaseReferenceSender.fetchContct();
        messageAdapterMSG.clear();
        for(int i=0;i<arrContact.size();i++)
        {
            MessageModelMsg model = new MessageModelMsg();
            model.setMsgId(String.valueOf(arrContact.get(i).id));
            model.setSenderId(arrContact.get(i).name);
            model.setMessage(arrContact.get(i).phone_no);

            messageAdapterMSG.add(model);

        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(receiverId,null,message,null,null);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
}