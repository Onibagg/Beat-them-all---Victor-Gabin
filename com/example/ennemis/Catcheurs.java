package com.example.ennemis;

import java.util.Random;

/**
 * La classe Catcheurs représente un ennemi de type catcheur.
 */
public class Catcheurs extends Ennemis {

    /**
     * Constructeur qui initialise un catcheur avec un nom, des points de vie (PV), une force et une défense aléatoires.
     *
     * @param nom Le nom du catcheur.
     */
    public Catcheurs(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(150) + 1;
        this.force = rand.nextInt(60) + 1;
        this.arme = "Poings";
    }
}