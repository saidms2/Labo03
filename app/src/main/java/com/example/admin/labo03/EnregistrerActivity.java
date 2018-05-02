package com.example.admin.labo03;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EnregistrerActivity extends AppCompatActivity {

    private TextInputLayout txt_nom, txt_prenom, txt_telephone;
    private Button btn_envoyer;

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_nom = (TextInputLayout) findViewById(R.id.txtInputNom);
        txt_prenom = (TextInputLayout) findViewById(R.id.txtInputPrenom);
        txt_telephone = (TextInputLayout) findViewById(R.id.txtInputTelephone);

        btn_envoyer=findViewById(R.id.btn_send);

        btn_envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = txt_nom.getEditText().getText().toString().trim();
                String prenom = txt_prenom.getEditText().getText().toString().trim();
                String telephone = txt_telephone.getEditText().getText().toString().trim();
                requeteEnregistrer();

            }
        });
    }

    public void requeteEnregistrer() {

        String url = "http://10.0.2.2:8888/GestionContacts/controleur/controleur_PDO.php";
        //Recuperer les donnees


        final String nom = txt_nom.getEditText().getText().toString().trim();
        final String prenom = txt_prenom.getEditText().getText().toString().trim();
        final String telephone = txt_telephone.getEditText().getText().toString().trim();


        progressDialog = new ProgressDialog(EnregistrerActivity.this);
        progressDialog.setMessage("Uploading, attendre SVP...");
        progressDialog.show();



        //Envoyer données au serveur

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK"))
                                Toast.makeText(EnregistrerActivity.this, "Contacte bien enregistre", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(EnregistrerActivity.this, "Probleme pour enregistrer", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnregistrerActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "enregistrer");
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("telephone", telephone);
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
