package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NouveauPanier extends AppCompatActivity {
    GridAdapter2 adapter ;
    ListView listView ;
    private String url = "https://kydlaugan.pythonanywhere.com/api/article/" ;
    private String url_panier = "https://kydlaugan.pythonanywhere.com/api/panier/" ;
    private String url_commande = "https://kydlaugan.pythonanywhere.com/api/commande/" ;
    private TextView somme ;
    List<GridItem2> items = new ArrayList<>();
    Button commande ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_panier);
        commande = (Button) findViewById(R.id.commande);
        // recuperation de l'heure actuelle
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Mois commence à 0, donc ajoutez 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = year + "-" + month + "-" + day;
        String currentTime = hour + ":" + minute + ":" + second;
        String date_commande = currentDate + " "  + currentTime ;

        String user = openjson("data.json");
        GetDataPanier(user);
        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataPanierForCommand(user , date_commande);
            }
        });

    }
    private void GetDataArticle(ListView liste_article ,  String article_id , String quantite , String prix_panier)
    {
        RequestQueue queue = Volley.newRequestQueue(NouveauPanier.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {

                        JSONObject jsonObject = jsonArray.getJSONObject(Integer.parseInt(article_id)-1);

                        String titre = jsonObject.getString("titre");
                        String classes = jsonObject.getString("classe");
                        String serie = jsonObject.getString("serie");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        if(serie.equals("Aucun"))
                            serie = " " ;
                        items.add(new GridItem2(titre , classes  , serie ,quantite ,prix , prix_panier , image));
                        GridAdapter2 adapter = new GridAdapter2(NouveauPanier.this, items);
                        liste_article.setAdapter(adapter);

                }
                catch (Exception w)
                {
                    Toast.makeText(NouveauPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NouveauPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetDataPanier( String user )
    {
        RequestQueue queue = Volley.newRequestQueue(NouveauPanier.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_panier, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                somme = (TextView)findViewById(R.id.somme);
                String net_payer = "0" ;
                listView = (ListView) findViewById(R.id.listview);
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String  user_id = jsonObject.getString("user_id");
                        String article_id = jsonObject.getString("article_id");
                        String quantite = jsonObject.getString("quantite");
                        String prix = jsonObject.getString("prix");
                        String envoye_commande = jsonObject.getString("envoye_commande");
                        if(user_id.equals(user) && envoye_commande.equals("false")){
                            net_payer = String.valueOf( Float.parseFloat(net_payer) + Float.parseFloat(prix) ) ;
                            somme.setText(net_payer);
                            GetDataArticle(listView,article_id , quantite , prix);
                        }

                    }
                }
                catch (Exception w)
                {
                    Toast.makeText(NouveauPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NouveauPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetDataPanierForCommand( String user , String date_commande )
    {
        RequestQueue queue = Volley.newRequestQueue(NouveauPanier.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_panier, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                String liste =  " ";
                somme = (TextView)findViewById(R.id.somme);

                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String  user_id = jsonObject.getString("user_id");
                        String article_id = jsonObject.getString("article_id");
                        String quantite = jsonObject.getString("quantite");
                        String prix = jsonObject.getString("prix");
                        String id = jsonObject.getString("id");
                        String envoye_commande = jsonObject.getString("envoye_commande");
                        if(user_id.equals(user) && envoye_commande.equals("false")){
                            liste = liste + "  :  " + id  ;
                            PostDataPanier(id, user_id , article_id , quantite , prix);
                        }

                    }
                    if(liste.trim().equals("")){
                        showDialog2();
                    }
                    else{
                        PostDataCommand(user , liste  , date_commande , somme.getText().toString());
                        String nom = openjson2("data.json") ;
                        SendEmailTask sendEmailTask = new SendEmailTask("ytras.services@gmail.com", "Commande de livres YtrasBook"  + "\n" , "Id utilisateur   :  " + user  +  "\n"  +" ID Article Commandés   :" + liste + "\n" +  "Date de Commande   :  " + date_commande + "\n" + "Net a payer  :" + somme.getText().toString());
                        sendEmailTask.execute();
                    }
                }
                catch (Exception w)
                {
                    Toast.makeText(NouveauPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NouveauPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void PostDataPanier( String id , String user_id , String article_id , String quantite , String prix ) {
        RequestQueue queue = Volley.newRequestQueue(NouveauPanier.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.PUT, url_panier + id + "/", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(NouveauPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("article_id" , article_id) ;
                params.put("user_id" , user_id) ;
                params.put("quantite" , quantite) ;
                params.put("prix", prix);
                params.put("envoye_commande" , "true");
                return params;
            }
        };
        queue.add(request);
    }
    private void PostDataCommand( String user_id , String liste , String date_commande , String montant) {
        RequestQueue queue = Volley.newRequestQueue(NouveauPanier.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url_commande, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(NouveauPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id" , user_id) ;
                params.put("liste" , liste) ;
                params.put("date_commande", date_commande);
                params.put("montant", montant);
                params.put("livré" , "false");
                showDialog();
                return params;
            }
        };
        queue.add(request);
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = NouveauPanier.this.openFileInput(filename)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = fis.read()) != -1) {
                content.append((char) character);
            }
            readJsonObject = new JSONObject(content.toString());
            id = readJsonObject.getString("id") ;
            // Utilisez les valeurs lues comme bon vous semble
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return  id ;
    }

    public void showDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommandeDialogueMessage dialogMessage = new CommandeDialogueMessage();
        dialogMessage.show(fragmentManager , "Message");
    }
    public void showDialog2(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        PanierVideDialog dialogMessage = new PanierVideDialog();
        dialogMessage.show(fragmentManager , "Message");
    }

    public String openjson2(String filename){
        JSONObject readJsonObject = null;
        String nom = null;
        String prenom =  null ;
        try (FileInputStream fis = NouveauPanier.this.openFileInput(filename)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = fis.read()) != -1) {
                content.append((char) character);
            }
            readJsonObject = new JSONObject(content.toString());
            nom = readJsonObject.getString("nom") ;
            prenom = readJsonObject.getString("prenom") ;
            // Utilisez les valeurs lues comme bon vous semble
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return nom + " " + prenom ;
    }

    //fin de l'activite
}