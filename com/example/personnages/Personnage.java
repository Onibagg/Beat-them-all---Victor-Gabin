package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Random;
import java.util.Scanner;

public interface Personnage {
    String getNom();
    int getPV();
    int getForce();
    void setPV(int PV);
    void utiliserCapaciteSpeciale();
    void resetCapaciteUtilisee();
    boolean isCapaciteUtilisee();
    void utiliserCapacite();

    default void defendre() {
        System.out.println(getNom() + " se défend.");
    }

    default void attaquer(Ennemis ennemi, int randomNumber, LogInit logInit) {
        int degats = randomNumber * this.getForce();
        int nouveauxPV = ennemi.getPV() - degats;

        ennemi.setPV(Math.max(nouveauxPV, 0)); // S'assurer que les PV ne deviennent pas négatifs
        System.out.println(this.getNom() + " attaque " + randomNumber + " fois et inflige " + degats + " dégâts à " + ennemi.getNom());
        logInit.logMaker(this.getNom() + " attaque " + randomNumber + " fois et inflige " + degats + " dégâts à " + ennemi.getNom());
    }

    default void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner, Random random) {
        while (this.getPV() > 0 && ennemi.getPV() > 0) {
            logInit.logMaker("Demande de choix d'action à attaque ou défense.");
            System.out.print("Voulez-vous attaquer (a) ou vous défendre (d) ? ");

            String choix = scanner.next();

            boolean joueurDefend = false;
            if (choix.equalsIgnoreCase("d")) {
                this.defendre();
                logInit.logMaker(this.getNom() + " se défend.");
                joueurDefend = true;
            } else if (!choix.equalsIgnoreCase("a")) {
                System.out.println("Choix invalide. Veuillez entrer 'a' pour attaquer ou 'd' pour vous défendre.");
                continue;
            }

            boolean ennemiEsquive = random.nextBoolean();
            if (ennemiEsquive && choix.equalsIgnoreCase("a")) {
                logInit.logMaker(this.getNom() + " attaque " + ennemi.getNom() + " mais il l'esquive.");
                System.out.println(this.getNom() + " attaque " + ennemi.getNom() + " mais il l'esquive.");
            } else if (choix.equalsIgnoreCase("a") && !ennemiEsquive) {
                Random randomAttaque = new Random();
                int randomNumberAttaque = randomAttaque.nextInt(5) + 1; // Generates a number between 1 and 5
                attaquer(ennemi, randomNumberAttaque, logInit);
            } else if (choix.equalsIgnoreCase("a") && ennemiEsquive) {
                logInit.logMaker(this.getNom() + " attaque mais " + ennemi.getNom() + " esquive l'attaque.");
                System.out.println(this.getNom() + " attaque mais " + ennemi.getNom() + " esquive l'attaque.");

            }

            if (ennemi.getPV() > 0) {
                if (!ennemiEsquive && !joueurDefend) {
                    ennemi.attaquer(this, logInit);
                } else if (!ennemiEsquive && joueurDefend) {
                    logInit.logMaker(ennemi.getNom() + " attaque mais " + this.getNom() + " se défend.");
                    System.out.println(ennemi.getNom() + " attaque mais " + this.getNom() + " se défend.");
                }
            }

            System.out.println(this.getNom() + " PV: " + this.getPV());
            System.out.println(ennemi.getNom() + " PV: " + ennemi.getPV());
            ennemi.resetDefending();
        }

        if (this.getPV() <= 0) {
            logInit.logMaker(this.getNom() + " a été vaincu par " + ennemi.getNom());
            System.out.println(this.getNom() + " a été vaincu par " + ennemi.getNom());
        } else if (ennemi.getPV() <= 0) {
            logInit.logMaker(ennemi.getNom() + " a été vaincu par " + this.getNom());
            System.out.println(ennemi.getNom() + " a été vaincu par " + this.getNom());
        }
    }
}