package com.example.ytrasbook;


  
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.Toast;
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
                  Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();
              }
           });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                                  Toast.makeText(getActivity(),"Cancel",Toast.LENGTH_SHORT).show();
              }
        });
          AlertDialog alertDialog=builder.create();
          return alertDialog;
      }
   }
