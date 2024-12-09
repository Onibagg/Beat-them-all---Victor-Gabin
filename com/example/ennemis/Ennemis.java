package com.example.ennemis;

import com.example.LogInit;
import com.example.personnages.Personnage;

/**
 * La classe Ennemis représente un ennemi générique avec des attributs communs.
 */
public class Ennemis {
    protected int pv;
    protected String nom;
    protected int force;
    protected String arme;
    private boolean defending;

    /**
     * Constructeur qui initialise un ennemi avec un nom.
     *
     * @param nom Le nom de l'ennemi.
     */
    public Ennemis(String nom) {
        this.nom = nom;
    }

    /**
     * Constructeur qui initialise un ennemi avec un nom, une force et des points de vie (PV) spécifiés.
     *
     * @param nom   Le nom de l'ennemi.
     * @param force La force de l'ennemi.
     * @param pv    Les points de vie (PV) de l'ennemi.
     */
    public Ennemis(String nom, int force, int pv) {
        this.nom = nom;
        this.force = force;
        this.pv = pv;
    }

    /**
     * Retourne le nom de l'ennemi.
     *
     * @return Le nom de l'ennemi.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne les points de vie (PV) de l'ennemi.
     *
     * @return Les points de vie (PV) de l'ennemi.
     */
    public int getPV() {
        return pv;
    }

    /**
     * Définit les points de vie (PV) de l'ennemi.
     *
     * @param pv Les nouveaux points de vie (PV) de l'ennemi.
     */
    public void setPV(int pv) {
        this.pv = pv;
    }

    /**
     * Retourne la force de l'ennemi.
     *
     * @return La force de l'ennemi.
     */
    public int getForce() {
        return force;
    }

    /**
     * Indique si l'ennemi est en train de se défendre.
     *
     * @return true si l'ennemi se défend, false sinon.
     */
    public boolean isDefending() {
        return defending;
    }

    /**
     * Réinitialise l'état de défense de l'ennemi.
     */
    public void resetDefending() {
        this.defending = false;
    }

    /**
     * Méthode qui permet à l'ennemi d'attaquer un personnage.
     *
     * @param personnage Le personnage attaqué.
     * @param logInit    L'instance de LogInit pour enregistrer l'attaque.
     */
    public void attaquer(Personnage personnage, LogInit logInit) {
        int degats = this.getForce();
        int nouveauxPV = personnage.getPV() - degats;
        personnage.setPV(Math.max(nouveauxPV, 0));
        logInit.logMaker(this.getNom() + " inflige " + degats + " dégâts à " + personnage.getNom());
    }
}