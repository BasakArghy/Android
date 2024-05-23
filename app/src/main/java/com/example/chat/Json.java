package com.example.chat;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {


    JSONArray jsonArray;






    public JSONArray parseData(Context context){


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url ="https://api.myjson.online/v1/records/721243bf-e8a6-41ed-8a3f-1b71e15cf86e";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Json parsing
                try {
                    jsonParse(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });
        requestQueue.add(stringRequest);
   return jsonArray; }
    public  JSONArray jsonParse(String response) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(response);
           jsonArray= jsonObject.getJSONArray("data");

            return jsonArray;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

}
