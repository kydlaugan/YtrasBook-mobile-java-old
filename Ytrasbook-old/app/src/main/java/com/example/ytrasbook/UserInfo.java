package com.example.ytrasbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        String user_id = openjson("datas.json");
        getdata(user_id);
    }


    private void getdata(String user_id)
    {
        String url = "https://kydlaugan.pythonanywhere.com/api/user/";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String nom = jsonObject.getString("nom");
                        String prenom = jsonObject.getString("prenom");
                        String email = jsonObject.getString("email");
                        String contact1 = jsonObject.getString("contact");
                        String residence = jsonObject.getString("residence");
                        String id = jsonObject.getString("id") ;
                        TextView nom_edit = findViewById(R.id.nom) ;
                        TextView email_edit = findViewById(R.id.email) ;
                        TextView contact1_result = findViewById(R.id.contact1) ;
                        TextView residence_result = findViewById(R.id.residence) ;
                        if(id.equals(user_id)){
                            nom_edit.setText(nom + " "+ prenom);
                            email_edit.setText(email);
                            contact1_result.setText(contact1);
                            residence_result.setText(residence);
                        }

                    }

                }
                catch (Exception w)
                {
                    //Toast.makeText(UserInfo.this,w.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(UserInfo.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = UserInfo.this.openFileInput(filename)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = fis.read()) != -1) {
                content.append((char) character);
            }
            readJsonObject = new JSONObject(content.toString());
            id = readJsonObject.getString("id") ;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return  id ;

    }
    //fin de l'activite
}