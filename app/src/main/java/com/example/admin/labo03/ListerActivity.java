package com.example.admin.labo03;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

public class ListerActivity extends AppCompatActivity {

    protected ListView listResultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listResultat = findViewById(R.id.listerRsltListe);

        lister();


    }

    public void lister() {

        final ArrayList<HashMap<String, Object>> tabLivres = new ArrayList<HashMap<String, Object>>();


        String url = "http://10.0.2.2:8888/GestionContacts/controleur/controleur_PDO.php";

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i,j;
                            JSONArray jsonResponse = new JSONArray(response);
                            HashMap<String, Object> map;
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unContact;
                                for(i=1;i<jsonResponse.length();i++){
                                    unContact=jsonResponse.getJSONObject(i);
                                    map= new HashMap<String, Object>();

                                    map.put("id", unContact.getString("id"));
                                    map.put("nom", unContact.getString("nom"));
                                    map.put("prenom", unContact.getString("prenom"));
                                    map.put("telephone", unContact.getString("telephone"));
                                    tabLivres.add(map);
                                }

                                SimpleAdapter monAdapter = new SimpleAdapter (ListerActivity.this, tabLivres, R.layout.affichage_contacts,
                                        new String[] {"nom", "prenom", "telephone"},
                                        new int[] {R.id.textNom, R.id.textPrenom, R.id.textTelephone});
                                listResultat.setAdapter(monAdapter);


                                listResultat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        ImageView imageTel = (ImageView)view.findViewById(R.id.brtnTeleLister);
                                        TextView tel = (TextView) view.findViewById(R.id.textTelephone);
                                        final String telephone = tel.getText().toString();


                                        imageTel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {



                                                Context context = ListerActivity.this;
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:"+telephone));

                                                //Intent intent = new Intent(Intent.ACTION_CALL,  Uri.parse("tel:" + "1324567890"));
                                                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    Toast.makeText(context, "permission not granted", Toast.LENGTH_SHORT).show();
                                                    ActivityCompat.requestPermissions(ListerActivity.this, new String[]{Manifest.permission.CALL_PHONE},143);
                                                }else{
                                                    context.startActivity(callIntent);
                                                }

                                            }
                                        });

                                    }
                                });








                            }
                            else{}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListerActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
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
