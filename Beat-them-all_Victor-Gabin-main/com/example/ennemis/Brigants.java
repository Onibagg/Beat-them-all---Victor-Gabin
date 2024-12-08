package com.example.ennemis;

import java.util.Random;

public class Brigants extends Ennemis {
    public Brigants(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(100) + 1;
        this.force = rand.nextInt(50) + 1;
        this.defense = rand.nextInt(30) + 1;
        this.arme = "Couteau";
    }

    public void comportementSpecifique() {
        System.out.println("Brigant attaque normalement.");
    }
}
