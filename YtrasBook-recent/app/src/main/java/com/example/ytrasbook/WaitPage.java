package com.example.ytrasbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WaitPage extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_page, container, false);


        // route vers la page principal
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //demarrage de la page

            }
        } ;
        // envoie du splash screen avec un delai
        new Handler().postDelayed(runnable , 3000);

        return  view ;
    }
}