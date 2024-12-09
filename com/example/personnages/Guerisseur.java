package com.example.personnages;

import com.example.LogInit;
import com.example.ennemis.Ennemis;

/**
 * La classe Guerisseur représente un personnage soigneur avec des capacités spéciales dans le jeu.
 */
public class Guerisseur implements Personnage {
    int PV;
    int force;
    int defense;
    String nom;
    public boolean capaciteUtilisee;

    /**
     * Construit un nouveau Guerisseur avec le nom spécifié.
     *
     * @param nom le nom du Guerisseur
     */
    public Guerisseur(String nom) {
        this.nom = nom;
        this.PV = 300;
        this.force = 5;
        this.capaciteUtilisee = false;
    }

    /**
     * Renvoie le nom du Guerisseur.
     *
     * @return le nom du Guerisseur
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie les points de vie (PV) du Guerisseur.
     *
     * @return les points de vie du Guerisseur
     */
    @Override
    public int getPV() {
        return PV;
    }

    /**
     * Définit les points de vie (PV) du Guerisseur.
     *
     * @param PV les nouveaux points de vie
     */
    @Override
    public void setPV(int PV) {
        this.PV = PV;
    }

    /**
     * Renvoie la valeur d'esquive du Guerisseur.
     *
     * @return la valeur d'esquive du Guerisseur
     */
    @Override
    public int getEsquive() {
        return 0;
    }

    /**
     * Définit la valeur d'esquive du Guerisseur.
     *
     * @param esquive la nouvelle valeur d'esquive
     */
    @Override
    public void setEsquive(int esquive) {
        // Pas d'implémentation nécessaire pour le Guerisseur
    }

    /**
     * Utilise la capacité spéciale du Guerisseur, qui restaure les points de vie au maximum.
     */
    @Override
    public void utiliserCapaciteSpeciale() {
        System.out.println(nom + " utilise sa capacité spéciale : Soin !");
        this.PV = 100;
    }

    /**
     * Renvoie la force d'attaque du Guerisseur.
     *
     * @return la force d'attaque du Guerisseur
     */
    public int getForce() {
        return force;
    }

    /**
     * Réinitialise l'état d'utilisation de la capacité spéciale du Guerisseur.
     */
    @Override
    public void resetCapaciteUtilisee() {
        this.capaciteUtilisee = false;
    }

    /**
     * Vérifie si la capacité spéciale a été utilisée.
     *
     * @return true si la capacité spéciale a été utilisée, false sinon
     */
    @Override
    public boolean isCapaciteUtilisee() {
        return capaciteUtilisee;
    }

    /**
     * Utilise la capacité spéciale du Guerisseur, qui restaure les points de vie au maximum.
     *
     * @param ennemi le personnage ennemi
     * @param logInit l'objet de gestion des journaux
     */
    @Override
    public void utiliserCapacite(Ennemis ennemi, LogInit logInit) {
        if (!capaciteUtilisee) {
            logInit.logMaker(nom + " utilise sa capacité spéciale : Soin !");
            this.PV = 100;
            this.capaciteUtilisee = true;
        } else {
            System.out.println(nom + " a déjà utilisé sa capacité spéciale.");
        }
    }
}
