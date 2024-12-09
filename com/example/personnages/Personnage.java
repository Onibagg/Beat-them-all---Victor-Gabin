package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Random;
import java.util.Scanner;

/**
 * Interface représentant un personnage dans le jeu.
 */
public interface Personnage {

    /**
     * Renvoie le nom du personnage.
     *
     * @return le nom du personnage
     */
    String getNom();

    /**
     * Renvoie les points de vie (PV) du personnage.
     *
     * @return les points de vie du personnage
     */
    int getPV();

    /**
     * Renvoie la force d'attaque du personnage.
     *
     * @return la force d'attaque du personnage
     */
    int getForce();

    /**
     * Définit les points de vie (PV) du personnage.
     *
     * @param PV les nouveaux points de vie du personnage
     */
    void setPV(int PV);

    /**
     * Renvoie la valeur d'esquive du personnage.
     *
     * @return la valeur d'esquive du personnage
     */
    int getEsquive();

    /**
     * Définit la valeur d'esquive du personnage.
     *
     * @param esquive la nouvelle valeur d'esquive
     */
    void setEsquive(int esquive);

    /**
     * Utilise la capacité spéciale du personnage.
     */
    void utiliserCapaciteSpeciale();

    /**
     * Réinitialise l'état d'utilisation de la capacité spéciale du personnage.
     */
    void resetCapaciteUtilisee();

    /**
     * Vérifie si la capacité spéciale a été utilisée.
     *
     * @return true si la capacité spéciale a été utilisée, false sinon
     */
    boolean isCapaciteUtilisee();

    /**
     * Utilise la capacité spéciale du personnage sur un ennemi.
     *
     * @param ennemi le personnage ennemi
     * @param logInit l'objet de gestion des journaux
     */
    void utiliserCapacite(Ennemis ennemi, LogInit logInit);

    /**
     * Défend le personnage contre une attaque.
     */
    default void defendre() {
        System.out.println(getNom() + " se défend.");
    }

    /**
     * Attaque un ennemi avec un nombre aléatoire de coups.
     *
     * @param ennemi le personnage ennemi
     * @param randomNumber le nombre aléatoire de coups
     * @param logInit l'objet de gestion des journaux
     */
    default void attaquer(Ennemis ennemi, int randomNumber, LogInit logInit) {
        int degats = randomNumber * this.getForce();
        int nouveauxPV = ennemi.getPV() - degats;
        ennemi.setPV(Math.max(nouveauxPV, 0)); // S'assurer que les PV ne deviennent pas négatifs
        logInit.logMaker(this.getNom() + " attaque " + randomNumber + " fois et inflige " + degats + " dégâts à " + ennemi.getNom());
    }

    /**
     * Gère l'utilisation de la capacité spéciale du personnage.
     *
     * @param ennemi le personnage ennemi
     * @param logInit l'objet de gestion des journaux
     */
    default void gererCapaciteSpeciale(Ennemis ennemi, LogInit logInit) {
        if (!isCapaciteUtilisee()) {
            utiliserCapacite(ennemi, logInit);
            logInit.logMaker(getNom() + " utilise sa capacité spéciale !");
        } else {
            logInit.logMaker(getNom() + " a déjà utilisé sa capacité spéciale.");
        }
    }

    /**
     * Gère le combat entre le personnage et un ennemi.
     *
     * Cette méthode permet au joueur de choisir entre attaquer, se défendre ou utiliser
     * une capacité spéciale à chaque tour. Le combat continue jusqu'à ce que les points
     * de vie (PV) du personnage ou de l'ennemi atteignent zéro.
     *
     * @param ennemi le personnage ennemi
     * @param logInit l'objet de gestion des journaux
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     * @param random l'objet Random pour générer des valeurs aléatoires
     */
    default void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner, Random random) {
        while (this.getPV() > 0 && ennemi.getPV() > 0) {
            logInit.logMaker("Demande de choix d'action à attaque ou défense.");
            logInit.logMaker("Voulez-vous attaquer (a), vous défendre (d) ou utiliser votre capacité spéciale (c) ? ");

            String choix = scanner.next();

            boolean joueurDefend = false;
            if (choix.equalsIgnoreCase("d")) {
                this.defendre();
                logInit.logMaker(this.getNom() + " se défend.");
                joueurDefend = true;
            } else if (choix.equalsIgnoreCase("c")) {
                gererCapaciteSpeciale(ennemi, logInit);
                continue;
            } else if (!choix.equalsIgnoreCase("a")) {
                logInit.logMaker("Choix invalide. Veuillez entrer 'a' pour attaquer, 'd' pour vous défendre ou 'c' pour utiliser votre capacité spéciale.");
                continue;
            }

            boolean ennemiEsquive = random.nextInt(100) < 25;
            if (ennemiEsquive && choix.equalsIgnoreCase("a")) {
                logInit.logMaker(this.getNom() + " attaque " + ennemi.getNom() + " mais il l'esquive.");
            } else if (choix.equalsIgnoreCase("a") && !ennemiEsquive) {
                Random randomAttaque = new Random();
                int randomNumberAttaque = randomAttaque.nextInt(5) + 1; // Génère un nombre entre 1 et 5
                attaquer(ennemi, randomNumberAttaque, logInit);
            } else if (choix.equalsIgnoreCase("a") && ennemiEsquive) {
                logInit.logMaker(this.getNom() + " attaque mais " + ennemi.getNom() + " esquive l'attaque.");
            }

            if (ennemi.getPV() > 0) {
                if (!ennemiEsquive && !joueurDefend) {
                    ennemi.attaquer(this, logInit);
                } else if (!ennemiEsquive && joueurDefend || this.getEsquive() > 0) {
                    logInit.logMaker(ennemi.getNom() + " attaque mais " + this.getNom() + " se défend.");
                    this.setEsquive(this.getEsquive() - 1);
                }
            }

            System.out.println(this.getNom() + " PV: " + this.getPV());
            System.out.println(ennemi.getNom() + " PV: " + ennemi.getPV());
            ennemi.resetDefending();
        }

        if (this.getPV() <= 0) {
            logInit.logMaker(this.getNom() + " a été vaincu par " + ennemi.getNom());

        } else if (ennemi.getPV() <= 0) {
            logInit.logMaker(ennemi.getNom() + " a été vaincu par " + this.getNom());
        }
    }
}