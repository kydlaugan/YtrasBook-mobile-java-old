package com.example.ytrasbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabListArticle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabListArticle extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue requestQueue;
    public TabListArticle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabListArticle.
     */
    // TODO: Rename and change types and number of parameters
    public static TabListArticle newInstance(String param1, String param2) {
        TabListArticle fragment = new TabListArticle();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_list_article, container, false);
        Bundle args = getArguments();
        String voirplus = null;
        String voirplus_1 = null;
        String voirplus_2 = null;
        String voirplus_3 = null;
        if (args != null) {
            voirplus = args.getString("item");
            voirplus_1 = args.getString("item1");
            voirplus_2 = args.getString("item2");
            voirplus_3 = args.getString("item3");
        }
        GridView list_article = view.findViewById(R.id.listviewperso);

        getdata(list_article , voirplus , voirplus_1 , voirplus_2 , voirplus_3);
        getdata2(list_article , voirplus , voirplus_1 , voirplus_2 , voirplus_3);
        requestQueue = Volley.newRequestQueue(requireContext());
        return view ;
    }

    private void getdata(GridView list_article , String voirplus ,String voirplus_1 , String voirplus_2 , String voirplus_3)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
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
                        String nom = jsonObject.getString("nom");
                        String niveau = jsonObject.getString("niveau");
                        String serie = jsonObject.getString("serie");
                        String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id");
                        if(nom.length() >= 23 ){
                            nom = nom.substring(0, 23);
                        }
                        if(matiere.equals(voirplus) || matiere.equals(voirplus_1)  || matiere.equals(voirplus_2) || matiere.equals(voirplus_3) ){
                            if(matiere.equals("Education à la citoyenneté"))
                                matiere = "ECM" ;
                            if(matiere.equals("Langue et culture nationale"))
                                matiere = "LCN" ;
                            if(matiere.equals("Arts cinematographiques"))
                                matiere = "Art-Cinéma" ;
                            items.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        }

                    }
                    GridAdapter adapter = new GridAdapter(requireContext(), items);
                    list_article.setAdapter(adapter);

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

    private void getdata2(GridView list_article , String voirplus ,String voirplus_1 , String voirplus_2 , String voirplus_3)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
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
                        String nom = jsonObject.getString("nom");
                        String niveau = jsonObject.getString("niveau");
                        String serie = jsonObject.getString("serie");
                        String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id");
                        if(nom.length() >= 23 ){
                            nom = nom.substring(0, 23);
                        }
                        if(niveau.equals(voirplus) || niveau.equals(voirplus_1)  || niveau.equals(voirplus_2) || niveau.equals(voirplus_3) ){
                            if(matiere.equals("Education à la citoyenneté"))
                                matiere = "ECM" ;
                            if(matiere.equals("Langue et culture nationale"))
                                matiere = "LCN" ;
                            if(matiere.equals("Arts cinematographiques"))
                                matiere = "Art-Cinéma" ;
                            items.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        }

                    }
                    GridAdapter adapter = new GridAdapter(requireContext(), items);
                    list_article.setAdapter(adapter);

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