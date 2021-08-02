package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class Registration extends AppCompatActivity {
    Button b1;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    RadioButton r1,r2;
    String url="";
    SharedPreferences sp;
    Spinner s1;
    String dst[]={"MALAPPURAM","KANNUR","KOZHIKODE","WAYANAD","KASARAGODE","PALAKKAD","ERNAKULAM","IDUKKI","PATHANAMTHITTA","KOLLAM","ALAPPUZHA","TIRUVANANTHAPURAM","THRISSUR","KOTTAYAM"};
    String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        b1=(Button)findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.editText4);
        e2=(EditText)findViewById(R.id.editText6);
        e3=(EditText)findViewById(R.id.editText8);
        e4=(EditText)findViewById(R.id.editText14);
        e5=(EditText)findViewById(R.id.editText9);
        e6=(EditText)findViewById(R.id.editText10);
        e7=(EditText)findViewById(R.id.editText11);
        e8=(EditText)findViewById(R.id.editText12);
        e9=(EditText)findViewById(R.id.editText13);
        e10=(EditText)findViewById(R.id.editText18);
        r1=(RadioButton)findViewById(R.id.radioButton);
        r2=(RadioButton)findViewById(R.id.radioButton2);
        s1=(Spinner)findViewById(R.id.spinner);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter aad=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,dst);
        s1.setAdapter(aad);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String first_name = e1.getText().toString();
                final String second_name = e2.getText().toString();


                if (r1.isChecked()) {

                    gender = r1.getText().toString();

                } else {

                    gender = r2.getText().toString();

                }

                final String dob = e3.getText().toString();
                final String ward = e4.getText().toString();
                final String place = e5.getText().toString();
                final String post = e6.getText().toString();
                final String pin = e7.getText().toString();
                final String district = s1.getSelectedItem().toString();
                final String phone = e8.getText().toString();
                final String email = e9.getText().toString();
                final String password = e10.getText().toString();

                if (first_name.equalsIgnoreCase("")) {
                    e1.setError("Please fill the field");
                    e1.requestFocus();
                } else if (second_name.equalsIgnoreCase("")) {
                    e2.setError("Please fill the field");
                    e2.requestFocus();
                } else if (dob.equalsIgnoreCase("")) {
                    e3.setError("Please fill the field");
                    e3.requestFocus();
                } else if (ward.equalsIgnoreCase("")) {
                    e4.setError("Please fill the field");
                    e4.requestFocus();
                } else if (place.equalsIgnoreCase("")) {
                    e5.setError("Please fill the field");
                    e5.requestFocus();
                } else if (post.equalsIgnoreCase("")) {
                    e6.setError("Please fill the field");
                    e6.requestFocus();
                } else if (pin.equalsIgnoreCase("")) {
                    e7.setError("Please fill the field");
                    e7.requestFocus();
                } else if (phone.equalsIgnoreCase("")) {
                    e8.setError("Please fill the field");
                    e8.requestFocus();
                }
                else if(email.equalsIgnoreCase(""))
                    {
                        e9.setError("Enter Your Email");
                    }
                    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        e9.setError("Enter Valid Email");
                        e9.requestFocus();

                } else if (password.equalsIgnoreCase("")) {
                    e10.setError("Please fill the field");
                    e10.requestFocus();
                } else {


                    RequestQueue queue = Volley.newRequestQueue(Registration.this);
                    url = "http://" + sp.getString("ip", "") + ":5000/registration";

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

                                    Toast.makeText(Registration.this, "ERROR", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(Registration.this, "Successfull", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);


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
                            params.put("fname", first_name);
                            params.put("sname", second_name);
                            params.put("gen", gender);
                            params.put("dob", dob);
                            params.put("wrd", ward);
                            params.put("pla", place);
                            params.put("pst", post);
                            params.put("pin", pin);
                            params.put("dst", district);
                            params.put("phn", phone);
                            params.put("email", email);
                            params.put("pass", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });
    }
}
