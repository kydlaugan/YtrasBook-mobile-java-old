package com.example.ytrasbook;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import androidx.appcompat.widget.SearchView;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set default
        //setFragment(new TabHome());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.home) {
                    setFragment(new TabHome());

                } else if (id == R.id.store) {
                    setFragment(new TabStore());

                } else if (id == R.id.setting) {
                    setFragment(new TabBasket());

                }else if (id == R.id.commande) {
                    setFragment(new TabCommande());

                }

                return true;
            }
        });

        ImageView searchButton = findViewById(R.id.search_button); // Remplacez par l'ID de votre bouton
        ImageView account  = findViewById(R.id.account) ;
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents =  new Intent(MainActivity.this , UserInfo.class);
                startActivity(intents);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this , SearchArticle.class);
                startActivity(intent);
            }
        });

        String matiere = getIntent().getStringExtra("matiere");
        String classe = getIntent().getStringExtra("classe");
        String prix = getIntent().getStringExtra("prix");
        String nom = getIntent().getStringExtra("nom");
        String img = getIntent().getStringExtra("img");
        String edition = getIntent().getStringExtra("edition");
        String auteur = getIntent().getStringExtra("auteur");
        String serie = getIntent().getStringExtra("serie");
        String id = getIntent().getStringExtra("id");
        if(matiere!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            TabDetails fragment = new TabDetails();
            Bundle bundle = new Bundle();
            bundle.putString("matiere", matiere);
            bundle.putString("classe", classe);
            bundle.putString("prix", prix);
            bundle.putString("nom", nom);
            bundle.putString("img", img);
            bundle.putString("edition", edition);
            bundle.putString("auteur", auteur);
            bundle.putString("serie", serie);
            bundle.putString("id", id);
            fragment.setArguments(bundle);

            transaction.replace(R.id.frame_layout, fragment);
            transaction.commit();
        }else{
            if(haveInternetConnection()== true)
                setFragment(new TabHome());
            else
                setFragment(new Coonection());
        }

        //fin du oncreate
    }
    private void setFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void showSearchDialog() {
        SearchView searchView = new SearchView(this); // Ou utilisez getContext() si vous êtes dans un fragment
        searchView.setQueryHint("Faites  une recherche ");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Recherchez  un livre");
        dialogBuilder.setView(searchView);
        dialogBuilder.setPositiveButton("Rechercher", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String query = searchView.getQuery().toString();
                showSearchResults(query);
            }
        });
        dialogBuilder.setNegativeButton("Annuler", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void showSearchResults(String query) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Résultats de la recherche");
        builder.setMessage("Résultats pour : " + query);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private boolean haveInternetConnection(){
        // Fonction haveInternetConnection : return true si connecté, return false dans le cas contraire
        NetworkInfo network = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (network==null || !network.isConnected())
        {
            // Le périphérique n'est pas connecté à Internet
            return false;
        }
        return true;
    }

//fin de la classe
}