package com.example.ennemis;

import com.example.LogInit;
import com.example.personnages.Personnage;

public class Ennemis {
    protected int pv;
    protected String nom;
    protected int force;
    protected int defense;
    protected String arme;
    private boolean defending;

    public Ennemis(String nom) {
        this.nom = nom;
    }
    public Ennemis(String nom, int force, int pv) {
        this.nom = nom;
        this.force = force;
        this.pv= pv;
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

    public boolean isDefending() {
        return defending;
    }

    public void resetDefending() {
        this.defending = false;
    }

    public void attaquer(Personnage personnage, LogInit logInit) {
        int degats = this.getForce();
        int nouveauxPV = personnage.getPV() - degats;
        personnage.setPV(Math.max(nouveauxPV, 0));
        System.out.println(this.getNom() + " inflige " + degats + " dégâts à " + personnage.getNom());
        logInit.logMaker(this.getNom() + " inflige " + degats + " dégâts à " + personnage.getNom());
    }
}