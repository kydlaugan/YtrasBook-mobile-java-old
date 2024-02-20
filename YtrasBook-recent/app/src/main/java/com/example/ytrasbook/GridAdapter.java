package com.example.ytrasbook;
import static androidx.core.content.ContextCompat.startActivity;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_article, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.titre = convertView.findViewById(R.id.titre);
            holder.classe = convertView.findViewById(R.id.classe);
            holder.serie = convertView.findViewById(R.id.serie);
            holder.prix = convertView.findViewById(R.id.prix);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridItem currentItem = items.get(position);
        holder.titre.setText(currentItem.getTitre());
        holder.classe.setText(currentItem.getClasse());
        holder.serie.setText(currentItem.getSerie());
        holder.prix.setText(currentItem.getPrix());
        Picasso.get()
                .load(currentItem.getImage())
                .placeholder(R.drawable.loader)
                .into(holder.image);
       holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(((AppCompatActivity) context) , DetailsArticle.class);
                intent.putExtra("id" , currentItem.getId());
                intent.putExtra("titre", currentItem.getTitre());
                intent.putExtra("prix", currentItem.getPrix());
                intent.putExtra("classe", currentItem.getClasse());
                intent.putExtra("serie", currentItem.getSerie());
                intent.putExtra("image", currentItem.getImage());
                intent.putExtra("matiere", currentItem.getMatiere());
                intent.putExtra("auteur", currentItem.getAuteur());
                intent.putExtra("editeur", currentItem.getEditeur());
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView titre , classe , serie , prix ;
    }
}