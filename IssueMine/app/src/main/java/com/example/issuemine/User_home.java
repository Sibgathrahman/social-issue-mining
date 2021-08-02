package com.example.issuemine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User_home extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        b1=(Button)findViewById(R.id.button16);
        b2=(Button)findViewById(R.id.button15);
        b3=(Button)findViewById(R.id.button14);
        b4=(Button)findViewById(R.id.button13);
        b5=(Button)findViewById(R.id.button12);
        b6=(Button)findViewById(R.id.button11);
        b7=(Button)findViewById(R.id.button10);
        b8=(Button)findViewById(R.id.button9);
        b9=(Button)findViewById(R.id.button17);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(),about.class);
                startActivity(i1);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(getApplicationContext(),Application.class);
                startActivity(i2);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(getApplicationContext(),chat.class);
                startActivity(i3);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent i4=new Intent(getApplicationContext(),Feedback.class);
                startActivity(i4);

            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i5=new Intent(getApplicationContext(),Issues.class);
                startActivity(i5);

            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i6=new Intent(getApplicationContext(),notification.class);
                startActivity(i6);

            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7=new Intent(getApplicationContext(),Policies.class);
                startActivity(i7);

            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i8=new Intent(getApplicationContext(),rating.class);
                startActivity(i8);

            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i9=new Intent(getApplicationContext(),login.class);
                startActivity(i9);
            }
        });
    }
}
