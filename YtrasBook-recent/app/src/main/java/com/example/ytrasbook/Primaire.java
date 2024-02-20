package com.example.ytrasbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Primaire extends Fragment {

    GridView gridView , gridView1  , gridView2 , gridView3 , gridView4 , gridView5 , gridView6,  gridView7 , gridView8 ;
    TextView voirplus , voirplus1 , voirplus2 , voirplus3 ,   voirplus4 , voirplus5 , voirplus6 , voirplus7 ,  voirplus8;
    private String url = "https://kydlaugan.pythonanywhere.com/api/primaire/" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_primaire, container, false);
        gridView = view.findViewById(R.id.gridview);
        gridView1 = view.findViewById(R.id.gridview1);
        gridView2 = view.findViewById(R.id.gridview2);
        gridView3 = view.findViewById(R.id.gridview3);
        gridView4 = view.findViewById(R.id.gridview4);
        gridView5 = view.findViewById(R.id.gridview5);
        gridView6 = view.findViewById(R.id.gridview6);
        gridView7 = view.findViewById(R.id.gridview7);
        gridView8 = view.findViewById(R.id.gridview8);
        voirplus = view.findViewById(R.id.voirplus);
        voirplus1 = view.findViewById(R.id.voirplus1);
        voirplus2 = view.findViewById(R.id.voirplus2);
        voirplus3 = view.findViewById(R.id.voirplus3);
        voirplus4 = view.findViewById(R.id.voirplus4);
        voirplus5 = view.findViewById(R.id.voirplus5);
        voirplus6 = view.findViewById(R.id.voirplus6);
        voirplus7 = view.findViewById(R.id.voirplus7);
        voirplus8 = view.findViewById(R.id.voirplus8);

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ImageSlider imageSlider1 = view.findViewById(R.id.image_slider1);
        ImageSlider imageSlider2 = view.findViewById(R.id.image_slider2);
        ImageSlider imageSlider3 = view.findViewById(R.id.image_slider3);


        //chargement des publicite
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pub1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub2 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub3 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub4 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider1.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider2.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider3.setImageList(slideModels , ScaleTypes.FIT);
        //chargement des articles
        getdata(gridView , gridView1 , gridView2 , gridView3 , gridView4 , gridView5 , gridView6 , gridView7 , gridView8);
        voirplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Mathematiques");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "TIC");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Sciences et Technologies");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Sciences à la citoyennete");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Français");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Ecriture");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Anglais");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Sciences Humaines et Sociales");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        voirplus8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext() , ListeCategorie.class);
                intent.putExtra("item" , "Litterature");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });
        return view ;
    }


    private void getdata( GridView list_article , GridView list_article_1 , GridView list_article_2  , GridView list_article_3 , GridView list_article_4 ,
                          GridView list_article_5 , GridView list_article_6 , GridView list_article_7 , GridView list_article_8 )
    {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                List<GridItem> items_1 = new ArrayList<>();
                List<GridItem> items_2 = new ArrayList<>();
                List<GridItem> items_3 = new ArrayList<>();
                List<GridItem> items_4 = new ArrayList<>();
                List<GridItem> items_5 = new ArrayList<>();
                List<GridItem> items_6 = new ArrayList<>();
                List<GridItem> items_7 = new ArrayList<>();
                List<GridItem> items_8 = new ArrayList<>();
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
                        String id = jsonObject.getString("id") ;
                        if(serie.equals("Aucun"))
                            serie = " " ;
                        if(classe.equals("SIL") || classe.equals("CP") || classe.equals("CE1") || classe.equals("CE2") || classe.equals("CM1") || classe.equals("CM2")){
                            if(matiere.equals("Mathematiques"))
                                items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));
                            else if (matiere.equals("TIC") )
                                items_1.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres", editeur));
                            else if (matiere.equals("Sciences et Technologies"))
                                items_2.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));
                            else if (matiere.equals("Sciences à la citoyenneté") )
                                items_3.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres", editeur));
                            else if (matiere.equals("Français"))
                                items_4.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));
                            else if (matiere.equals("Ecriture") )
                                items_5.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , "Autres", editeur));
                            else if (matiere.equals("Anglais"))
                                items_6.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));
                            else if (matiere.equals("Sciences Humaines et Sociales") )
                                items_7.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres", editeur));
                            else if (matiere.equals("Litterature"))
                                items_8.add(new GridItem(id , titre , prix ,classe , serie , image , matiere , "Autres" , editeur));


                        }

                    }
                    GridAdapter adapter = new GridAdapter(requireContext(), items);
                    list_article.setAdapter(adapter);
                    GridAdapter adapter_1 = new GridAdapter(requireContext(), items_1);
                    list_article_1.setAdapter(adapter_1);
                    GridAdapter adapter_2 = new GridAdapter(requireContext(), items_2);
                    list_article_2.setAdapter(adapter_2);
                    GridAdapter adapter_3 = new GridAdapter(requireContext(), items_3);
                    list_article_3.setAdapter(adapter_3);
                    GridAdapter adapter_4 = new GridAdapter(requireContext(), items_4);
                    list_article_4.setAdapter(adapter_4);
                    GridAdapter adapter_5 = new GridAdapter(requireContext(), items_5);
                    list_article_5.setAdapter(adapter_5);
                    GridAdapter adapter_6 = new GridAdapter(requireContext(), items_6);
                    list_article_6.setAdapter(adapter_6);
                    GridAdapter adapter_7 = new GridAdapter(requireContext(), items_7);
                    list_article_7.setAdapter(adapter_7);
                    GridAdapter adapter_8 = new GridAdapter(requireContext(), items_8);
                    list_article_8.setAdapter(adapter_8);


                }
                catch (Exception w)
                {
                    Toast.makeText(requireContext(),w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(),"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    //fin du fragment
}