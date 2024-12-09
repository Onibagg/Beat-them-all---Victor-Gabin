package com.example.ennemis;

import java.util.Random;

/**
 * La classe CRS représente un ennemi de type CRS.
 */
public class CRS extends Ennemis {

    /**
     * Constructeur qui initialise un CRS avec un nom, des points de vie (PV) et une force aléatoires.
     *
     * @param nom Le nom du CRS.
     */
    public CRS(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(120) + 1;
        this.force = rand.nextInt(60) + 1;
        this.arme = "Matraque";
    }

    /**
     * Méthode qui définit le comportement spécifique du CRS.
     * Affiche un message indiquant que le CRS utilise un bouclier et tire des lacrymos.
     */
    public void comportementSpecifique() {
        System.out.println("CRS utilise un bouclier et tire des lacrymos.");
    }
}