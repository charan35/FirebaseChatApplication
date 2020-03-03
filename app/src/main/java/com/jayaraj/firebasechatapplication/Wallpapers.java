package com.jayaraj.firebasechatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Wallpapers extends AppCompatActivity {

    ImageView black,blue,dark,primary,orange,lightyellow,color1,color2,color3,color4,color5,color6;
    String Black;
    Context context;
    public TextView text;

    /*public static final String mypreference = "mypref";
    public static final String Name = "person_name";
    SharedPreferences sharedpreferences;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        black = findViewById(R.id.black);
        blue = findViewById(R.id.blue);
        dark = findViewById(R.id.dark);
        primary = findViewById(R.id.primary);
        orange = findViewById(R.id.orange);
        lightyellow = findViewById(R.id.lightyellow);
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        color5 = findViewById(R.id.color5);
        color6 = findViewById(R.id.color6);

        text = findViewById(R.id.text);

       /* sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Name))
            text.setText(sharedpreferences.getString(Name, ""));*/

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Black");
                Toast.makeText(getApplicationContext(), "Wallpaper Selected", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("LightBlue");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Grey");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Blue");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("orange");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        lightyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("lightyellow");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color1");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color2");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color3");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color4");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color5");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });

        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("color6");
                Intent intent = new Intent(Wallpapers.this,Preview.class);
                intent.putExtra("text",text.getText().toString());
                startActivity(intent);
            }
        });
    }

}
