package com.example.ytrasbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterRecherche extends BaseAdapter {

    private Context context;
    private List<GridItem> items;

    public GridAdapterRecherche(Context context, List<GridItem> items) {
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
        GridAdapterRecherche.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liste_horizontale, parent, false);
            holder = new  ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.titre = convertView.findViewById(R.id.titre);
            holder.matiere = convertView.findViewById(R.id.matiere);
            holder.classe = convertView.findViewById(R.id.classe);
            holder.serie = convertView.findViewById(R.id.serie);
            holder.prix = convertView.findViewById(R.id.prix);
            convertView.setTag(holder);
        } else {
            holder = (GridAdapterRecherche.ViewHolder) convertView.getTag();
        }

        GridItem currentItem = items.get(position);
        holder.matiere.setText(currentItem.getMatiere());
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
                launchActivity(currentItem);
            }
        });

        return convertView;
    }
    private void launchActivity(GridItem currentItem) {
        Intent intent = new Intent((RechercheArticle) context, DetailsArticle.class);
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

    private static class ViewHolder {
        ImageView image;
        TextView matiere , titre , classe , serie , prix ;
    }
}
