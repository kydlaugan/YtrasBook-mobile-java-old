package com.example.ytrasbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2 ;
    ViewPagerAdapter viewPagerAdapter ;
    CardView recherche ;
    ImageView account  , panier;
    FloatingActionButton floatingActionButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager);
        account = (ImageView) findViewById(R.id.account);
        panier = (ImageView) findViewById(R.id.panier);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton) ;
        viewPagerAdapter = new ViewPagerAdapter(this) ;
        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
        recherche = (CardView) findViewById(R.id.recherche);
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , RechercheArticle.class);

                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this , LogIn.class );
                Intent intent1 =  new Intent(MainActivity.this , UserInfo.class);
                String user = openjson("data.json");
                if(user != null){
                    intent1.putExtra("id" , user);
                    startActivity(intent1);
                }
                else
                    startActivity(intent);
            }
        });

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSuccessDialog();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+237697887182"; // Numéro de téléphone
                String message = "Salut Très chers responsables de *YtrasBook* " + "\n" +  "\n" + " j'ai quelques préoccupations " + "\n" +  "Et j'aimerais que vous apportez des reponses. "; // Message
                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    private void showSuccessDialog() {
        ScrollView scrollView = findViewById(R.id.scrollview);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_panier , scrollView);
        Button fermer = view.findViewById(R.id.fermer);
        TextView nouvelle = view.findViewById(R.id.nouvelle);
        TextView ancienne =  view.findViewById(R.id.ancienne);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        fermer.findViewById(R.id.fermer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        nouvelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this , NouveauPanier.class);
                startActivity(intent);
            }
        });
        ancienne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this , AncienPanier.class);
                startActivity(intent);
            }
        });
        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public String openjson(String filename){
        JSONObject readJsonObject = null;
        String id = null;
        try (FileInputStream fis = MainActivity.this.openFileInput(filename)) {
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