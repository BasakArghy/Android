package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.chat.databinding.ActivityMainBinding;
import com.example.chat.databinding.ActivitySettingsBinding;
import com.example.chat.databinding.ActivityTheme2Binding;
import com.example.chat.databinding.ActivityThemeBinding;

public class Theme2 extends AppCompatActivity {
    ActivityTheme2Binding binding;
    String receiverId,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        binding= ActivityTheme2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiverId=getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");


        binding.Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme2.this,ChatActivity.class);
                itheme.putExtra("color","1");
                itheme.putExtra("id",receiverId);
                itheme.putExtra("name",name);
                startActivity(itheme);
                finish();
            }
        });
        binding.Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme2.this,ChatActivity.class);
                itheme.putExtra("color","2");
                itheme.putExtra("id",receiverId);
                itheme.putExtra("name",name);
                startActivity(itheme);
                finish();
            }
        });
        binding.Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme2.this,ChatActivity.class);
                itheme.putExtra("color","3");
                itheme.putExtra("id",receiverId);
                itheme.putExtra("name",name);
                startActivity(itheme);
                finish();
            }
        });
        binding.Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme2.this,ChatActivity.class);
                itheme.putExtra("color","4");
                itheme.putExtra("id",receiverId);
                itheme.putExtra("name",name);
                startActivity(itheme);
                finish();
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