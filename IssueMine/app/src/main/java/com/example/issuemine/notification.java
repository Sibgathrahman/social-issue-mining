package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notification extends AppCompatActivity {
    ListView v1;
    String url="";
    ArrayList<String>date,noti;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        v1=(ListView)findViewById(R.id.list1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        RequestQueue queue = Volley.newRequestQueue(notification.this);
        url = "http://" +sp.getString("ip","")+ ":5000/notification";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONArray json = new JSONArray(response);
                    date=new ArrayList<String>();
                    noti=new ArrayList<String>();

                    for(int i=0;i<json.length();i++)
                    {
                        JSONObject jo=json.getJSONObject(i);
                        date.add(jo.getString("date"));
                        noti.add(jo.getString("notification"));


                    }

                    v1.setAdapter(new Custom(notification.this,date,noti));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);
    }
}
