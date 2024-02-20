package com.example.ytrasbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

// bibliotheque ajouté
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import kotlin.reflect.KFunction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue requestQueue;
    public TabHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabHome.
     */
    // TODO: Rename and change types and number of parameters
    public static TabHome newInstance(String param1, String param2) {
        TabHome fragment = new TabHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tab_home, container, false);
        View views = LayoutInflater.from(getContext()).inflate(R.layout.list_article, container, false);

        GridView list_article = view.findViewById(R.id.listviewperso);
        GridView list_article_1 = view.findViewById(R.id.listviewperso1);
        GridView list_article_2 = view.findViewById(R.id.listviewperso2);
        GridView list_article_3 = view.findViewById(R.id.listviewperso3);
        GridView list_article_4 = view.findViewById(R.id.listviewperso4);
        GridView list_article_5 = view.findViewById(R.id.listviewperso5);
        GridView list_article_6 = view.findViewById(R.id.listviewperso6);
        GridView list_article_7 = view.findViewById(R.id.listviewperso7);
        GridView list_article_8 = view.findViewById(R.id.listviewperso8);
        GridView list_article_9 = view.findViewById(R.id.listviewperso9);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ImageSlider imageSlider1 = view.findViewById(R.id.image_slider1);
        ImageSlider imageSlider2 = view.findViewById(R.id.image_slider2);
        ImageSlider imageSlider3 = view.findViewById(R.id.image_slider3);
        ImageSlider imageSlider4 = view.findViewById(R.id.image_slider4);
        ImageView img = views.findViewById(R.id.img) ;
        TextView categorie = view.findViewById(R.id.categorie) ;

        //define image slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pack_7 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.cahier , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub2 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider1.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider2.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider3.setImageList(slideModels , ScaleTypes.FIT);
        imageSlider4.setImageList(slideModels , ScaleTypes.FIT);
        getdata( list_article , list_article_1 , list_article_2 , list_article_3 , list_article_4 , list_article_5 , list_article_6 , list_article_7 , list_article_8 ,list_article_9);

        TextView voirplus = view.findViewById(R.id.voirplus);
        TextView voirplus_1 = view.findViewById(R.id.voirplus1);
        TextView voirplus_2 = view.findViewById(R.id.voirplus2);
        TextView voirplus_3 = view.findViewById(R.id.voirplus3);
        TextView voirplus_4 = view.findViewById(R.id.voirplus4);
        TextView voirplus_5 = view.findViewById(R.id.voirplus5);
        TextView voirplus_6 = view.findViewById(R.id.voirplus6);
        TextView voirplus_7 = view.findViewById(R.id.voirplus7);
        TextView voirplus_8 = view.findViewById(R.id.voirplus8);
        TextView voirplus_9 = view.findViewById(R.id.voirplus9);
        voirplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Mathematiques");
                bundle.putString("item1", "Sciences Tles Litteraires");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Physique");
                bundle.putString("item1", "Chimie");
                bundle.putString("item2", "PCT");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Informatique");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Sciences");
                bundle.putString("item1", "SVT");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Langue Française");
                bundle.putString("item1", "Litterature");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Anglais");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Histoire");
                bundle.putString("item1", "Geographie");
                bundle.putString("item2", "Education à la citoyenneté");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Litterature");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Langues Etrangère");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        voirplus_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", "Art");
                bundle.putString("item1", "Latin");
                bundle.putString("item2", "Arts cinematographiques");
                bundle.putString("item3", "Langue et culture nationale");
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        requestQueue = Volley.newRequestQueue(requireContext());
        return view;
    }
    // fonction pour remplir la gridview
    public void UpdateGrid(GridView list_article ){

        List<GridItem> items = new ArrayList<>();

        for (int i = 0; i <= 2 ; i++) {
            // items.add(new GridItem("Excel", "Seconde" , "15.000" ,"Excellence", R.drawable.livre_tle));
        }
        GridAdapter adapter = new GridAdapter(requireContext(), items);
        list_article.setAdapter(adapter);

    }

    private void getdata( GridView list_article , GridView list_article_1 , GridView list_article_2 , GridView list_article_3 , GridView list_article_4 ,
                         GridView list_article_5 , GridView list_article_6 , GridView list_article_7 , GridView list_article_8 , GridView list_article_9)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
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
                List<GridItem> items_9 = new ArrayList<>();
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
                        String id = jsonObject.getString("id") ;
                        if(matiere.equals("Education à la citoyenneté"))
                            matiere = "ECM" ;
                        if(matiere.equals("Langue et culture nationale"))
                            matiere = "LCN" ;

                        if(nom.length() >= 23 ){
                             nom = nom.substring(0, 23);
                        }
                        if(matiere.equals("Mathematiques"))
                            items.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("PCT") || matiere.equals("Physique") || matiere.equals("Chimie") )
                            items_1.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Informatique"))
                            items_2.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("SVT") || matiere.equals("Sciences"))
                            items_3.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Langue Française"))
                            items_4.add(new GridItem(matiere,niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Anglais"))
                            items_5.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Education à la citoyenneté") || matiere.equals("Histoire") || matiere.equals("Geographie"))
                            items_6.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Litterature"))
                            items_7.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Langues Etrangère"))
                            items_8.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("Art") || matiere.equals("Latin")  || matiere.equals("Arts cinematographiques") || matiere.equals("Langue et culture nationale"))
                            items_9.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));

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
                    GridAdapter adapter_9 = new GridAdapter(requireContext(), items_9);
                    list_article_9.setAdapter(adapter_9);

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