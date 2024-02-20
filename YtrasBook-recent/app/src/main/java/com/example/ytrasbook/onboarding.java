package com.example.ytrasbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Button suivant = (Button) findViewById(R.id.suivant);
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(onboarding.this  , MainActivity.class);
                savedata("1");
                startActivity(intent);
                finish();
            }
        });
    }

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

    //fin de l'activite
}