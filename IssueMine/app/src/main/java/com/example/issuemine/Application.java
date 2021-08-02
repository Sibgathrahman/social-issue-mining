package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Application extends AppCompatActivity {
    Button b1,b2;
    EditText e1,e2;
    String url="";
    SharedPreferences sp;
    private static final int FILE_SELECT_CODE = 0;
    int res;
    String fileName="",path="";
    String subject,file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button3);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText5);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //getting all types of files
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, ""), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject = e1.getText().toString();
                file = e2.getText().toString();

                if (subject.equalsIgnoreCase("")) {
                    e1.setError("Please fill the field");
                    e1.requestFocus();
                } else if (file.equalsIgnoreCase("")) {
                    e2.setError("Choose A File");
                    e2.requestFocus();
                } else {


                    res = uploadFile(path);

                    if (res == 1) {
                        Toast.makeText(getApplicationContext(), " registered", Toast.LENGTH_LONG).show();
                        Intent ik = new Intent(getApplicationContext(), Application.class);
                        startActivity(ik);

                    } else {
                        Toast.makeText(getApplicationContext(), " error", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        path = FileUtils.getPath(this, uri);
                        //e4.setText(path1);
                        Log.d("path", path);
                        File fil = new File(path);
                        int fln = (int) fil.length();
                          e2.setText(path);

                        File file = new File(path);

                        byte[] byteArray = null;
                        try {
                            InputStream inputStream = new FileInputStream(fil);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] b = new byte[fln];
                            int bytesRead = 0;

                            while ((bytesRead = inputStream.read(b)) != -1) {
                                bos.write(b, 0, bytesRead);
                            }

                            byteArray = bos.toByteArray();
                            inputStream.close();
                            Bitmap bmp= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            if(bmp!=null){
//
//
//                                i1.setVisibility(View.VISIBLE);
//                                i1.setImageBitmap(bmp);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please select suitable file", Toast.LENGTH_LONG).show();
                }
                break;


        }


    }

    public int uploadFile(String sourceFileUri) {
        try {
            if(android.os.Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            fileName = sourceFileUri;
            String upLoadServerUri = "http://" + sp.getString("ip", "") + ":5000/application";
            Toast.makeText(getApplicationContext(), upLoadServerUri, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            FileUpload fp = new FileUpload(fileName);
            Map mp = new HashMap<String, String>();
            mp.put("sub",subject );
            mp.put("uid",sp.getString("lid",""));

            fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
            return 1;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error"+e,Toast.LENGTH_LONG).show();
            return 0;
        }
    }



}
