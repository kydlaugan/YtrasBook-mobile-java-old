package com.example.ytrasbook;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // route vers la page principal
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //demarrage de la page
                Intent intent = new Intent(getApplicationContext() , onboarding.class);
                Intent intents = new Intent(getApplicationContext() , MainActivity.class);
                String id =  openjson("onboarding.json") ;
                if(id == null){
                    savedata("1");
                    startActivity(intent);
                }else
                    startActivity(intents);
                finish();
            }
        } ;
        // envoie du splash screen avec un delai
        new Handler().postDelayed(runnable , 3000);
    }

    //fin de l'activité
    public void savedata( String id ){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String fileName = "onboarding.json"; // Nom du fichier
        String data = jsonObject.toString(); // Convertir l'objet JSON en chaîne de caractères

        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = SplashScreen.this.openFileInput(filename)) {
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

    //fin de l'activite
}