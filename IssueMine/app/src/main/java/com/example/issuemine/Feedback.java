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
import android.widget.EditText;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button b1;
    EditText e1;
    Spinner s1;
    String url="";
    SharedPreferences sp;
    ArrayList<String>dptname,did;
    String dep="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        b1=(Button)findViewById(R.id.button5);
        e1=(EditText)findViewById(R.id.editText7);
        s1=(Spinner)findViewById(R.id.spinner3);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        RequestQueue queue = Volley.newRequestQueue(Feedback.this);
        url = "http://" +sp.getString("ip","")+ ":5000/department";

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

                    for (int i=0;i<json.length();i++)

                    {
                        JSONObject jo=json.getJSONObject(i);
                        did.add(jo.getString("did"));
                        dptname.add(jo.getString("department"));
                    }

                    ArrayAdapter aad=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,dptname);
                    s1.setAdapter(aad);
                    s1.setOnItemSelectedListener(Feedback.this);





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

                final String feedback = e1.getText().toString();

                if (feedback.equalsIgnoreCase("")) {
                    e1.setError("Please fill the field");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(Feedback.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/feedback";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("Invalid")) {

                                    Toast.makeText(Feedback.this, "Invalid username or password", Toast.LENGTH_SHORT).show();


                                } else {

                                    String lid = json.getString("id");
                                    SharedPreferences.Editor edp = sp.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
//                                Intent ik = new Intent(getApplicationContext(), User_home.class);
//                                startActivity(ik);


                                }
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
                            params.put("fdbk", feedback);
                            params.put("dpt", dep);
                            params.put("uid", sp.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        dep=did.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
