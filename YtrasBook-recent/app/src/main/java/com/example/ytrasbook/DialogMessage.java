package com.example.ytrasbook;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogMessage extends DialogFragment {

     @NonNull
     @Override
     public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

         AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
         builder.setTitle("YtrasBook");
         builder.setMessage("Article ajouté au panier ! consultez vos commandes");
         builder.setPositiveButton("Ok!!", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
          });
         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
       });
         AlertDialog alertDialog=builder.create();
         return alertDialog;
     }
  }
