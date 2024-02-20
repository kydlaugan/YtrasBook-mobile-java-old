package com.example.ytrasbook;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import com.squareup.picasso.Picasso;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<GridItem> items;

    public GridAdapter(Context context, List<GridItem> items) {
        this.context = context;
        this.items = items;
    }
    public void setFilteredList(List<GridItem> filteredList){
        this.items = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0 ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_article, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.img);
            holder.matiere = convertView.findViewById(R.id.matiere);
            holder.classe = convertView.findViewById(R.id.classe);
            holder.prix = convertView.findViewById(R.id.prix);
            holder.nom = convertView.findViewById(R.id.nom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridItem currentItem = items.get(position);
       // holder.img.setImageResource(currentItem.getImg());
        holder.matiere.setText(currentItem.getMatiere());
        holder.classe.setText(currentItem.getClasse());
        holder.prix.setText(currentItem.getPrix());
        holder.nom.setText(currentItem.getNom());
        Picasso.get()
                .load(currentItem.getImg())
                .placeholder(R.drawable.loader)
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                TabDetails anotherFragment = new TabDetails();
                Bundle bundle = new Bundle();
                bundle.putString("matiere", currentItem.getMatiere());
                bundle.putString("classe", currentItem.getClasse());
                bundle.putString("prix", currentItem.getPrix());
                bundle.putString("nom", currentItem.getNom());
                bundle.putString("img", currentItem.getImg());
                bundle.putString("edition", currentItem.getEdition());
                bundle.putString("auteur", currentItem.getAuteur());
                bundle.putString("serie", currentItem.getSerie());
                bundle.putString("id", currentItem.getId());
                anotherFragment.setArguments(bundle);

                transaction.replace(R.id.frame_layout, anotherFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView img;
        TextView matiere , classe , prix , nom ;
    }
}