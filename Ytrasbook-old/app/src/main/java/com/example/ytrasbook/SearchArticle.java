package com.example.ytrasbook;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class SearchArticle extends AppCompatActivity {
    private SearchView searchView ;
    private ListView listview_show ;
    private GridAdapter2 itemApdater ;
    private List<GridItem> itemList  ;
    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

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
        listview_show = (ListView) findViewById(R.id.list);

        itemList = new ArrayList<>();
       // UpdateGrid(listview_show , itemList) ;
        getdata(listview_show , itemList);

        requestQueue = Volley.newRequestQueue(this);

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
            itemApdater.setFilteredList(filteredList);
        }

    }
    public void UpdateGrid(ListView list_article , List<GridItem> items){

        for (int i = 0; i <= 2 ; i++) {
           // items.add(new GridItem("Excel", "Seconde" , "15.000" , "Excellence",R.drawable.livre_tle));
           // items.add(new GridItem("Maths", "Seconde" , "15.000" ,"Excellence", R.drawable.livre_tle));
        }
        itemApdater = new GridAdapter2(this, items);
        list_article.setAdapter(itemApdater);

    }
    private void getdata( ListView list_article , List<GridItem> items)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String nom = jsonObject.getString("nom");
                        String niveau = jsonObject.getString("niveau");
                        String serie = jsonObject.getString("serie");
                        String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id");
                        if(matiere.equals("Education à la citoyenneté"))
                            matiere = "ECM" ;
                        if(matiere.equals("Langue et culture nationale"))
                            matiere = "LCN" ;

                        if(nom.length() >= 23 ){
                            nom = nom.substring(0, 23);
                        }
                        items.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));

                    }
                    itemApdater = new GridAdapter2(SearchArticle.this, items);
                    list_article.setAdapter(itemApdater);

                }
                catch (Exception w)
                {
                    Toast.makeText(SearchArticle.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    //fin de l'activite
}