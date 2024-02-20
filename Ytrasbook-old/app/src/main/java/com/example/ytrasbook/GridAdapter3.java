package com.example.ytrasbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter3 extends BaseAdapter {

    private Context context;
    private List<GridItem2> items;
    public GridAdapter3(Context context, List<GridItem2> items) {
        this.context = context;
        this.items = items;
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
        GridAdapter3.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_commade, parent, false);
            holder = new GridAdapter3.ViewHolder();
            holder.nom = convertView.findViewById(R.id.nom);
            holder.niveau = convertView.findViewById(R.id.niveau);
            holder.serie = convertView.findViewById(R.id.serie);
            holder.quantite = convertView.findViewById(R.id.quantite);
            holder.prix_unitaire = convertView.findViewById(R.id.prix_unitaire);
            holder.prix_panier = convertView.findViewById(R.id.prix_panier);
            holder.img = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (GridAdapter3.ViewHolder) convertView.getTag();
        }

        GridItem2 currentItem = items.get(position);
        //holder.img.setImageResource(currentItem.getImg());
        holder.nom.setText(currentItem.getNom());
        holder.niveau.setText(currentItem.getNiveau());
        holder.serie.setText(currentItem.getSerie());
        holder.quantite.setText(currentItem.getQuantite());
        holder.prix_unitaire.setText(currentItem.getPrixUnitaire());
        holder.prix_panier.setText(currentItem.getPrixPanier());
        Picasso.get()
                .load(currentItem.getImg())
                .placeholder(R.drawable.loader)
                .into(holder.img);


        return convertView;
    }
    private static class ViewHolder {
        ImageView img ;
        TextView nom , niveau , serie , quantite  , prix_unitaire , prix_panier;
    }
}
