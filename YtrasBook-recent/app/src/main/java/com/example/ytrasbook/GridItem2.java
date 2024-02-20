package com.example.ytrasbook;

public class GridItem2 {
    private String titre , niveau , serie , quantite , prixUnitaire , prixPanier , image;
    public GridItem2(String titre , String niveau , String serie , String quantite , String prixUnitaire , String prixPanier , String image){
        this.titre = titre ;
        this.niveau = niveau ;
        this.serie = serie ;
        this.quantite = quantite ;
        this.prixUnitaire = prixUnitaire ;
        this.prixPanier = prixPanier ;
        this.image = image ;
    }
    public String getTitre(){return titre ;}
    public String getNiveau(){return niveau ;}
    public String getSerie(){return serie ;}
    public String getQuantite(){return quantite ;}
    public String getPrixUnitaire(){return prixUnitaire ;}
    public String getPrixPanier(){return prixPanier ;}
    public String getImage(){return image ;}


}
