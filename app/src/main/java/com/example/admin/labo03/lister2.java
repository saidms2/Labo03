package com.example.admin.labo03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class lister2 extends AppCompatActivity {
        protected ListView listResultat;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_lister);

            listResultat = findViewById(R.id.listerRsltListe);




            //Lister
            final ArrayList<HashMap<String, Object>> tabLivres = new ArrayList<HashMap<String, Object>>();

            String url = "http://10.0.2.2:8888/GestionContacts/controleur/controleur_PDO.php";
            String url2="http://10.0.2.2:8888/test/test.php";
            Toast.makeText(this, "avant string request", Toast.LENGTH_SHORT).show();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(lister2.this, "dans onResponse", Toast.LENGTH_SHORT).show();

                    Log.d("RESULTAT", response);

                    try {
                        //Log.d("RESULTAT", "RESULTAT 2 "+response);
                        int i,j;
                        //JSONArray jsonResponse = new JSONArray(response);
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.d("JSON", "jsonResponse "+jsonResponse);

                    /*HashMap<String, Object> map;
                    String msg = jsonResponse.getString(0);
                    Log.d("JSON", "jsonResponse "+jsonResponse);
                    if(msg.equals("OK")){
                        JSONObject unContact;
                        for(i=1;i<jsonResponse.length();i++){
                            unContact=jsonResponse.getJSONArray(0);
                            Log.d("RESULTAT", "contact "+i+" " +unContact);
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
                    }
                    else{}*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("RESULTAT", "ERROR "+error);

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    // Les parametres pour POST
                    params.put("action", "lister");
                    return super.getParams();

                }
            };




            Volley.newRequestQueue(this).add(request);//Si Volley rouge clique Volley et choisir add dependency on module volley

        }
    }


