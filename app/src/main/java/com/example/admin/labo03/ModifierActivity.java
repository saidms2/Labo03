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

public class ModifierActivity extends AppCompatActivity {

    TextInputLayout textid, txtTelModifier;
    Button chercher, modifier, annuler;
    TextView txtId, txtNom, txtPrenom ,txtTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chercher = findViewById(R.id.searchBtnModifier);
        modifier = findViewById(R.id.btnModifierTel);
        annuler = findViewById(R.id.btnAnnulerModf);
        textid = findViewById(R.id.txtInputModifier);

        txtId=findViewById(R.id.txtIdModifier);
        txtNom=findViewById(R.id.txtNomModifier);
        txtPrenom=findViewById(R.id.txtPrenomModifier);
        txtTelephone=findViewById(R.id.txtTelephoneModifier);

        txtTelModifier=findViewById(R.id.txtInputModifTel);


        annuler.setVisibility(View.INVISIBLE);
        modifier.setVisibility(View.INVISIBLE);
        txtTelModifier.setVisibility(View.INVISIBLE);

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

                                txtId.setText(unContact.getString("id"));
                                txtNom.setText(unContact.getString("nom"));
                                txtPrenom.setText(unContact.getString("prenom"));
                                txtTelephone.setText(unContact.getString("telephone"));

                                annuler.setVisibility(View.VISIBLE);
                                modifier.setVisibility(View.VISIBLE);
                                txtTelModifier.setVisibility(View.VISIBLE);

                                modifier.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(ModifierActivity.this, "btn modifier", Toast.LENGTH_LONG).show();
                                        String telephone = txtTelModifier.getEditText().getText().toString();
                                        requeteModifier(id, telephone);
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
                                Toast.makeText(ModifierActivity.this, "Ce client est inexistant", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifierActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
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

    public void requeteModifier(final String id, final String telephone) {
        //Toast.makeText(ModifierActivity.this, "je suis dans modifié", Toast.LENGTH_LONG).show();

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
                                Toast.makeText(ModifierActivity.this, "Téléphone modifié", Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(ModifierActivity.this, "le téléphone n'est pas modifié", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifierActivity.this, "ERREUR", Toast.LENGTH_LONG).show();
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "modifier");
                params.put("telephone", telephone);
                params.put("idContact", id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }


    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
