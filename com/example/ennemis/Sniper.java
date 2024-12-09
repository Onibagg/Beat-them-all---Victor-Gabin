package com.example.ennemis;

import java.util.Random;

/**
 * La classe Sniper représente un ennemi de type sniper.
 */
public class Sniper extends Ennemis {

    /**
     * Constructeur qui initialise un sniper avec un nom, des points de vie (PV) et une force aléatoires.
     *
     * @param nom Le nom du sniper.
     */
    public Sniper(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(80) + 1;
        this.force = rand.nextInt(100) + 1;
        this.arme = "Fusil de précision";
    }
}