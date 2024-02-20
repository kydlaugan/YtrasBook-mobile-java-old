package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class UserInfo extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String url = "https://kydlaugan.pythonanywhere.com/api/user/" ;
    private String item ;
    private EditText nom_put , prenom_put , residence_put , contact_put , email_put ;
    private TextView username , deconnexion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Bundle extras = getIntent().getExtras();
        deconnexion = (TextView) findViewById(R.id.deconnexion);
        if(extras != null ){
            item = extras.getString("id");
        }
        getdata(item);

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deconnexion au compte
            /*    supprimerFichierJson(getFilesDir() + File.separator +"data.json"); */
                finish();
            }
        });

    }

    private void getdata(String id_enter)
    {
        RequestQueue queue = Volley.newRequestQueue(UserInfo.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                nom_put = (EditText) findViewById(R.id.nom);
                prenom_put = (EditText) findViewById(R.id.prenom);
                residence_put = (EditText) findViewById(R.id.residence);
                contact_put = (EditText) findViewById(R.id.contact);
                email_put = (EditText) findViewById(R.id.email);
                username = (TextView) findViewById(R.id.username);

                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String residence = jsonObject.getString("residence");
                        String email = jsonObject.getString("email");
                        String contact = jsonObject.getString("contact");

                        if (id.equals(id_enter)){
                            username.setText(nom + "  " + prenom);
                            nom_put.setText(nom);
                            prenom_put.setText(prenom);
                            residence_put.setText(residence);
                            contact_put.setText(contact);
                            email_put.setText(email);

                        }
                    }

                }
                catch (Exception w)
                {
                    Toast.makeText(UserInfo.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserInfo.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    public void supprimerFichierJson(String cheminDuFichier) {
        // Créer un objet File avec le chemin du fichier JSON
        File fichierJson = new File(cheminDuFichier);
        if (fichierJson.exists()) {
            boolean aEteSupprime = fichierJson.delete();
            if (aEteSupprime) {
                Toast.makeText(this, "Suppression resussi", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Le fichier n'existe pas", Toast.LENGTH_SHORT).show();
        }
    }
    //fin de l'activite
}