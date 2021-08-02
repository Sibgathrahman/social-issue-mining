package com.example.issuemine;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

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
import android.content.DialogInterface;

public class chat extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView v1;
    String url="";
    SharedPreferences sp;
    ArrayList<String>fname,cid;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        v1=(ListView)findViewById(R.id.list3);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       final String id=sp.getString("lid","");
        RequestQueue queue = Volley.newRequestQueue(chat.this);
        url = "http://" + sp.getString("ip","") + ":5000/view_councilor";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONArray json = new JSONArray(response);
                    fname=new ArrayList<String>();
                    cid=new ArrayList<String>();

                    for(int i=0;i<json.length();i++)
                    {
                        JSONObject jo=json.getJSONObject(i);
                        fname.add(jo.getString("first_name")+" "+jo.getString("second_name"));
                        cid.add(jo.getString("lid"));


                    }


                     ArrayAdapter<String> ad=new ArrayAdapter<>(chat.this,android.R.layout.simple_list_item_1,fname);
                    v1.setAdapter(ad);
                    v1.setOnItemClickListener(chat.this);

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
                params.put("fid", id);
                return params;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {



        pos=i;

        AlertDialog.Builder ald=new AlertDialog.Builder(chat.this);
        ald.setTitle("Interact Councilor")

                .setNegativeButton(" CHAT ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//				//String url="http://maps.google.com/maps?q="+latitude.get(pos)+","+longitude.get(pos);
//				Intent in=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//				startActivity(in);
                        Intent i1=new Intent(getApplicationContext(),chatt.class);
                        i1.putExtra("tid", cid.get(pos));
                        i1.putExtra("fname", fname.get(pos));
                        startActivity(i1);
                    }
                });

        AlertDialog al=ald.create();
        al.show();

    }


    }

