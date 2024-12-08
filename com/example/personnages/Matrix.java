package com.example.personnages;

public class Matrix extends Personnage {

    public Matrix(String nom) {
        super(nom);
        this.PV = 220;
        this.force = 50;
        this.defense = 30;
        this.arme = "Gun";
        this.capaciteUtilisee = false;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        if (!capaciteUtilisee) {
            System.out.println(nom + " utilise sa capacité spéciale: Bullet Time!");
            capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }
    }
}