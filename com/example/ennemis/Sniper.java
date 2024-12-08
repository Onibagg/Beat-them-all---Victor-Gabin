package com.example.ennemis;

import java.util.Random;

public class Sniper extends Ennemis {
    public Sniper(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(80) + 1;
        this.force = rand.nextInt(100) + 1;
        this.defense = rand.nextInt(20) + 1;
        this.arme = "Fusil de précision";
    }

    public void comportementSpecifique() {
        System.out.println("Sniper tire de très loin.");
    }
}
