package com.example.gamelog;

import com.example.personnages.*;
import com.example.ennemis.*;
import com.example.LogInit;
import com.example.game.*;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class GameTests {

    public static void main(String[] args) {
        LogInit logInit = new LogInit();
        try {
            logInit.initializeLog("game_log.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //testResetCapaciteUtilisee(logInit); --> OK
        testGererRencontres(logInit);
        //testJouerNiveau(logInit);
        //testSelectionnerEnnemi(logInit);
        //testGererCoffre(logInit);

        logInit.closeLog();
    }

    public static void testResetCapaciteUtilisee(LogInit logInit) {
        Personnage personnage = new Personnage() {
            private boolean capaciteUtilisee = false;

            public String getNom() { return "Hero"; }
            public int getPV() { return 100; }
            public void setPV(int PV) {}
            public void utiliserCapaciteSpeciale() {}
            public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {}
            public void resetCapaciteUtilisee() { capaciteUtilisee = false; }
            public void utiliserCapacite() { capaciteUtilisee = true; }
            public boolean isCapaciteUtilisee() { return capaciteUtilisee; }
        };
        personnage.utiliserCapacite();
        personnage.resetCapaciteUtilisee();
        if (!personnage.isCapaciteUtilisee()) {
            System.out.println("testResetCapaciteUtilisee passed");
        } else {
            System.out.println("testResetCapaciteUtilisee failed");
        }
    }

    public static void testGererRencontres(LogInit logInit) {
        Personnage personnage = new Personnage() {
            private int pv = 100;

            public String getNom() { return "Hero"; }
            public int getPV() { return pv; }
            public void setPV(int PV) { this.pv = PV; }
            public void utiliserCapaciteSpeciale() {}
            public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {
                this.pv -= 10; // Simuler une perte de PV
                ennemi.setPV(ennemi.getPV() - 10); // Simuler des dégâts à l'ennemi
            }
            public void resetCapaciteUtilisee() {}
            public void utiliserCapacite() {}
            public boolean isCapaciteUtilisee() { return false; }
        };

        Ennemis ennemi = new Ennemis("Goblin") {
            private int pv = 50;
            public String getNom() { return "Goblin"; }
            public int getPV() { return pv; }
            public void setPV(int PV) { this.pv = PV; }
        };

        // Simuler une rencontre avec un ennemi
        Scanner scanner = new Scanner(System.in);
        Main.random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 20; // Forcer une rencontre avec un ennemi
            }
        };
        Main.gererRencontres(logInit, personnage, scanner);
        if (personnage.getPV() == 90 && ennemi.getPV() == 40) {
            System.out.println("testGererRencontres (ennemi) passed");
        } else {
            System.out.println("testGererRencontres (ennemi) failed");
        }
    }

    public static void testJouerNiveau(LogInit logInit) {
        Personnage personnage = new Personnage() {
            public String getNom() { return "Hero"; }
            public int getPV() { return 100; }
            public void setPV(int PV) {}
            public void utiliserCapaciteSpeciale() {}
            public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {}
            public void resetCapaciteUtilisee() {}
            public void utiliserCapacite() {}
            public boolean isCapaciteUtilisee() { return false; }
        };
        List<Carte> mesCartes = Arrays.asList(new Carte("Paris", "Tour de Paris", "Louvre", "Tour Eiffel", 5), new Carte("Rome", "Tour de Rome", "Colisée", "Vatican", 5));
        Scanner scanner = new Scanner(System.in);
        Main.jouerNiveau(scanner, logInit, personnage, mesCartes);
        System.out.println("testJouerNiveau executed");
    }

    public static void testSelectionnerEnnemi(LogInit logInit) {
        Ennemis ennemi = Main.selectionnerEnnemi();
        if (ennemi != null) {
            System.out.println("testSelectionnerEnnemi passed");
        } else {
            System.out.println("testSelectionnerEnnemi failed");
        }
    }

    public static void testGererCoffre(LogInit logInit) {
        Personnage personnage = new Personnage() {
            public String getNom() { return "Hero"; }
            public int getPV() { return 100; }
            public void setPV(int PV) {}
            public void utiliserCapaciteSpeciale() {}
            public void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner) {}
            public void resetCapaciteUtilisee() {}
            public void utiliserCapacite() {}
            public boolean isCapaciteUtilisee() { return false; }
        };
        Scanner scanner = new Scanner(System.in);

        System.setIn(new ByteArrayInputStream("o\n".getBytes()));
        Main.gererCoffre(logInit, personnage, scanner);
        System.out.println("testGererCoffre executed");
    }
}