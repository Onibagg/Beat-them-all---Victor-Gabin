package com.example.ennemis;

import java.util.Random;

public class Catcheurs extends Ennemis {
    public Catcheurs(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(150) + 1;
        this.force = rand.nextInt(70) + 1;
        this.defense = rand.nextInt(50) + 1;
        this.arme = "Poings";
    }

}
