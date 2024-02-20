package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class ListeCategorie extends AppCompatActivity {
    String item  , item1 , item2 , item3= null ;
    GridAdapter adapter ;
    GridView gridView ;
    TextView categorie ;
    ImageView previous ;
    private RequestQueue requestQueue;

    private String url = "https://kydlaugan.pythonanywhere.com/api/secondaire/" ;
    private String url_primaire = "https://kydlaugan.pythonanywhere.com/api/primaire/" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_categorie);
        Bundle extras = getIntent().getExtras();
        if(extras != null ){
            item = extras.getString("item");
            item1 = extras.getString("item1");
            item2 = extras.getString("item2");
            item3 = extras.getString("item3");
        }

        gridView = (GridView) findViewById(R.id.gridview);
        categorie = (TextView) findViewById(R.id.categorie);
        previous = (ImageView) findViewById(R.id.previous);
        categorie.setText(item);
        if(item.equals("1ère Maternelle") || item.equals("2ème Maternelle") || item.equals("SIL") || item.equals("CP") || item.equals("CE1") || item.equals("CE2" + " ème") || item.equals("CM1") || item.equals("CM2") || item1.equals("Autres"))
            GetDataPrimaire(gridView);
        else
            getdata(gridView);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
/*
    public void UpdateGrid(GridView list_article ){
        List<GridItem> items = new ArrayList<>();

        for (int i = 0; i <= 30  ; i++) {
            if(item.equals("Mathematiques"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("PCT"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Informatique"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("SVT"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Français"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Anglais"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Histoire"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Oeuvres"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Langues Etrangères"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Autres"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Mathematique"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"4" + " ème", " C " , String.valueOf(R.drawable.svt4) , "Mathematiques" , "KydLaugan et autres" , "NORTTHON"));
            else if (item.equals("Langage"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"Maternelle 1ere", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Dessin"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"Maternelle 1ere", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Maths"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("TIC"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Sciences et Technologie"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Sciences à la citoyennete"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Françai"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Ecriture"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Anglai"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Sciences Humaines et Sociales"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("Litteratur"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));
            else if (item.equals("5"))
                items.add(new GridItem(String.valueOf(i) , " Excellence en Mathematiques" , "15.000" ,"SIL", "" , String.valueOf(R.drawable.svt4) , "Mathematiques" , "" , "NORTTHON"));

        }
        adapter = new GridAdapter(this, items);
        list_article.setAdapter(adapter);
    }
*/

    private void getdata( GridView list_article )
    {
        RequestQueue queue = Volley.newRequestQueue(ListeCategorie.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String titre = jsonObject.getString("titre");
                        String classe = jsonObject.getString("classe");
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

                        if(item.equals(matiere) || item1.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere) || matiere.equals(item1) || matiere.equals(item2))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere) || item1.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere)|| item1.equals(matiere) || item2.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere)  || item1.equals(matiere) || item2.equals(matiere) || item3.contains(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Mathematique"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Langage"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Dessin"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Maths"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("TIC"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Sciences et Technologie"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Sciences à la citoyennete"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Françai"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Ecriture"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Anglai"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Sciences Humaines et Sociales"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals("Litteratur"))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(classe))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));

                    }
                    adapter = new GridAdapter(ListeCategorie.this, items);
                    list_article.setAdapter(adapter);

                }
                catch (Exception w)
                {
                    //Toast.makeText(ListeCategorie.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListeCategorie.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetDataPrimaire( GridView list_article )
    {
        RequestQueue queue = Volley.newRequestQueue(ListeCategorie.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_primaire, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String titre = jsonObject.getString("titre");
                        String classe = jsonObject.getString("classe");
                        String serie = jsonObject.getString("serie");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String auteur =  "Autres" ;
                        String id = jsonObject.getString("id") ;
                        if(serie.equals("Aucun"))
                            serie = " " ;

                        if(item.equals(matiere) || item1.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere) || matiere.equals(item1) || matiere.equals(item2))
                            items.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere) || item1.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere)|| item1.equals(matiere) || item2.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere))
                            items.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , auteur , editeur));
                        else if (item.equals(matiere)  || item1.equals(matiere) || item2.equals(matiere) || item3.contains(matiere))
                            items.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , auteur , editeur));
                        else if (item.equals(classe))
                            items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , auteur , editeur));

                    }
                    adapter = new GridAdapter(ListeCategorie.this, items);
                    list_article.setAdapter(adapter);

                }
                catch (Exception w)
                {
                    //Toast.makeText(ListeCategorie.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListeCategorie.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
    //fin de l'activite
}