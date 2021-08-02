package com.example.issuemine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

public class chatt extends AppCompatActivity {
    LinearLayout lt;
    EditText ed;
    Button b1;
    TextView t1;
    SharedPreferences sh;

    String url="";
    String fid="";
    String id="";
    Handler hd;
    static String prv="";

    String lastid;

    public static String ur="",url2;
    TelephonyManager tm;
    SharedPreferences  sp;

    public static ArrayList<String> from_id,toid,msg,date ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatt);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed=(EditText)findViewById(R.id.editText1);
        lt=(LinearLayout)findViewById(R.id.linear1);
        b1=(Button)findViewById(R.id.button1);
        t1=(TextView)findViewById(R.id.textView1);


        lastid="0";
        t1.setText(getIntent().getStringExtra("fname"));

        fid=getIntent().getStringExtra("tid");
        id=sp.getString("lid", "");

        hd=new Handler();
        hd.post(r);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                final String message=ed.getText().toString();
                ur="http://"+sp.getString("ip", "")+":5000/chat";

                final RequestQueue res=Volley.newRequestQueue(chatt.this);
                StringRequest string=new StringRequest(Request.Method.POST,ur,
                        new Response.Listener<String>() {
                            public void onResponse(String respo)
                            {

                                if(respo.equals("success"))
                                {
                                    ed.setText("");

//                                    res.stop();

                                }
                                else {
                                    ed.setText("");
                                   // Toast.makeText(getApplicationContext(), "ok",Toast.LENGTH_LONG ).show();

                                }
                            }
                        }

                        , new Response.ErrorListener() {
                    public void  onErrorResponse(VolleyError error)
                    {

                    }
                })

                {
                    protected Map<String,String> getParams() throws AuthFailureError
                    {
                        Map<String,String> params=new HashMap<>();
                        params.put("from_id", id);
                        params.put("toid", fid);
                        params.put("msg", message);


                        return params;
                    }
                }
                        ;
                res.add(string);


            }
        });







    }

    @Override
    public void onBackPressed() {

        hd.removeCallbacks(r);
        super.onBackPressed();
    }

    public Runnable r=new Runnable() {

        @Override
        public void run() {

            url2="http://"+sp.getString("ip", "")+":5000/Viewchat";
            final RequestQueue mqueu=Volley.newRequestQueue(chatt.this);

            StringRequest string=new StringRequest(Request.Method.POST,url2,
                    new Response.Listener<String>() {
                        public void onResponse(String respo)
                        {

                            try {
                                JSONArray arr = new JSONArray(respo);
                                if(respo.length() > 0){


                                    from_id=new ArrayList<String>();
                                    toid=new ArrayList<String>();
                                    msg=new ArrayList<String>();
                                    date=new ArrayList<String>();
                                    lt.removeAllViews();
                                    for (int i = 0; i < arr.length(); i++) {
                                        JSONObject c = arr.getJSONObject(i);

                                        from_id.add(c.getString("fid"));
                                        toid.add(c.getString("tid"));
                                        msg.add(c.getString("message"));
                                        date.add(c.getString("date"));

                                        TextView tv=new TextView(getApplicationContext());
                                        TextView tv1=new TextView(getApplicationContext());
                                        if(!c.getString("date").equals(prv))
                                        {
                                            //Toast.makeText(getApplicationContext(), "result is"+prv, Toast.LENGTH_LONG).show();
                                            tv1.setText(c.getString("date"));
                                            tv1.setGravity(Gravity.CENTER);
                                            prv=c.getString("date");
                                        }

                                        if(from_id.get(i).equalsIgnoreCase(id)){
                                            tv.setTextColor(Color.RED);
                                            tv.setText(""+""+msg.get(i));
                                            tv.setGravity(Gravity.RIGHT);

                                            tv.setBackgroundColor(Color.WHITE);

                                            //tv1.setTextColor(Color.RED);
                                            //tv1.setText(date.get(i)+"");


                                            tv1.setBackgroundColor(Color.WHITE);



                                        }
                                        else{
                                            tv.setTextColor(Color.BLUE);
                                            tv.setText(msg.get(i));
                                            tv.setGravity(Gravity.LEFT);

                                            tv.setBackgroundColor(Color.YELLOW);

                                            //tv1.setTextColor(Color.BLACK);
                                            //tv1.setText(date.get(i));
                                            //tv1.setGravity(Gravity.CENTER);

                                            tv1.setBackgroundColor(Color.YELLOW);
                                        }

                                        lt.addView(tv);
                                        lt.addView(tv1);


                                    }

                                }
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Toast.makeText(getApplicationContext(), "err"+e, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                            hd.postDelayed(r, 2000);
                        }
                    }

                    , new Response.ErrorListener() {
                public void  onErrorResponse(VolleyError error)
                {

                }
            })

            {
                protected Map<String,String> getParams() throws AuthFailureError
                {
                    Map<String,String> params=new HashMap<>();
                    params.put("uid", id);
                    params.put("fid", fid);


                    return params;
                }
            }
                    ;
            mqueu.add(string);

        }

    };



}