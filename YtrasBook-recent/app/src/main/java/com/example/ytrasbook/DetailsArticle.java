package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsArticle extends AppCompatActivity {
    EditText edittext ;
    private String id , titre , prix , classe , serie , image  , matiere , auteur , editeur = null ;
    TextView ids , titres , prixs , classes , series  , matieres , auteurs , editeurs  , voirplus , voirplus1 ;
    ImageView images , previous;
    GridView gridView , gridView1 ;
    GridAdapter adapter  , adapter_1 ;
    CardView recherche ;
    Button panier  ;
    EditText quantite ;
    private RequestQueue requestQueue;

    private String url = "https://kydlaugan.pythonanywhere.com/api/secondaire/" ;
    private String url_primaire = "https://kydlaugan.pythonanywhere.com/api/primaire/" ;
    private String url_post = "https://kydlaugan.pythonanywhere.com/api/panier/" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_article);

        edittext = (EditText)findViewById(R.id.quantite);
        edittext.setFilters(new InputFilter[]{ new MinMaxValues("1", "100")});
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
            titre = extras.getString("titre");
            prix = extras.getString("prix");
            classe = extras.getString("classe");
            serie = extras.getString("serie");
            image = extras.getString("image");
            matiere = extras.getString("matiere");
            auteur = extras.getString("auteur");
            editeur = extras.getString("editeur");
        }
        titres = (TextView)findViewById(R.id.titre) ;
        prixs = (TextView)findViewById(R.id.prix) ;
        classes = (TextView)findViewById(R.id.classe) ;
        series = (TextView)findViewById(R.id.serie) ;
        matieres = (TextView)findViewById(R.id.matiere) ;
        auteurs = (TextView)findViewById(R.id.auteur) ;
        editeurs = (TextView)findViewById(R.id.editeur) ;
        voirplus = (TextView)findViewById(R.id.voirplus) ;
        voirplus1 = (TextView)findViewById(R.id.voirplus1) ;
        previous = (ImageView) findViewById(R.id.previous) ;
        images = (ImageView)findViewById(R.id.img) ;
        gridView = (GridView)findViewById(R.id.gridview) ;
        gridView1 = (GridView)findViewById(R.id.gridview1) ;
        panier = (Button) findViewById(R.id.panier) ;
        quantite = (EditText) findViewById(R.id.quantite);

        titres.setText(titre);
        prixs.setText(prix);
        classes.setText(classe);
        series.setText(serie);
        matieres.setText(matiere);
        auteurs.setText(auteur);
        editeurs.setText(editeur);
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.loader)
                .into(images);

        ImageSlider imageSlider = (ImageSlider) findViewById(R.id.image_slider);
        //chargement des publicite

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pub1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub2 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub3 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub4 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels , ScaleTypes.FIT);
        //chargement des données
        if(auteur.equals("Autres"))
            GetDataPrimaire(gridView , gridView1);
        else
            getdata(gridView , gridView1);

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsArticle.this , LogIn.class) ;
                String user_id = openjson("data.json");
                String prix_panier = String.valueOf( Integer.parseInt(quantite.getText().toString()) * Float.parseFloat(prix) ) ;
                if(user_id != null){
                    postData(user_id , id , quantite.getText().toString() , prix_panier);
                    showDialog();
                }else
                    startActivity(intent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        voirplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsArticle.this , ListeCategorie.class);
                intent.putExtra("item" , classe);
                intent.putExtra("item1" , " ");
                intent.putExtra("item2" , " ");
                intent.putExtra("item3" , " ");
                startActivity(intent);
            }
        });
        voirplus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsArticle.this , ListeCategorie.class);
                intent.putExtra("item" , "Art");
                intent.putExtra("item1" , "Latin");
                intent.putExtra("item2" , "Arts cinematographiques");
                intent.putExtra("item3" , "Arts Langue et culture nationale");
                startActivity(intent);
            }
        });

        recherche = (CardView) findViewById(R.id.recherche);
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsArticle.this , RechercheArticle.class);

                startActivity(intent);
            }
        });


    }

    private void getdata( GridView list_article , GridView list_article_1)
    {
        RequestQueue queue = Volley.newRequestQueue(DetailsArticle.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                List<GridItem> items_1 = new ArrayList<>();
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

                        if(classe.equals(classes))
                            items.add(new GridItem(id , titre , prix ,classes, serie , image , matiere , auteur , editeur));
                        items_1.add(new GridItem(id , titre , prix ,classes, serie , image , matiere , auteur , editeur));

                    }
                    adapter = new GridAdapter(DetailsArticle.this, items);
                    list_article.setAdapter(adapter);
                    adapter_1 = new GridAdapter(DetailsArticle.this, items_1);
                    list_article_1.setAdapter(adapter_1);

                }
                catch (Exception w)
                {
                    Toast.makeText(DetailsArticle.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void GetDataPrimaire( GridView list_article , GridView list_article_1)
    {
        RequestQueue queue = Volley.newRequestQueue(DetailsArticle.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_primaire, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                List<GridItem> items_1 = new ArrayList<>();
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String matiere = jsonObject.getString("matiere");
                        String titre = jsonObject.getString("titre");
                        String classes = jsonObject.getString("classe");
                        String serie = jsonObject.getString("serie");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id") ;
                        if(serie.equals("Aucun"))
                            serie = " " ;
                        if(classe.equals(classes))
                            items.add(new GridItem(id , titre , prix ,classes, serie , image , matiere , auteur , editeur));
                        items_1.add(new GridItem(id , titre , prix ,classes , serie , image , matiere , auteur , editeur));

                    }
                    adapter = new GridAdapter(DetailsArticle.this, items);
                    list_article.setAdapter(adapter);
                    adapter_1 = new GridAdapter(DetailsArticle.this, items_1);
                    list_article_1.setAdapter(adapter_1);

                }
                catch (Exception w)
                {
                    Toast.makeText(DetailsArticle.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void postData( String user_id , String article_id , String quantite ,String prix ) {
        RequestQueue queue = Volley.newRequestQueue(DetailsArticle.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url_post, new com.android.volley.Response.Listener<String>() {
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
                Toast.makeText(DetailsArticle.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("article_id" , article_id) ;
                params.put("user_id" , user_id) ;
                params.put("quantite" , quantite) ;
                params.put("prix", prix);
                params.put("envoye_commande" , "false");
                return params;
            }
        };
        queue.add(request);
    }
    public void showDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.show(fragmentManager , "Message");
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = DetailsArticle.this.openFileInput(filename)) {
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