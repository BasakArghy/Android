package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chat.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseReference databaseReference;
    UserAdapter userAdapter;
    String namely;
    int noti_on = 1;
    DrawerLayout drawerLayout;
    String notiClick;
    boolean check;
int a;
    ActionBarDrawerToggle drawerToggle;
String theme="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent().getExtras() != null )
        {
            //Toast.makeText(this,getIntent().getStringExtra("color"), Toast.LENGTH_SHORT).show();
        if(getIntent().getStringExtra("color").equals("1")){
            binding.rela.setBackground(getResources().getDrawable(R.drawable.a1));
            binding.mmain.setBackground(getResources().getDrawable(R.drawable.a1));
        } else if (getIntent().getStringExtra("color").equals("2")) {
            binding.rela.setBackground(getResources().getDrawable(R.drawable.a2));
            binding.mmain.setBackground(getResources().getDrawable(R.drawable.a2));
        }
        else if (getIntent().getStringExtra("color").equals("3")) {
            binding.rela.setBackground(getResources().getDrawable(R.drawable.a3));
            binding.mmain.setBackground(getResources().getDrawable(R.drawable.a3));
        }
        else if (getIntent().getStringExtra("color").equals("4")) {
            binding.rela.setBackground(getResources().getDrawable(R.drawable.a5));
            binding.mmain.setBackground(getResources().getDrawable(R.drawable.a5));
        }
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url ="https://api.myjson.online/v1/records/721243bf-e8a6-41ed-8a3f-1b71e15cf86e";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Json parsing
                jsonParse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // txt1.setText("Not Working");

                error.getStackTrace();
            }
        });
        requestQueue.add(stringRequest);





        // binding.img.setImageURI(Uri.parse(url));
        DatabaseReference db;
        a= (int) (Math.random()*(18-1)+0);
        db = FirebaseDatabase.getInstance().getReference("chats").child(FirebaseAuth.getInstance().getUid());
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                snapshot.getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long p =snapshot.getChildrenCount();


                        long s=0;
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                            s++;

                            if(s==p&&noti_on==1){
                                if(messageModel.getReceiverId().equals(FirebaseAuth.getInstance().getUid())){

                                    DatabaseReference data =FirebaseDatabase.getInstance().getReference("user").child(messageModel.getSenderId());
                                  data.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                                         namely = dsnapshot.getValue(UserModel.class).getUserName();
                                          Notification notification = new Notification(getApplicationContext(),namely,messageModel.getMessage(),messageModel.getSenderId());

                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {

                                      }
                                  });


                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userAdapter = new UserAdapter(this);
        binding.recycler.setAdapter(userAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid = dataSnapshot.getKey();
                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        userAdapter.add(userModel);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        drawerLayout=findViewById(R.id.layoutDrawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.naviDrawer);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.about)
            {
                Intent iabout = new Intent(MainActivity.this,ViewAccount.class);
                iabout.putExtra("id",FirebaseAuth.getInstance().getCurrentUser().getUid());
                startActivity(iabout);
            }
                else if (item.getItemId()==R.id.Notification) {
                if(noti_on==0){noti_on=1;
                    Toast.makeText(MainActivity.this,"turn on", Toast.LENGTH_SHORT).show();}
                else {noti_on=0;
                    Toast.makeText(MainActivity.this,"turn off", Toast.LENGTH_SHORT).show();}

            }
        else if (item.getItemId()==R.id.Settings){
                Intent isetti = new Intent(MainActivity.this,Settings.class);
                startActivity(isetti);
            }
            else if (item.getItemId()==R.id.SMS){
                Intent isetti = new Intent(MainActivity.this,MainActivityMSG.class);
                startActivity(isetti);
            }
            else if (item.getItemId()==R.id.shareitm) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "Let me recommend you this application\n";
                    shareMessage = shareMessage + "www.chatapp." + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
                return false;
            }
        });

    }

    public  void jsonParse(String response)
    {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            //Toast.makeText(this,String.valueOf(a), Toast.LENGTH_SHORT).show();
    JSONObject jsonObject2 = jsonArray.getJSONObject(a);


    Picasso.with(this)
            .load(jsonObject2.getString("urlToImage"))
            .resize(400, 100)
            .centerCrop()
            .into(binding.img);
    //TimeUnit.SECONDS.sleep(30);
binding.txtAd.setText(jsonObject2.getString("title"));
binding.add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent isetti = new Intent(MainActivity.this,advertise.class);
        try {
            isetti.putExtra("url",jsonObject2.getString("url"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        startActivity(isetti);
    }
});
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logOut)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,AuthenticationActivity.class));
            finish();
            return  true;
        } else if (item.getItemId()==R.id.Notification) {
            if(noti_on==0){noti_on=1;
                Toast.makeText(this,"turn on", Toast.LENGTH_SHORT).show();}
            else {noti_on=0;
                Toast.makeText(this,"turn off", Toast.LENGTH_SHORT).show();}

        }
        else if (item.getItemId()==R.id.Settings){
            Intent isetti = new Intent(MainActivity.this,Settings.class);
            startActivity(isetti);
        }
       else if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
            return false;
    }


}