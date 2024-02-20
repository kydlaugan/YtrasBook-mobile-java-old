package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {
    TextView creer_compte ;
    MaterialButton connexion ;
    private TextInputLayout contact , password ;
    ScrollView scrollView ;
    private RequestQueue requestQueue;

    private String url = "https://kydlaugan.pythonanywhere.com/api/user/" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

         creer_compte = (TextView) findViewById(R.id.creer_compte) ;
         connexion = (MaterialButton) findViewById(R.id.connexion) ;
         contact = (TextInputLayout) findViewById(R.id.contact) ;
         password = (TextInputLayout) findViewById(R.id.password) ;
         scrollView = (ScrollView)findViewById(R.id.scrollview) ;

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation() == true){
                    getdata(contact.getEditText().getText().toString() , password.getEditText().getText().toString());
                }else{
                    Snackbar snackbar = Snackbar.make(scrollView , "Information Incorrecte" , Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        creer_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this , Register.class);
                startActivity(intent);
            }
        });
    }

    private void getdata(String contact_enter , String password_enter)
    {
        RequestQueue queue = Volley.newRequestQueue(LogIn.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;

                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String email = jsonObject.getString("email");
                        String contact = jsonObject.getString("contact");
                        String password = jsonObject.getString("password");

                        if (email.equals(contact_enter) && password.equals(password_enter) || contact.equals(contact_enter) && password.equals(password_enter)){
                            Intent intent = new Intent(LogIn.this , UserInfo.class);
                            savedata(id);
                            intent.putExtra("id" , id);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
                catch (Exception w)
                {
                    Toast.makeText(LogIn.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogIn.this,"Pas de Connexion",Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private  boolean validation(){
        boolean response = true ;
        contact = (TextInputLayout) findViewById(R.id.contact);
        password = (TextInputLayout) findViewById(R.id.password);
        if(contact.getEditText().getText().toString().isEmpty()){
            contact.setError("Veillez remplir ce champ");
            response = false;
        } else if (password.getEditText().getText().toString().isEmpty()) {
            password.setError("Veillez remplir ce champ");
            response = false;

        }

        return response ;
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = LogIn.this.openFileInput(filename)) {
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

    public void savedata( String id){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);

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


    //fin de l'activité
}