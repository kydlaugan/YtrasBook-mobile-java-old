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

public class GridAdapter2 extends BaseAdapter {

    private Context context;
    private List<GridItem2> items;
    public GridAdapter2(Context context, List<GridItem2> items) {
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_commade, parent, false);
            holder = new ViewHolder();
            holder.titre = convertView.findViewById(R.id.titre);
            holder.niveau = convertView.findViewById(R.id.niveau);
            holder.serie = convertView.findViewById(R.id.serie);
            holder.quantite = convertView.findViewById(R.id.quantite);
            holder.prix_unitaire = convertView.findViewById(R.id.prix_unitaire);
            holder.prix_panier = convertView.findViewById(R.id.prix_panier);
            holder.img = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridItem2 currentItem = items.get(position);
        //holder.img.setImageResource(currentItem.getImg());
        holder.titre.setText(currentItem.getTitre());
        holder.niveau.setText(currentItem.getNiveau());
        holder.serie.setText(currentItem.getSerie());
        holder.quantite.setText(currentItem.getQuantite());
        holder.prix_unitaire.setText(currentItem.getPrixUnitaire());
        holder.prix_panier.setText(currentItem.getPrixPanier());
        Picasso.get()
                .load(currentItem.getImage())
                .placeholder(R.drawable.loader)
                .into(holder.img);


        return convertView;
    }
    private static class ViewHolder {
        ImageView img ;
        TextView titre , niveau , serie , quantite  , prix_unitaire , prix_panier;
    }
}
