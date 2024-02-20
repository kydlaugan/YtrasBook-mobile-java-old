package com.example.ytrasbook;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabCommande#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabCommande extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RequestQueue requestQueue;


    public TabCommande() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabCommande.
     */
    // TODO: Rename and change types and number of parameters
    public static TabCommande newInstance(String param1, String param2) {
        TabCommande fragment = new TabCommande();
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
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_tab_commande, container, false);
        ListView listview_show = view.findViewById(R.id.list);
        ListView listview_show_1 = view.findViewById(R.id.liste1);
        TextView somme = view.findViewById(R.id.somme);
        Button commande = view.findViewById(R.id.commande);
     /*   JSONObject json = openjson("datas.json");
        String user_id , contact , nom , prenom ;
        try {
            user_id = json.getString("id");
            contact = json.getString("contact_1");
            nom = json.getString("nom") ;
            prenom = json.getString("prenom") ;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } */

        JSONObject readJsonObject = null;
        String user_id = null, contact = null, nom = null, prenom  = null;
        try (FileInputStream fis = requireContext().openFileInput("datas.json")) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = fis.read()) != -1) {
                content.append((char) character);
            }
            readJsonObject = new JSONObject(content.toString());
            user_id = readJsonObject.getString("id");
            contact = readJsonObject.getString("contact_1");
            nom = readJsonObject.getString("nom") ;
            prenom = readJsonObject.getString("prenom") ;
            // Utilisez les valeurs lues comme bon vous semble
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //String user_id = openjson("data.json");
        getdata(listview_show , listview_show_1 ,user_id, somme);

        //UpdateGrid(listview_show) ;

        // recuperation de l'heure actuelle
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Mois commence à 0, donc ajoutez 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = year + "-" + month + "-" + day;
        String currentTime = hour + ":" + minute + ":" + second;
        String date_commande = currentDate + " "  + currentTime ;

        String finalUser_id = user_id;
        String finalContact = contact;
        String finalNom = nom;
        String finalPrenom = prenom;
        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataUsingVolley(finalUser_id, date_commande  , (String) somme.getText() );
                getdataOnPanier(finalUser_id, date_commande ,(String) somme.getText() , finalContact, finalNom, finalPrenom);

            }
        });

        requestQueue = Volley.newRequestQueue(requireContext());
        return view ;
    }
    public void UpdateGrid(ListView list_article ){

        List<GridItem2> items = new ArrayList<>();

        for (int i = 0; i <= 6 ; i++) {
            items.add(new GridItem2("Maths", "Seconde" , "15.000" ,"Excellence", "cle" ,"23","image"));
        }
        GridAdapter3 adapter = new GridAdapter3(requireContext(), items);
        list_article.setAdapter(adapter);

    }
    private void getdata(ListView list_article , ListView list_article_1 , String user_id , TextView somme)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/panier/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                List<GridItem2> items = new ArrayList<>();
                List<GridItem2> items2 = new ArrayList<>();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user = jsonObject.getString("user");
                        String article = jsonObject.getString("article");
                        String quantite = jsonObject.getString("quantite");
                        String prix_panier = jsonObject.getString("prix");
                        String status = jsonObject.getString("status");
                        String prix_unitaire = null;
                        String net_payer = "0" ;
                        String nom = null;
                        String niveau = null;
                        String serie = null;

                        if (user_id.trim().equals(user.trim())) {
                            prix_unitaire = String.valueOf(Float.parseFloat(prix_panier) / Integer.parseInt(quantite));
                            net_payer = String.valueOf(Float.parseFloat(prix_panier) + Integer.parseInt(net_payer));
                            String finalPrix_unitaire = prix_unitaire;
                            if(status.equals("En Cours")){
                                String test = (String) somme.getText();
                                somme.setText(String.valueOf(Float.parseFloat(test) + Float.parseFloat(net_payer)));

                            }
                           getdatatest(list_article , list_article_1 , article , status ,quantite,finalPrix_unitaire , prix_panier , items , items2);

                        }
                    }

                }
                catch (Exception w)
                {
                    //Toast.makeText(requireContext(),w.getMessage(),Toast.LENGTH_LONG).show();
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
    
    public JSONObject openjson(String filename){
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
        return  readJsonObject ;
    }

    private void postDataUsingVolley( String user , String date_commande , String prix ) {
        // url to post our data
        String url = "https://kydlaugan.pythonanywhere.com/api/commande/";

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
                params.put("user" , user) ;
                params.put("date_commande" , date_commande) ;
                params.put("prix" ,prix) ;
                params.put("description" ,"YtrasBook") ;

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getdataOnPanier( String user_id , String date_commande , String somme , String contact , String nom , String prenom)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/panier/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                String article_commande = " " ;
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String user = jsonObject.getString("user");
                        String article = jsonObject.getString("article");
                        String quantite = jsonObject.getString("quantite");
                        String prix = jsonObject.getString("prix");
                        String id  =  jsonObject.getString("id") ;
                        String status = jsonObject.getString("status");
                        if(user.equals(user_id)){
                            performUpdatePanier(id , user , article , quantite , prix);
                            if(status.equals("En Cours"))
                                article_commande = article_commande + " ;  " + article ;
                        }
                    }
                    if(article_commande.trim().equals("")){
                        Toast.makeText(requireContext(), "Vos paniers sont vides", Toast.LENGTH_SHORT).show();
                    }else{
                        SendEmailTask sendEmailTask = new SendEmailTask("ytras.services@gmail.com", "Commande de livres YtrasBook"  + "\n" , "Id utilisateur   :  " + user_id + "\n" + "Nom et Prenom  :  " + nom + "  " +prenom + "\n" + "Contact  :  " + contact  +  "\n" +" ID Article Commandés   :" + article_commande + "\n" +  "Date de Commande   :  " + date_commande + "\n" + "Net a payer  :" + somme);
                        sendEmailTask.execute();
                        Toast.makeText(requireContext(), "Commande Envoyé", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception w)
                {
                    //Toast.makeText(requireContext(),w.getMessage(),Toast.LENGTH_LONG).show();
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

    private void performUpdatePanier(String id , String user , String article , String quantite , String prix) {
        String updateUrl = "https://kydlaugan.pythonanywhere.com/api/panier/" + id + "/";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Réponse de succès du serveur
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestion des erreurs, par exemple affichage d'un message d'erreur
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("status", "Ajouté");
                params.put("user" , user) ;
                params.put("article" , article) ;
                params.put("quantite" , quantite) ;
                params.put("prix" , prix) ;
                return params;
            }
        };

        // Ajoutez la requête à la file d'attente de Volley
        Volley.newRequestQueue(requireContext()).add(stringRequest);
    }
    private void getdatatest( ListView list_article , ListView list_article_1  , String article , String status ,String quantite , String finalPrix_unitaire , String prix_panier , List<GridItem2> items, List<GridItem2> items2 )
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/article/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                try {

                        JSONObject jsonObject = jsonArray.getJSONObject(Integer.parseInt(article)-1);

                        String matiere = jsonObject.getString("matiere");
                        String nom = jsonObject.getString("nom");
                        String niveau = jsonObject.getString("niveau");
                        String serie = jsonObject.getString("serie");
                        String auteur = jsonObject.getString("auteur");
                        String editeur = jsonObject.getString("editeur");
                        String prix = jsonObject.getString("prix");
                        String image = jsonObject.getString("image");
                        String id = jsonObject.getString("id") ;
                        if(status.equals("En Cours")){
                            items.add(new GridItem2(nom, niveau + "  ème", serie, quantite, finalPrix_unitaire, prix_panier , image));
                            GridAdapter3 itemApdater = new GridAdapter3(requireContext(), items);
                            list_article.setAdapter(itemApdater);
                        }else{
                            items2.add(new GridItem2(nom, niveau + "  ème", serie, quantite, finalPrix_unitaire, prix_panier , image));
                            GridAdapter3 itemApdater = new GridAdapter3(requireContext(), items2);
                            list_article_1.setAdapter(itemApdater);
                        }
                }
                catch (Exception w)
                {
                   // Toast.makeText(requireContext(),w.getMessage(),Toast.LENGTH_LONG).show();
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

    public void savedata( String id ){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String fileName = "commande.json"; // Nom du fichier
        String data = jsonObject.toString(); // Convertir l'objet JSON en chaîne de caractères

        try (FileOutputStream fos = requireContext().openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //fin du fragment
}