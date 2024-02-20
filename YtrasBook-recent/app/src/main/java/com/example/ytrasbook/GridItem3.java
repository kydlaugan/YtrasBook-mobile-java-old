package com.example.ytrasbook;

public class GridItem3 {
    private String id , titre , prix , classe , nbre_pages , image  , paquets  ;
    public GridItem3(String id, String titre , String prix , String classe ,  String image , String nbre_pages , String paquets ) {
        this.id = id;
        this.titre = titre ;
        this.prix = prix ;
        this.classe = classe ;
        this.image = image ;
        this.nbre_pages = nbre_pages ;
        this.paquets = paquets ;

    }
    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }
    public String getPrix() {
        return prix;
    }
    public String getClasse() {
        return classe;
    }

    public String getNbre_pages() {
        return nbre_pages;
    }
    public String getImage() {
        return image;
    }
    public String getPaquets() {
        return paquets;
    }



}
