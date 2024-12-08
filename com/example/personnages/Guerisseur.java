package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Scanner;

public class Guerisseur implements Personnage {
    int PV;
    int force;
    int defense;
    String nom;
    String arme;
    public boolean capaciteUtilisee;

    public Guerisseur(String nom) {
        this.nom = nom;
        this.PV = 300; // Example initial value
        this.force = 10; // Example initial value
        this.defense = 5; // Example initial value
        this.capaciteUtilisee = false; // Initialize to false at the beginning of each level
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getPV() {
        return PV;
    }

    @Override
    public void setPV(int PV) {
        this.PV = PV;
    }

    @Override
    public void utiliserCapaciteSpeciale() {
        System.out.println("Capacité spéciale non définie pour " + nom);
    }

    public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {
        logInit.logMaker(nom + " combat " + ennemi.getNom());
        System.out.println(nom + " combat " + ennemi.getNom());
        // Simple combat logic
        while (this.PV > 0 && ennemi.getPV() > 0) {
            // Ask if the user wants to use the special attack
            if (!this.capaciteUtilisee) {
                System.out.print("Voulez-vous utiliser l'attaque spéciale (o/n) ? ");
                String choix = scanner.next();
                if (choix.equalsIgnoreCase("o")) {
                    this.utiliserCapaciteSpeciale();
                    this.capaciteUtilisee = true;
                    continue; // Skip the normal attack
                }
            }

            // Personnage attacks
            int damageToEnnemi = Math.max(5, this.force - ennemi.getDefense());
            ennemi.setPV(ennemi.getPV() - damageToEnnemi);
            logInit.logMaker(nom + " inflige " + damageToEnnemi + " dégâts à " + ennemi.getNom());
            System.out.println(nom + " inflige " + damageToEnnemi + " dégâts à " + ennemi.getNom());

            // Ennemi attacks
            int damageToPersonnage = Math.max(2, ennemi.getForce() - this.defense); // Ensure at least 1 damage
            this.PV -= damageToPersonnage;
            logInit.logMaker(ennemi.getNom() + " inflige " + damageToPersonnage + " dégâts à " + nom);
            System.out.println(ennemi.getNom() + " inflige " + damageToPersonnage + " dégâts à " + nom);
        }

        if (this.PV <= 0) {
            logInit.logMaker(nom + " a été vaincu par " + ennemi.getNom());
            System.out.println(nom + " a été vaincu par " + ennemi.getNom());
        } else {
            logInit.logMaker(nom + " a vaincu " + ennemi.getNom());
            System.out.println(nom + " a vaincu " + ennemi.getNom());
        }
    }
    @Override
    public void resetCapaciteUtilisee() {
        this.capaciteUtilisee = false;
    }

    @Override
    public boolean isCapaciteUtilisee() {
        return false;
    }

    @Override
    public void utiliserCapacite() {

    }
}