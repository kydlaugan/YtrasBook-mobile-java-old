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


public class Maternelle extends Fragment {

    GridView gridView , gridView1  , gridView2 ;
    GridAdapter adapter ;
    TextView voirplus , voirplus1 , voirplus2 ;
    private String url = "https://kydlaugan.pythonanywhere.com/api/primaire/" ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maternelle, container, false);
        gridView = view.findViewById(R.id.gridview);
        gridView1 = view.findViewById(R.id.gridview1);
        gridView2 = view.findViewById(R.id.gridview2);
        voirplus = view.findViewById(R.id.voirplus);
        voirplus1 = view.findViewById(R.id.voirplus1);
        voirplus2 = view.findViewById(R.id.voirplus2);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ImageSlider imageSlider1 = view.findViewById(R.id.image_slider1);


        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pub1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub2 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub3 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub4 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider1.setImageList(slideModels , ScaleTypes.FIT);

        //chargement des articles
        getdata(gridView , gridView1 , gridView2);

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
                intent.putExtra("item" , "Langage");
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
                intent.putExtra("item" , "Dessin-peinture-coloriage");
                intent.putExtra("item1" , "Autres");
                intent.putExtra("item2" , "");
                intent.putExtra("item3" , "");
                startActivity(intent);
            }
        });

        return view ;
    }


    private void getdata( GridView list_article , GridView list_article_1 , GridView list_article_2 )
    {
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                List<GridItem> items = new ArrayList<>();
                List<GridItem> items_1 = new ArrayList<>();
                List<GridItem> items_2 = new ArrayList<>();
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
                        if(classe.equals("1ère Maternelle") || classe.equals("2ème Maternelle")){
                            if(matiere.equals("Mathematiques"))
                                items.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));
                            else if (matiere.equals("Langage") )
                                items_1.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres", editeur));
                            else if (matiere.equals("Dessin-peinture-coloriage"))
                                items_2.add(new GridItem(id , titre , prix ,classe, serie , image , matiere , "Autres" , editeur));

                        }

                    }
                    GridAdapter adapter = new GridAdapter(requireContext(), items);
                    list_article.setAdapter(adapter);
                    GridAdapter adapter_1 = new GridAdapter(requireContext(), items_1);
                    list_article_1.setAdapter(adapter_1);
                    GridAdapter adapter_2 = new GridAdapter(requireContext(), items_2);
                    list_article_2.setAdapter(adapter_2);


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