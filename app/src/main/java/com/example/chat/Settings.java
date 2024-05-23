package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.databinding.ActivitySettingsBinding;
import com.example.chat.databinding.ActivityViewAccountBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
TextView update,delete,view;
ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        binding= ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        delete = findViewById(R.id.DelAC);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idel = new Intent(Settings.this,DeleteAccount.class);
                startActivity(idel);
            }
        });
        binding.VAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iview = new Intent(Settings.this,ViewAccount.class);
              iview.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                //Toast.makeText(Settings.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                startActivity(iview);
            }
        });

        binding.CGThm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itheme = new Intent(Settings.this,Theme.class);
               // itheme.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                //Toast.makeText(Settings.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                startActivity(itheme);
            }
        });
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "Let me recommend you this application\n";
                    shareMessage = shareMessage  +"www.chatapp."+ BuildConfig.APPLICATION_ID ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        binding.hlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itheme = new Intent(Settings.this,Help.class);
                // itheme.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                //Toast.makeText(Settings.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                startActivity(itheme);
            }
        });
        binding.UpPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itheme = new Intent(Settings.this,UploadProflePic.class);
                // itheme.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                //Toast.makeText(Settings.this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                startActivity(itheme);
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