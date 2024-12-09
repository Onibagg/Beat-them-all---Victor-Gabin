package com.example.ennemis;

import java.util.Random;

/**
 * La classe Brigants représente un ennemi de type brigant.
 */
public class Brigants extends Ennemis {

    /**
     * Constructeur qui initialise un brigant avec un nom, des points de vie (PV) et une force aléatoires.
     *
     * @param nom Le nom du brigant.
     */
    public Brigants(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(100) + 1;
        this.force = rand.nextInt(40) + 1;
        this.arme = "Couteau";
    }

    /**
     * Constructeur qui initialise un brigant avec un nom, une force et des points de vie (PV) spécifiés.
     *
     * @param nom   Le nom du brigant.
     * @param force La force du brigant.
     * @param pv    Les points de vie (PV) du brigant.
     */
    public Brigants(String nom, int force, int pv) {
        super(nom);
        this.pv = pv;
        this.force = force;
        this.arme = "Couteau";
    }

    /**
     * Méthode qui définit le comportement spécifique du brigant.
     * Affiche un message indiquant que le brigant attaque normalement.
     */
    public void comportementSpecifique() {
        System.out.println("Brigant attaque normalement.");
    }
}