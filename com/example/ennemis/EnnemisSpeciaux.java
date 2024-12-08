package com.example.ennemis;

import java.util.Random;

public class EnnemisSpeciaux extends Ennemis {
    public EnnemisSpeciaux(String nom) {
        super(nom);
        Random rand = new Random();
        this.pv = rand.nextInt(200) + 1;
        this.force = rand.nextInt(90) + 1;
        this.defense = rand.nextInt(60) + 1;
        this.arme = "Arme spéciale";
    }

    public void comportementSpecifique() {
        if (this.nom.equals("Trump")) {
            System.out.println("Trump pose un mur devant lui et danse.");
        } else if (this.nom.equals("Spider-Man")) {
            System.out.println("Spider-Man esquive les balles, seul le corps à corps fonctionne.");
        }
    }
}
