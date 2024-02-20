package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RechercheArticle extends AppCompatActivity {
    SearchView searchView ;
    GridAdapterRecherche itemAdapter ;
    private List<GridItem> itemList  ;
    ListView listView ;
    private RequestQueue requestQueue;
    private String url = "https://kydlaugan.pythonanywhere.com/api/secondaire/" ;
    private String url_primaire = "https://kydlaugan.pythonanywhere.com/api/primaire/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_article);

        listView = (ListView)findViewById(R.id.listview) ;
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        itemList = new ArrayList<>();
        getdata(listView , itemList);
        GetdataPrimaire(listView , itemList);


    }

    private void filterList(String text) {
        List<GridItem> filteredList = new ArrayList<>();
        for (GridItem item : itemList) {
            if(item.getMatiere().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "Article non trouvé !", Toast.LENGTH_SHORT).show();
        }else{
            itemAdapter.setFilteredList(filteredList);
        }
    }

    private void getdata( ListView list_article ,  List<GridItem> items)
    {
        RequestQueue queue = Volley.newRequestQueue(RechercheArticle.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String titre = jsonObject.getString("titre");
                        String classes = jsonObject.getString("classe");
                        String serie = jsonObject.getString("serie");
                        String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id") ;
                        if(serie.equals("Aucun"))
                            serie = " " ;
                        if(matiere.equals("Education à la citoyenneté"))
                            matiere = "ECM" ;
                        if(matiere.equals("Langue et culture nationale"))
                            matiere = "LCN" ;
                        items.add(new GridItem(id , titre , prix ,classes , serie , image , matiere , auteur , editeur));

                    }
                    itemAdapter = new GridAdapterRecherche(RechercheArticle.this, items);
                    list_article.setAdapter(itemAdapter);

                }
                catch (Exception w)
                {
                    Toast.makeText(RechercheArticle.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RechercheArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetdataPrimaire( ListView list_article ,  List<GridItem> items)
    {
        RequestQueue queue = Volley.newRequestQueue(RechercheArticle.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_primaire, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String titre = jsonObject.getString("titre");
                        String classes = jsonObject.getString("classe");
                        String serie = jsonObject.getString("serie");
                        //String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id") ;
                        if(serie.equals("Aucun"))
                            serie = "";

                        items.add(new GridItem(id , titre , prix ,classes, serie , image , matiere , "Autres" , editeur));

                    }
                    itemAdapter = new GridAdapterRecherche(RechercheArticle.this, items);
                    list_article.setAdapter(itemAdapter);

                }
                catch (Exception w)
                {
                    Toast.makeText(RechercheArticle.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RechercheArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    //fin de l'activite
}