package com.example.personnages;

import com.example.LogInit;
import com.example.ennemis.Ennemis;

/**
 * The Hitman class represents a character with special abilities in the game.
 */
public class Hitman implements Personnage {
    int PV;
    int force;
    int defense;
    String nom;
    public boolean capaciteUtilisee;
    int esquive = 0;

    /**
     * Constructs a new Hitman with the specified name.
     *
     * @param nom the name of the Hitman
     */
    public Hitman(String nom) {
        this.nom = nom;
        this.PV = 200;
        this.force = 10;
        this.capaciteUtilisee = false;
    }

    /**
     * Returns the name of the Hitman.
     *
     * @return the name of the Hitman
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Returns the dodge value of the Hitman.
     *
     * @return the dodge value of the Hitman
     */
    public int getEsquive() {
        return esquive;
    }

    /**
     * Sets the dodge value of the Hitman.
     *
     * @param esquive the new dodge value
     */
    public void setEsquive(int esquive) {
        this.esquive = esquive;
    }

    /**
     * Returns the health points (PV) of the Hitman.
     *
     * @return the health points of the Hitman
     */
    @Override
    public int getPV() {
        return PV;
    }

    /**
     * Sets the health points (PV) of the Hitman.
     *
     * @param PV the new health points
     */
    @Override
    public void setPV(int PV) {
        this.PV = PV;
    }

    /**
     * Uses the special ability of the Hitman, which increases defense to maximum.
     */
    @Override
    public void utiliserCapaciteSpeciale() {
        System.out.println(nom + " utilise sa capacité spéciale : Esquive !");
        this.defense = Integer.MAX_VALUE;
    }

    /**
     * Returns the attack force of the Hitman.
     *
     * @return the attack force of the Hitman
     */
    public int getForce() {
        return force;
    }

    /**
     * Resets the special ability usage status of the Hitman.
     */
    @Override
    public void resetCapaciteUtilisee() {
        this.capaciteUtilisee = false;
    }

    /**
     * Checks if the special ability has been used.
     *
     * @return true if the special ability has been used, false otherwise
     */
    @Override
    public boolean isCapaciteUtilisee() {
        return capaciteUtilisee;
    }

    /**
     * Uses the special ability of the Hitman, which increases dodge for a limited time.
     *
     * @param ennemi the enemy character
     * @param logInit the log initialization object
     */
    @Override
    public void utiliserCapacite(Ennemis ennemi, LogInit logInit) {
        if (!capaciteUtilisee) {
            logInit.logMaker(nom + " utilise sa capacité spéciale : Esquive !");
            this.esquive = 2;
            this.capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }
    }
}