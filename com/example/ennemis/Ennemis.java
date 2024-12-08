package com.example.ennemis;

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

    public String getNom() {
        return nom;
    }

    public int getPV() {
        return pv;
    }

    public void setPV(int pv) {
        this.pv = pv;
    }

    public int getDefense() {
        return defense;
    }

    public int getForce() {
        return force;
    }

    public boolean isDefending() {
        return defending;
    }

    public void defendre() {
        System.out.println(getNom() + " se défend.");
        this.defending = true;
    }

    public void resetDefending() {
        this.defending = false;
    }
}