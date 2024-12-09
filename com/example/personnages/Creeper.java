// com/example/personnages/Creeper.java
package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Scanner;

public class Creeper implements Personnage {
    int PV;
    int force;
    int defense;
    String nom;
    public boolean capaciteUtilisee;

    public Creeper(String nom) {
        this.nom = nom;
        this.PV = 150;
        this.force = 15;
        this.defense = 3;
        this.capaciteUtilisee = false;
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
        System.out.println(nom + " utilise sa capacité spéciale : Explosion !");
        // One-shot the enemy
        this.force = Integer.MAX_VALUE;
    }

    public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {
        logInit.logMaker(nom + " combat " + ennemi.getNom());
        System.out.println(nom + " combat " + ennemi.getNom());
        while (this.PV > 0 && ennemi.getPV() > 0) {
            if (!this.capaciteUtilisee) {
                System.out.print("Voulez-vous utiliser l'attaque spéciale (o/n) ? ");
                String choix = scanner.next();
                if (choix.equalsIgnoreCase("o")) {
                    this.utiliserCapaciteSpeciale();
                    this.capaciteUtilisee = true;
                    continue;
                }
            }
            int damageToEnnemi = Math.max(5, this.force - ennemi.getDefense());
            ennemi.setPV(ennemi.getPV() - damageToEnnemi);
            logInit.logMaker(nom + " inflige " + damageToEnnemi + " dégâts à " + ennemi.getNom());
            System.out.println(nom + " inflige " + damageToEnnemi + " dégâts à " + ennemi.getNom());

            int damageToPersonnage = Math.max(2, ennemi.getForce() - this.defense);
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
        return capaciteUtilisee;
    }

    @Override
    public void utiliserCapacite() {
        // Implementation of the special ability
    }
}