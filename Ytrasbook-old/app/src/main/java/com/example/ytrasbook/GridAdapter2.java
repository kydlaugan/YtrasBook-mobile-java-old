package com.example.ytrasbook;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter2 extends BaseAdapter {

    private Context context;
    private List<GridItem> items;

    public GridAdapter2(Context context, List<GridItem> items) {
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
        GridAdapter2.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_horizontal, parent, false);
            holder = new GridAdapter2.ViewHolder();
            holder.img = convertView.findViewById(R.id.img);
            holder.nom = convertView.findViewById(R.id.nom);
            holder.matiere = convertView.findViewById(R.id.matiere);
            holder.classe = convertView.findViewById(R.id.classe);
            holder.prix = convertView.findViewById(R.id.prix);
            convertView.setTag(holder);
        } else {
            holder = (GridAdapter2.ViewHolder) convertView.getTag();
        }

        GridItem currentItem = items.get(position);
        //holder.img.setImageResource(currentItem.getImg());
        holder.nom.setText(currentItem.getNom());
        holder.matiere.setText(currentItem.getMatiere());
        holder.classe.setText(currentItem.getClasse());
        holder.prix.setText(currentItem.getPrix());
        Picasso.get()
                .load(currentItem.getImg())
                .placeholder(R.drawable.loader)
                .into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(currentItem);
            }
        });

        return convertView;
    }
    private void launchActivity(GridItem item) {
        Intent intent = new Intent((SearchArticle) context, MainActivity.class);
        intent.putExtra("matiere", item.getMatiere()); // Mettez les données de l'élément dans l'intent si nécessaire
        intent.putExtra("classe", item.getClasse());
        intent.putExtra("prix", item.getPrix());
        intent.putExtra("img", item.getImg());
        intent.putExtra("nom", item.getNom());
        intent.putExtra("edition", item.getEdition());
        intent.putExtra("auteur", item.getAuteur());
        intent.putExtra("serie", item.getSerie());
        intent.putExtra("id", item.getId());
        context.startActivity(intent);
    }

    private static class ViewHolder {
        ImageView img;
        TextView matiere , classe , prix , nom ;
    }
}
