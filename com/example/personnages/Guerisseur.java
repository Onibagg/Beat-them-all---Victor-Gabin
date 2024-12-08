package com.example.personnages;

public class Guerisseur extends Personnage {

    public Guerisseur(String nom) {
        super(nom);
        this.PV = 150;
        this.force = 30;
        this.defense = 40;
        this.arme = "Staff";
        this.capaciteUtilisee = false;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(nom + " utilise sa capacité spéciale: Healing!");
            capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }
    }
}