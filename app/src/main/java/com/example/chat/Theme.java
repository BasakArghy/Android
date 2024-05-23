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
import com.example.chat.databinding.ActivityThemeBinding;

public class Theme extends AppCompatActivity {
ActivityThemeBinding binding;
ActivityMainBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        binding= ActivityThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        binding.Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme.this,MainActivity.class);
                itheme.putExtra("color","1");
                startActivity(itheme);
            }
        });
        binding.Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme.this,MainActivity.class);
                itheme.putExtra("color","2");
                startActivity(itheme);
            }
        });
        binding.Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme.this,MainActivity.class);
                itheme.putExtra("color","3");
                startActivity(itheme);
            }
        });
        binding.Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent itheme = new Intent(Theme.this,MainActivity.class);
                itheme.putExtra("color","4");
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