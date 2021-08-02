package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class rating extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b1;
    Spinner s1;
    RatingBar r1;
    String url="",dep;
    SharedPreferences sp;
    ArrayList<String>dptname,did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        b1=(Button)findViewById(R.id.button7);
        s1=(Spinner)findViewById(R.id.spinner2);
        r1=(RatingBar)findViewById(R.id.ratingBar);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        RequestQueue queue = Volley.newRequestQueue(rating.this);
        url = "http://" + sp.getString("ip","") + ":5000/rating_department";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONArray json = new JSONArray(response);
                    did=new ArrayList<String>();
                    dptname=new ArrayList<String>();

                    for(int i=0;i<json.length();i++)
                    {
                        JSONObject jo=json.getJSONObject(i);
                        did.add(jo.getString("did"));
                        dptname.add(jo.getString("department"));


                    }

                    ArrayAdapter aad=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,dptname);
                    s1.setAdapter(aad);
                    s1.setOnItemSelectedListener(rating.this);


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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String rate=r1.getRating()+"";
//                final String department=s1.getSelectedItem().toString();






                RequestQueue queue = Volley.newRequestQueue(rating.this);
                url = "http://" + sp.getString("ip","") + ":5000/rating";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");

                            if (res.equalsIgnoreCase("success")) {

                                Toast.makeText(rating.this, "success", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(), User_home.class);
                                startActivity(ik);

                            } else {


                                Toast.makeText(rating.this, "Invalid", Toast.LENGTH_SHORT).show();



                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "exxp" + e, Toast.LENGTH_LONG).show();

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
                        params.put("rating", rate);
                        params.put("dpt", dep);
                        params.put("uid", sp.getString("lid",""));

                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos=position;
        dep=did.get(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
