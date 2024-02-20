package com.example.ytrasbook;

public class GridItem2 {
    private String nom , niveau , serie , quantite , prixUnitaire , prixPanier , img;
    public GridItem2(String nom , String niveau , String serie , String quantite , String prixUnitaire , String prixPanier , String img){
        this.nom = nom ;
        this.niveau = niveau ;
        this.serie = serie ;
        this.quantite = quantite ;
        this.prixUnitaire = prixUnitaire ;
        this.prixPanier = prixPanier ;
        this.img = img ;
    }
    public String getNom(){return nom ;}
    public String getNiveau(){return niveau ;}
    public String getSerie(){return serie ;}
    public String getQuantite(){return quantite ;}
    public String getPrixUnitaire(){return prixUnitaire ;}
    public String getPrixPanier(){return prixPanier ;}
    public String getImg(){return img ;}


}
