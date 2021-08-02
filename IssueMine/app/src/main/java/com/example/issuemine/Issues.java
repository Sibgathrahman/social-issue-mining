package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Issues extends AppCompatActivity {
    Button b1;
    EditText e1,e2,e3;
    String url="",ward,description,issue;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        b1=(Button)findViewById(R.id.button6);
        e1=(EditText)findViewById(R.id.editText15);
        e2=(EditText)findViewById(R.id.editText16);
        e3=(EditText)findViewById(R.id.editText17);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ward = e1.getText().toString();
                issue = e2.getText().toString();
                description = e3.getText().toString();

                if (ward.equalsIgnoreCase("")) {
                    e1.setError("Please fill the field");
                    e1.requestFocus();
                } else if (issue.equalsIgnoreCase("")) {
                    e2.setError("Please fill the field");
                    e2.requestFocus();
                } else if (description.equalsIgnoreCase("")) {
                    e3.setError("Please fill the field");
                    e3.requestFocus();
                } else {


                    RequestQueue queue = Volley.newRequestQueue(Issues.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/issues";

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

                                    Toast.makeText(Issues.this, "success", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), User_home.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(Issues.this, "Invalid", Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();

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
                            params.put("wrd", ward);
                            params.put("isu", issue);
                            params.put("dscn", description);
                            params.put("uid", sp.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });
    }
}
