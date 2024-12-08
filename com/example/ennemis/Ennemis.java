package com.example.ennemis;

import com.example.personnages.Personnage;

import java.util.ArrayList;
import java.util.List;

public class Ennemis {
    protected int pv;
    protected int force;
    protected int defense;
    protected String arme;
    protected String nom;

    public Ennemis(String nom) {
        this.nom = nom;
    }

    public static List<Ennemis> genererListeEnnemis() {
        List<Ennemis> ennemis = new ArrayList<>();
        ennemis.add(new Brigants("Brigant"));
        ennemis.add(new Catcheurs("Catcheur"));
        ennemis.add(new CRS("CRS"));
        ennemis.add(new Sniper("Sniper"));
        ennemis.add(new EnnemisSpeciaux("Ennemi Spécial"));
        return ennemis;
    }

    public String getNom() {
        return nom;
    }

    public int getPV() {
        return pv;
    }

    public void setPV(int pv) {
        this.pv = pv;
    }

    public int getForce() {
        return force;
    }

    public int getDefense() {
        return defense;
    }
}

