package com.example.pc.gestiontissage.Models;

/**
 * Created by pc on 12/04/2018.
 */

public class Arret {

    private int code;
    private String nom;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Arret(int code, String nom) {
        this.code = code;
        this.nom = nom;
    }
}
