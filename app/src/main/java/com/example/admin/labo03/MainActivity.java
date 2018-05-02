package com.example.admin.labo03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button insererBtn, listerBtn, chercherBtn, effacerBtn, modifierBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insererBtn = findViewById(R.id.btnInsererData);
        listerBtn = findViewById(R.id.btnListerData);
        chercherBtn = findViewById(R.id.btnChercher);
        effacerBtn = findViewById(R.id.btnEffacer);
        modifierBtn = findViewById(R.id.btnModifier);

        insererBtn.setOnClickListener(this);
        listerBtn.setOnClickListener(this);
        chercherBtn.setOnClickListener(this);
        effacerBtn.setOnClickListener(this);
        modifierBtn.setOnClickListener(this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()){

            case R.id.btnInsererData:
                //Toast.makeText(MainActivity.this,"btnInsererData", Toast.LENGTH_LONG).show();
                intent = new Intent(MainActivity.this, EnregistrerActivity.class);
                startActivity(intent);
                break;
            case R.id.btnListerData:
                intent = new Intent(MainActivity.this, ListerActivity.class);
                startActivity(intent);
                break;

            case R.id.btnChercher:
                intent = new Intent(MainActivity.this, ChercherActivity.class);
                startActivity(intent);
                break;

            case R.id.btnEffacer:
                intent = new Intent(MainActivity.this, EffacerActivity.class);
                startActivity(intent);
                break;

            case R.id.btnModifier:
                intent = new Intent(MainActivity.this, ModifierActivity.class);
                startActivity(intent);
                break;
        }

    }
}
