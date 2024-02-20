package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private TextInputLayout nom , prenom ,  residence  , contact , email , password  , password1;
    private ScrollView scrollView ;
    private String url_post = "https://kydlaugan.pythonanywhere.com/api/user/" ;
    private MaterialButton button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nom = (TextInputLayout) findViewById(R.id.nom);
        prenom = (TextInputLayout) findViewById(R.id.prenom);
        residence = (TextInputLayout) findViewById(R.id.residence);
        contact = (TextInputLayout) findViewById(R.id.contact);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        password1 = (TextInputLayout) findViewById(R.id.password1);
        button  = (MaterialButton) findViewById(R.id.enregistrer);
        scrollView = (ScrollView)findViewById(R.id.scrollview) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()==true){
                    if(password.getEditText().getText().toString().equals(password1.getEditText().getText().toString())){
                        postData(nom.getEditText().getText().toString() , prenom.getEditText().getText().toString() , residence.getEditText().getText().toString() , contact.getEditText().getText().toString() , email.getEditText().getText().toString() , password.getEditText().getText().toString());
                        Toast.makeText(Register.this, "Enregistré", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Snackbar snackbar = Snackbar.make(scrollView , "Mot de passe différent" , Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }
                }else{
                    Snackbar snackbar = Snackbar.make(scrollView , "Compte non enregistré" , Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

                }

            }
        });

    }

    public void savedata( String id , String nom , String prenom , String residence , String contact , String email , String password){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("nom", nom);
            jsonObject.put("prenom", prenom);
            jsonObject.put("residence", residence);
            jsonObject.put("contact", contact);
            jsonObject.put("email", email);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String fileName = "data.json"; // Nom du fichier
        String data = jsonObject.toString(); // Convertir l'objet JSON en chaîne de caractères

        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postData(String nom , String prenom , String residence , String contact , String email , String password ) {
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url_post, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    String new_id = respObj.getString("id") ;
                    String new_nom = respObj.getString("nom") ;
                    String new_prenom = respObj.getString("prenom") ;
                    String new_residence = respObj.getString("residence") ;
                    String new_contact = respObj.getString("contact") ;
                    String new_email = respObj.getString("email") ;
                    String new_password = respObj.getString("password") ;
                    savedata(new_id ,new_nom , new_prenom , new_residence , new_contact , new_email , new_password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Register.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nom" , nom) ;
                params.put("prenom" , prenom) ;
                params.put("residence" , residence) ;
                params.put("contact", contact);
                params.put("email" , email);
                params.put("password" , password);
                return params;
            }
        };
        queue.add(request);
    }

    private  boolean validation(){
        boolean response = true ;
        nom = (TextInputLayout) findViewById(R.id.nom);
        prenom = (TextInputLayout) findViewById(R.id.prenom);
        residence = (TextInputLayout) findViewById(R.id.residence);
        contact = (TextInputLayout) findViewById(R.id.contact);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        password1 = (TextInputLayout) findViewById(R.id.password1);
        if(nom.getEditText().getText().toString().isEmpty()){
            nom.setError("Veillez remplir ce champ");
            response = false;
        }
        if(prenom.getEditText().getText().toString().isEmpty()){
            prenom.setError("Veillez remplir ce champ");
            response = false;
        }
        if(residence.getEditText().getText().toString().isEmpty()){
            residence.setError("Veillez remplir ce champ");
            response = false;
        }
        if(contact.getEditText().getText().toString().isEmpty()){
            contact.setError("Veillez remplir ce champ");
            response = false;
        }
        if(email.getEditText().getText().toString().isEmpty()){
            email.setError("Veillez remplir ce champ");
            response = false;
        }
        if(password.getEditText().getText().toString().isEmpty()){
            password.setError("Veillez remplir ce champ");
            response = false;
        }
        if(password1.getEditText().getText().toString().isEmpty()){
            password1.setError("Veillez remplir ce champ");
            response = false;
        }

       return response ;
    }
    //fin de l'activite
}