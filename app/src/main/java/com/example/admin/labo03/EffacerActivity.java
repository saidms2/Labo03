package com.example.admin.labo03;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class EffacerActivity extends AppCompatActivity {

    TextInputLayout textid;
    Button chercher, supprimer, annuler;
    TextView txtId, txtNom, txtPrenom ,txtTelephone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effacer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chercher = findViewById(R.id.searchButtonEffacer);
        supprimer = findViewById(R.id.btnSupprimer);
        annuler = findViewById(R.id.btnAnnuler);

        textid = findViewById(R.id.txtInputEffacer);

        txtId=findViewById(R.id.txtIdSup);
        txtNom=findViewById(R.id.txtNomSup);
        txtPrenom=findViewById(R.id.txtPrenomSup);
        txtTelephone=findViewById(R.id.txtTelephoneSup);

        annuler.setVisibility(View.INVISIBLE);
        supprimer.setVisibility(View.INVISIBLE);


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

                                txtId.setText(unContact.getString("id"));
                                txtNom.setText(unContact.getString("nom"));
                                txtPrenom.setText(unContact.getString("prenom"));
                                txtTelephone.setText(unContact.getString("telephone"));

                                annuler.setVisibility(View.VISIBLE);
                                supprimer.setVisibility(View.VISIBLE);

                                supprimer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        requeteSupprimer(id);
                                    }
                                });

                                annuler.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                                //}

                            }
                            else{
                                Toast.makeText(EffacerActivity.this, "Probleme pour enregistrer", Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EffacerActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
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

    public void requeteSupprimer(final String id) {

        String url = "http://10.0.2.2:8888/GestionContacts/controleur/controleur_PDO.php";
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
                            Toast.makeText(EffacerActivity.this, "Contact effacer", Toast.LENGTH_SHORT).show();

                            txtId.setText("");
                            txtNom.setText("");
                            txtPrenom.setText("");
                            txtTelephone.setText("");

                        }
                        else{
                            Toast.makeText(EffacerActivity.this, "Client", Toast.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EffacerActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                }
            }
    ) {



        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            // Les parametres pour POST
            params.put("action", "supprimer");
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
