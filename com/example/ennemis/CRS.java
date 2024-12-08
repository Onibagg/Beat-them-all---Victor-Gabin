package com.example.ennemis;

import java.util.Random;

public class CRS extends Ennemis {
    public CRS(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(120) + 1;
        this.force = rand.nextInt(60) + 1;
        this.defense = rand.nextInt(40) + 1;
        this.arme = "Matraque";
    }

    public void comportementSpecifique() {
        System.out.println("CRS utilise un bouclier et tire des lacrymos.");
    }
}
