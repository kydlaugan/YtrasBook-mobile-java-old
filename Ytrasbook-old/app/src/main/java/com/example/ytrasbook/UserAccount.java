package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAccount extends AppCompatActivity {
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Button sauvegarde = (Button)findViewById(R.id.sauvegarde);
        TextInputEditText nom , prenom , email , contact_1 , residence ,password_1 , password_2 ;
        nom = (TextInputEditText)findViewById(R.id.nom);
        prenom = (TextInputEditText)findViewById(R.id.prenom);
        email = (TextInputEditText)findViewById(R.id.email);
        contact_1 = (TextInputEditText)findViewById(R.id.telephone1);
        residence = (TextInputEditText)findViewById(R.id.residence);
        password_1 =(TextInputEditText)findViewById(R.id.password1);
        password_2 = (TextInputEditText)findViewById(R.id.password2);
        sauvegarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (password_1.getText().toString().trim().equals(password_2.getText().toString().trim())) {
                        postDataUsingVolley(nom.getText().toString(), prenom.getText().toString(), email.getText().toString(), contact_1.getText().toString(), residence.getText().toString(), password_1.getText().toString().trim());
                        //savedata(email.getText().toString(), contact_1.getText().toString(), contact_2.getText().toString(), password_1.getText().toString().trim());
                        finish();
                    } else
                        Toast.makeText(UserAccount.this, "Veillez confimer le bon mot de passe !", Toast.LENGTH_SHORT).show();
                //fin de la verification
            }
        });
    }

    private void postDataUsingVolley( String nom , String prenom , String email ,String contact_1 , String residence , String password) {
        // url to post our data
        String url = "https://kydlaugan.pythonanywhere.com/api/user/";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(UserAccount.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty

                // on below line we are displaying a success toast message.
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);
                    String id  = respObj.getString("id") ;
                    String email_result = respObj.getString("email");
                    String contact_1_result = respObj.getString("contact");
                    String password_result = respObj.getString("password");
                    String nom_result = respObj.getString("nom");
                    String prenom_result = respObj.getString("prenom");

                    savedata(id , email_result , contact_1_result, nom_result , prenom_result);

                    // below are the strings which we
                    // extract from our json object.


                    // on below line we are setting this string s to our text view.
                    Toast.makeText(UserAccount.this, "Enregistré !", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(UserAccount.this, "non enregistré", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("nom" , nom) ;
                params.put("prenom" , prenom) ;
                params.put("email" , email) ;
                params.put("contact", contact_1);
                params.put("residence" , residence) ;
                params.put("password" , password) ;

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
    //fin de l'activité
    public void savedata( String id , String email , String contact_1 , String nom , String prenom){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("email", email);
            jsonObject.put("contact_1", contact_1);
            jsonObject.put("nom", nom);
            jsonObject.put("prenom", prenom);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String fileName = "datas.json"; // Nom du fichier
        String data = jsonObject.toString(); // Convertir l'objet JSON en chaîne de caractères

        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//fin de l'activite
}
