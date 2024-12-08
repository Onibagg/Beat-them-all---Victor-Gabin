package com.example.personnages;

public class Creeper extends Personnage {

    public Creeper(String nom) {
        super(nom);
        this.PV = 150;
        this.force = 40;
        this.defense = 20;
        this.arme = "Explosives";
        this.capaciteUtilisee = false;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(nom + " utilise sa capacité spéciale: Explosion!");
            capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }

    }
}