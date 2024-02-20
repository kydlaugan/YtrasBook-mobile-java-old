package com.example.ytrasbook;

public class GridItem {
    private String id , titre , prix , classe , serie , image  , matiere , auteur , editeur ;
    public GridItem(String id, String titre , String prix , String classe ,String serie , String image , String matiere , String auteur , String editeur) {
        this.id = id;
        this.titre = titre ;
        this.prix = prix ;
        this.classe = classe ;
        this.serie = serie ;
        this.image = image ;
        this.matiere = matiere ;
        this.auteur = auteur ;
        this.editeur = editeur ;

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

    public String getSerie() {
        return serie;
    }
    public String getImage() {
        return image;
    }
    public String getMatiere() {
        return matiere;
    }
    public String getAuteur() {
        return auteur;
    }
    public String getEditeur() {
        return editeur ;
    }


}
