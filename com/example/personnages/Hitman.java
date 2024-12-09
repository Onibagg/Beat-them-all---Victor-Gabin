package com.example.personnages;

public class Hitman implements Personnage {
    int PV;
    int force;
    int defense;
    String nom;
    public boolean capaciteUtilisee;

    public Hitman(String nom) {
        this.nom = nom;
        this.PV = 200;
        this.force = 10;
        this.capaciteUtilisee = false;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getPV() {
        return PV;
    }

    @Override
    public void setPV(int PV) {
        this.PV = PV;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        System.out.println(nom + " utilise sa capacité spéciale : Tir précis !");
        // One-shot the enemy
        this.force = Integer.MAX_VALUE;
    }


    public int getForce() {
        return force;
    }

    @Override
    public void resetCapaciteUtilisee() {
        this.capaciteUtilisee = false;
    }

    @Override
    public boolean isCapaciteUtilisee() {
        return capaciteUtilisee;
    }

    @Override
    public void utiliserCapacite() {
        // Implementation of the special ability
    }
}