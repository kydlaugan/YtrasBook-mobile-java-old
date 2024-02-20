package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AncienPanier extends AppCompatActivity {
    private String url_panier = "https://kydlaugan.pythonanywhere.com/api/panier/" ;
    private String url = "https://kydlaugan.pythonanywhere.com/api/article/" ;
    GridAdapter2 adapter ;
    ListView listView ;
    List<GridItem2> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancien_panier);

        String user = openjson("data.json");
        GetDataPanier(user);
    }

    private void GetDataPanier( String user )
    {
        RequestQueue queue = Volley.newRequestQueue(AncienPanier.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_panier, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
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
                        if(user_id.equals(user) && envoye_commande.equals("true")){
                            //net_payer = String.valueOf( Float.parseFloat(net_payer) + Float.parseFloat(prix) ) ;
                            GetDataArticle(listView,article_id , quantite , prix);
                        }

                    }
                }
                catch (Exception w)
                {
                    Toast.makeText(AncienPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AncienPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetDataArticle(ListView liste_article ,  String article_id , String quantite , String prix_panier)
    {
        RequestQueue queue = Volley.newRequestQueue(AncienPanier.this);
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
                    GridAdapter2 adapter = new GridAdapter2(AncienPanier.this, items);
                    liste_article.setAdapter(adapter);

                }
                catch (Exception w)
                {
                    Toast.makeText(AncienPanier.this,"pas de connexion",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AncienPanier.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = AncienPanier.this.openFileInput(filename)) {
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

    //fin de l'activite
}