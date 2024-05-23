package com.example.chat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivityMSG extends AppCompatActivity {
    UserAdapterMSG userAdapterMSG;
    RecyclerView recycler;
    MyDBhelper dBhelpe;
    MyDBhelper databaseReferenceSender;
    FloatingActionButton btn;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final  String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    SMSReceiver smsReceiver =new SMSReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            dBhelpe.updateaddition("", mobNo);

            ArrayList<ContactModel> arrContact = dBhelpe.fetchContct();
            userAdapterMSG.clear();
            for (int i = 0; i < arrContact.size(); i++) {
                ContactModel model = new ContactModel();
                model.name = arrContact.get(i).name;
                model.phone_no = arrContact.get(i).phone_no;

                userAdapterMSG.add(model);
            }
            databaseReferenceSender = new MyDBhelper(MainActivityMSG.this,"me"+mobNo);
            databaseReferenceSender.addContact("me",msg);

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
        setContentView(R.layout.activity_main_msg);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   dBhelpe = new MyDBhelper(this,"contact");
//dBhelpe.deleteAllContct();
   userAdapterMSG = new UserAdapterMSG(this);
        recycler = findViewById(R.id.recycler);
        recycler.setAdapter(userAdapterMSG);
       recycler.setLayoutManager(new LinearLayoutManager(this));
       btn =findViewById(R.id.btn);
        userAdapterMSG.clear();
        ArrayList<ContactModel> arrContact = dBhelpe.fetchContct();
        for(int i=arrContact.size()-1;i>=0;i--)
        {ContactModel model = new ContactModel();
            model.name=arrContact.get(i).name;
            model.phone_no=arrContact.get(i).phone_no;

            userAdapterMSG.add(model);}
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog dialog = new Dialog(MainActivityMSG.this);
               dialog.setContentView(R.layout.add_update_lay);
               EditText editName = dialog.findViewById(R.id.editName);
               EditText editNumber =dialog.findViewById(R.id.editNumber);
               Button btnOk =dialog.findViewById(R.id.btnOk);
               dialog.show();
               btnOk.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       String name="",num="";
                       if(!editName.getText().toString().equals(""))
                       {  name = editName.getText().toString();}
                       else {
                           Toast.makeText(MainActivityMSG.this, "Please Enter Contact name", Toast.LENGTH_SHORT).show();
                       }
                       if(!editNumber.getText().toString().equals("")){
                           num =editNumber.getText().toString();}
                       else {
                           Toast.makeText(MainActivityMSG.this, "Please Enter Contact number", Toast.LENGTH_SHORT).show();
                       }


               dBhelpe.addContact(name,num);
               ArrayList<ContactModel> arrContact = dBhelpe.fetchContct();
               userAdapterMSG.clear();
             for(int i=arrContact.size()-1;i>=0;i--)
               {ContactModel model = new ContactModel();
                   model.name=arrContact.get(i).name;
                   model.phone_no=arrContact.get(i).phone_no;

                   userAdapterMSG.add(model);

                  // Toast.makeText(MainActivity.this, "Name :"+ arrContact.get(i).name+", Phone No: "+arrContact.get(i).phone_no, Toast.LENGTH_SHORT).show();
               }
                       dialog.dismiss();
           }

       });

    }
});
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