package com.example.pc.gestiontissage.Models;

/**
 * Created by pc on 12/04/2018.
 */

public class Employe {

    private String nom;

    private String matricule;
    private String prenom;
    private int mg;

    private int htrv;
    private double production;

    public Employe(String nom, String prenom)
    {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Employe(String nom, String matricule, String prenom) {
        this.nom = nom;
        this.matricule = matricule;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMat() {
        return matricule;
    }

    public void setMat(String mat) {
        this.matricule = mat;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getMg() {
        return mg;
    }

    public void setMg(int mg) {
        this.mg = mg;
    }

    public int getHtrv() {
        return htrv;
    }

    public void setHtrv(int htrv) {
        this.htrv = htrv;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }
}
