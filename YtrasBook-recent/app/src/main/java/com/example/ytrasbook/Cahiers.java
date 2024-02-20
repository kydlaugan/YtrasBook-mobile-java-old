package com.example.ytrasbook;

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

public class Cahiers extends Fragment {
    private  String url = "https://kydlaugan.pythonanywhere.com/api/cahiers/" ;

    GridView gridView , gridView1  , gridView2 , gridView3 , gridView4 , gridView5 , gridView6,  gridView7 , gridView8 ;
    TextView voirplus , voirplus1 , voirplus2 , voirplus3 ,   voirplus4 , voirplus5 , voirplus6 , voirplus7 ,  voirplus8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cahiers, container, false);
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

        //chargement des données
        getdata(gridView , gridView1 , gridView2 , gridView3 , gridView4 , gridView5 , gridView6 , gridView7 , gridView8);


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

                List<GridItem3> items = new ArrayList<>();
                List<GridItem3> items_1 = new ArrayList<>();
                List<GridItem3> items_2 = new ArrayList<>();
                List<GridItem3> items_3 = new ArrayList<>();
                List<GridItem3> items_4 = new ArrayList<>();
                List<GridItem3> items_5 = new ArrayList<>();
                List<GridItem3> items_6 = new ArrayList<>();
                List<GridItem3> items_7 = new ArrayList<>();
                List<GridItem3> items_8 = new ArrayList<>();
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id") ;
                        String titre = jsonObject.getString("titre");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String classe = jsonObject.getString("classe");
                        String nbre_page = jsonObject.getString("nbre_page");
                        String paquets= jsonObject.getString("contenu_paquets");
                        String francophone = jsonObject.getString("francophone");
                        if(classe.equals("Aucun")){
                            if(nbre_page.equals("32 pages") || nbre_page.equals("50 pages"))
                                items.add(new GridItem3(id , titre , prix ,"", image ,nbre_page ,paquets ));


                        }

                    }
                    GridAdapterAccessoires adapter = new GridAdapterAccessoires(requireContext(), items);
                    list_article.setAdapter(adapter);
                    GridAdapterAccessoires adapter_1 = new GridAdapterAccessoires(requireContext(), items_1);
                    list_article_1.setAdapter(adapter_1);
                    GridAdapterAccessoires adapter_2 = new GridAdapterAccessoires(requireContext(), items_2);
                    list_article_2.setAdapter(adapter_2);
                    GridAdapterAccessoires adapter_3 = new GridAdapterAccessoires(requireContext(), items_3);
                    list_article_3.setAdapter(adapter_3);
                    GridAdapterAccessoires adapter_4 = new GridAdapterAccessoires(requireContext(), items_4);
                    list_article_4.setAdapter(adapter_4);
                    GridAdapterAccessoires adapter_5 = new GridAdapterAccessoires(requireContext(), items_5);
                    list_article_5.setAdapter(adapter_5);
                    GridAdapterAccessoires adapter_6 = new GridAdapterAccessoires(requireContext(), items_6);
                    list_article_6.setAdapter(adapter_6);
                    GridAdapterAccessoires adapter_7 = new GridAdapterAccessoires(requireContext(), items_7);
                    list_article_7.setAdapter(adapter_7);
                    GridAdapterAccessoires adapter_8 = new GridAdapterAccessoires(requireContext(), items_8);
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


    //fin da la l'activité
}