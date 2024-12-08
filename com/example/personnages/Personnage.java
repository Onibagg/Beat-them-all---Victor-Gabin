package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Random;
import java.util.Scanner;

public interface Personnage {
    String getNom();
    int getPV();
    void setPV(int PV);
    void utiliserCapaciteSpeciale();
    void resetCapaciteUtilisee();
    boolean isCapaciteUtilisee();
    void utiliserCapacite();

    default void defendre() {
        System.out.println(getNom() + " se défend.");
    }

    default void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner, Random random) {
        while (this.getPV() > 0 && ennemi.getPV() > 0) {
            System.out.print("Voulez-vous attaquer (a) ou vous défendre (d) ? ");
            String choix = scanner.next();

            boolean joueurDefend = false;
            if (choix.equalsIgnoreCase("a")) {
                if (!ennemi.isDefending()) {
                    int degats = random.nextInt(20) + 1; // Random damage between 1 and 20
                    ennemi.setPV(ennemi.getPV() - degats);
                    logInit.logMaker(this.getNom() + " inflige " + degats + " dégâts à " + ennemi.getNom());
                    System.out.println(this.getNom() + " inflige " + degats + " dégâts à " + ennemi.getNom());
                } else {
                    logInit.logMaker(this.getNom() + " attaque mais " + ennemi.getNom() + " se défend et ne prend aucun dégât.");
                    System.out.println(this.getNom() + " attaque mais " + ennemi.getNom() + " se défend et ne prend aucun dégât.");
                }
            } else if (choix.equalsIgnoreCase("d")) {
                this.defendre();
                logInit.logMaker(this.getNom() + " se défend.");
                joueurDefend = true;
            } else {
                System.out.println("Choix invalide. Veuillez entrer 'a' pour attaquer ou 'd' pour vous défendre.");
                continue;
            }

            if (ennemi.getPV() > 0) {
                if (random.nextBoolean()) {
                    if (!joueurDefend) {
                        int degats = random.nextInt(20) + 1; // Random damage between 1 and 20
                        this.setPV(this.getPV() - degats);
                        logInit.logMaker(ennemi.getNom() + " inflige " + degats + " dégâts à " + this.getNom());
                        System.out.println(ennemi.getNom() + " inflige " + degats + " dégâts à " + this.getNom());
                    } else {
                        logInit.logMaker(ennemi.getNom() + " attaque mais " + this.getNom() + " se défend et ne prend aucun dégât.");
                        System.out.println(ennemi.getNom() + " attaque mais " + this.getNom() + " se défend et ne prend aucun dégât.");
                    }
                } else {
                    ennemi.defendre();
                    logInit.logMaker(ennemi.getNom() + " se défend.");
                }
            }

            // Afficher les PV après chaque coup
            System.out.println(this.getNom() + " PV: " + this.getPV());
            System.out.println(ennemi.getNom() + " PV: " + ennemi.getPV());

            // Réinitialiser l'état de défense de l'ennemi après chaque tour
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