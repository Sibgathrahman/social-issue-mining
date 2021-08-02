package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
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

public class about extends AppCompatActivity {

    String url="";
    SharedPreferences sp;
    String  corp,dscr;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1=(TextView)findViewById(R.id.textView24);
        t2=(TextView)findViewById(R.id.textView25);


        RequestQueue queue = Volley.newRequestQueue(about.this);
        url = "http://" +sp.getString("ip","") + ":5000/about";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONArray json = new JSONArray(response);
                    corp="";
                    dscr="";

                    for(int i=0;i<json.length();i++)
                    {
                        JSONObject jo=json.getJSONObject(i);
                        corp=jo.getString("corporation");
                        dscr=jo.getString("Description");
                    }

                    t1.setText(corp);
                    t2.setText(dscr);



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
