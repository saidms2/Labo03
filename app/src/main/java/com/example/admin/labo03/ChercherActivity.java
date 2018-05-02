package com.example.admin.labo03;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChercherActivity extends AppCompatActivity {

    protected int contactId;
    TextView txtNom, txtPrenom,txtTele;
    TextInputLayout textid;
    Button chercher;
    ImageButton imgtele;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chercher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chercher = findViewById(R.id.searchButton);
        textid = findViewById(R.id.txtInputChercher);

        txtNom = findViewById(R.id.textChNom);
        txtPrenom = findViewById(R.id.textChPrenom);
        txtTele = findViewById(R.id.textChTel);
        imgtele = findViewById(R.id.imageButtonTel);

        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = textid.getEditText().getText().toString();


                requeteChercher(id);

            }
        });
    }


    public void requeteChercher(final String id) {

        String url = "http://10.0.2.2:8888/GestionContacts/controleur/controleur_PDO.php";
        //Recuperer les donnees






        //Envoyer données au serveur

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unContact;
                                //for(i=1;i<jsonResponse.length();i++){
                                    unContact=jsonResponse.getJSONObject(1);

                                txtNom.setText(unContact.getString("nom"));
                                txtPrenom.setText(unContact.getString("prenom"));
                                final String telephone = unContact.getString("telephone");
                                txtTele.setText(unContact.getString("telephone"));
                                imgtele.setBackgroundResource(R.drawable.ic_call_black_24dp);

                                imgtele.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {




                                        Context context = ChercherActivity.this;
                                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:"+telephone));

                                        //Intent intent = new Intent(Intent.ACTION_CALL,  Uri.parse("tel:" + "1324567890"));
                                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            Toast.makeText(context, "permission not granted", Toast.LENGTH_SHORT).show();
                                            ActivityCompat.requestPermissions(ChercherActivity.this, new String[]{Manifest.permission.CALL_PHONE},143);
                                        }else{
                                            context.startActivity(callIntent);
                                        }

                                    }
                                });

                                //}

                            }
                            else{
                                Toast.makeText(ChercherActivity.this, "Ce client est inexistant", Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChercherActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "chercher");
                params.put("idContact", id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
