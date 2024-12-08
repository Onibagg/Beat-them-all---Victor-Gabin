package com.example.personnages;

public class Hitman extends Personnage {

    public Hitman(String nom) {
        super(nom);
        this.PV = 200;
        this.force = 60;
        this.defense = 25;
        this.arme = "Sniper";
        this.capaciteUtilisee = false;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(nom + " utilise sa capacité spéciale: Silent Kill!");
            capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }
    }
}