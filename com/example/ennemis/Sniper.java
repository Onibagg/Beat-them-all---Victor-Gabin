package com.example.ennemis;

import java.util.Random;

public class Sniper extends Ennemis {
    public Sniper(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(80) + 1;
        this.force = rand.nextInt(100) + 1;
        this.arme = "Fusil de précision";
    }

}
