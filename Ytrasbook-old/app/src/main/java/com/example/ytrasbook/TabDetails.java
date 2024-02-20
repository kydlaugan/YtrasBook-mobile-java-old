package com.example.ytrasbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.FileInputStream;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue requestQueue;


    public TabDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static TabDetails newInstance(String param1, String param2) {
        TabDetails fragment = new TabDetails();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_details, container, false);
        Bundle args = getArguments();
        String matiere_result = null;
        String classe_result = null;
        String prix_result = null;
        String img_result = null;
        String nom_result = null;
        String edition_result = null;
        String auteur_result = null;
        String serie_result = null;
        String id_result = null;
        if (args != null) {
            matiere_result = args.getString("matiere");
            classe_result = args.getString("classe");
            prix_result = args.getString("prix");
            img_result = args.getString("img");
            nom_result = args.getString("nom");
            edition_result = args.getString("edition");
            auteur_result = args.getString("auteur");
            serie_result = args.getString("serie");
            id_result = args.getString("id");
        }
        ImageView img;
        TextView matiere, classe, prix, edition , nom , auteur , serie ;
        TextView voirplus = view.findViewById(R.id.voirplus);
        TextView voirplus_1 = view.findViewById(R.id.voirplus1);
        EditText quantite = view.findViewById(R.id.quantite);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        img = view.findViewById(R.id.img);
        matiere = view.findViewById(R.id.matiere);
        classe = view.findViewById(R.id.classe);
        edition = view.findViewById(R.id.edition);
        auteur = view.findViewById(R.id.auteur);
        serie = view.findViewById(R.id.serie);
        nom = view.findViewById(R.id.nom);
        prix = view.findViewById(R.id.prix);


        matiere.setText(matiere_result);
        classe.setText(classe_result);
        prix.setText(prix_result);
        edition.setText(edition_result);
        nom.setText(nom_result);
        auteur.setText(auteur_result);
        serie.setText(serie_result);
        Picasso.get()
                .load(img_result)
                .into(img);
        //define image slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pack_7 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.cahier , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub1 , ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pub2 , ScaleTypes.FIT));
        imageSlider.setImageList(slideModels , ScaleTypes.FIT);

        GridView list_article = view.findViewById(R.id.listviewperso);
        GridView list_article_1 = view.findViewById(R.id.listviewperso1);
        getdata(list_article , list_article_1 , classe_result );

        Button panier = view.findViewById(R.id.panier);
        String finalId_result = id_result;
        String finalPrix_result = prix_result;

        char charToRemove = 'è';
        StringBuilder modifiedStringBuilder = new StringBuilder();

        for (char c : classe_result.toCharArray()) {
            if (c != charToRemove && c!= 'm' && c!='e') {
                modifiedStringBuilder.append(c);
            }
        }

        String finalClasse_result = modifiedStringBuilder.toString();
        voirplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabListArticle anotherFragment = new TabListArticle();
                Bundle bundle = new Bundle();
                bundle.putString("item", finalClasse_result.trim());
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
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , UserAccount.class) ;

                String user_id = openjson("datas.json");

                char charToRemove = '.';
                StringBuilder modifiedStringBuilder = new StringBuilder();

                for (char c : finalPrix_result.toCharArray()) {
                    if (c != charToRemove) {
                        modifiedStringBuilder.append(c);
                    }
                }

                String new_prix_result = modifiedStringBuilder.toString();

                //on verifie si l'objet json est vide
                if (user_id != null) {
                    String final_prix = String.valueOf(Integer.parseInt(quantite.getText().toString().trim()) * Float.parseFloat(new_prix_result.trim()));
                    if(quantite.getText().toString() != null ){
                        postDataUsingVolley(user_id, finalId_result, quantite.getText().toString(), final_prix);
                        showDialog();
                    }
                    else
                        Toast.makeText(requireContext(), "Veillez entrer une quantite", Toast.LENGTH_SHORT).show();
                } else
                    startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(requireContext());
        return view;
    }

    public void UpdateGrid(GridView list_article){

        List<GridItem> items = new ArrayList<>();

        for (int i = 0; i <= 2 ; i++) {
           // items.add(new GridItem("Excel", "Seconde" , "15.000" ,"Excellence" ,R.drawable.livre_tle));
        }
        GridAdapter adapter = new GridAdapter(requireContext(), items);
        list_article.setAdapter(adapter);

    }

    private void getdata( GridView list_article , GridView list_article_1 , String classe)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
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
                        String new_classe = classe.substring(0, 1);
                        if(niveau.equals(new_classe.trim()))
                            items.add(new GridItem(matiere, niveau + " ème", prix ,nom, image , editeur ,auteur ,serie , id));
                        else if (matiere.equals("PCT") || matiere.equals("Physique") || matiere.equals("Chimie") || matiere.equals("Français") || matiere.equals("Litterarture"))
                            items_1.add(new GridItem(matiere, niveau + " ème" , prix ,nom, image , editeur ,auteur ,serie , id));

                    }
                    GridAdapter adapter = new GridAdapter(requireContext(), items);
                    list_article.setAdapter(adapter);
                    GridAdapter adapter_1 = new GridAdapter(requireContext(), items_1);
                    list_article_1.setAdapter(adapter_1);

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
    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = requireContext().openFileInput(filename)) {
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

    private void postDataUsingVolley( String user , String article , String quantite ,String prix ) {
        // url to post our data
        String url = "https://kydlaugan.pythonanywhere.com/api/panier/";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                //email_username.setText("");



                // on below line we are displaying a success toast message.
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.


                    // on below line we are setting this string s to our text view.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(requireContext(),"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("user_id" , user) ;
                params.put("article_id" , article) ;
                params.put("quantite" , quantite) ;
                params.put("prix", prix);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
    public void showDialog(){
        FragmentManager fragmentManager = getParentFragmentManager();
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.show(fragmentManager , "Message");
    }

    //fin de la classe
}