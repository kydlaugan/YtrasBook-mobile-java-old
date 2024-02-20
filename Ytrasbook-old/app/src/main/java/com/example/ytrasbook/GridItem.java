package com.example.ytrasbook;

public class GridItem {
    private String matiere;
    private String classe;
    private String prix;
    private String nom;

    private String img;
    private  String edition ;
    private String auteur ;
    private String serie ;
    private String id ;
    public GridItem(String matiere, String classe , String prix , String nom ,String img , String edition , String auteur , String serie , String id) {
        this.matiere = matiere;
        this.classe = classe;
        this.prix = prix;
        this.nom = nom;
        this.img = img;
        this.edition = edition;
        this.auteur = auteur;
        this.serie = serie;
        this.id = id;
    }
    public String getMatiere() {
        return matiere;
    }

    public String getClasse() {
        return classe;
    }
    public String getPrix() {
        return prix;
    }
    public String getNom() {
        return nom;
    }

    public String getImg() {
        return img;
    }
    public String getEdition() {
        return edition;
    }
    public String getAuteur() {
        return auteur;
    }
    public String getSerie() {
        return serie;
    }
    public String getId() {
        return id;
    }


}
