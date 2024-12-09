package com.example.gamelog;

import com.example.personnages.*;
import com.example.ennemis.*;
import com.example.LogInit;
import com.example.game.*;
import java.io.IOException;
import java.util.Scanner;

import static com.example.game.Main.random;

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
        //testReculerAPositionZero(logInit); --> OK
        //testPourcentageApparitionSniper(logInit); --> OK
        //testCalculDegats(logInit); --> OK
        //testAttackDamage(logInit); --> OK



        logInit.closeLog();
    }

    public static void testReculerAPositionZero(LogInit logInit) {
        Personnage personnage = new Creeper("TestCreeper");
        Scanner scanner = new Scanner(System.in);

        // Simulate the initial position at 0
        int position = 0;
        System.out.println("Position avant tentative de recul: " + position);
        logInit.logMaker("Position avant tentative de recul: " + position);
        // Try to move back when at position 0
        try {
            position--;
            if (position < 0) {
                position = 0; // Ensure position does not go below 0
            }
            System.out.println("Position après tentative de recul: " + position);
            logInit.logMaker("Position après tentative de recul: " + position);

            if (position == 0) {
                System.out.println("testReculerAPositionZero passed");
            } else {
                System.out.println("testReculerAPositionZero failed");
            }
        } catch (Exception e) {
            System.out.println("testReculerAPositionZero failed with exception: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }


    public static void testResetCapaciteUtilisee(LogInit logInit) {
        Personnage personnage = new Creeper("TestCreeper");

        // Simulate using the special ability
        personnage.utiliserCapaciteSpeciale();
        ((Creeper) personnage).capaciteUtilisee = true; // Manually set to true for testing

        // Reset the special ability usage
        personnage.resetCapaciteUtilisee();

        // Check if the special ability usage has been reset
        if (!personnage.isCapaciteUtilisee()) {
            System.out.println("testResetCapaciteUtilisee passed");
        } else {
            System.out.println("testResetCapaciteUtilisee failed");
        }
    }

    public static void testPourcentageApparitionSniper(LogInit logInit) {
        int totalTests = 1000;
        int sniperCount = 0;

        for (int i = 0; i < totalTests; i++) {
            Ennemis ennemi = Main.selectionnerEnnemi();
            if (ennemi.getNom().equals("Sniper")) {
                sniperCount++;
            }
        }

        double pourcentageSniper = (sniperCount / (double) totalTests) * 100;
        System.out.println("Pourcentage d'apparition du Sniper: " + pourcentageSniper + "%");
        if (pourcentageSniper >= 12 && pourcentageSniper <= 18) {
            System.out.println("testPourcentageApparitionSniper passed");
        } else {
            System.out.println("testPourcentageApparitionSniper failed");
        }
    }

    public static void testCalculDegats(LogInit logInit) {
        logInit.logMaker("Test de calcul des dégâts");
        Personnage personnage = new Creeper("TestCreeper");
        Ennemis ennemi = new Brigants("TestBrigant");
        Scanner scanner = new Scanner(System.in);

        // Initial PV of the character
        int initialPV = personnage.getPV();
        logInit.logMaker("PV initiaux du personnage: " + initialPV);

        // Simulate the combat
        int totalDegats = 0;
        for (int i = 0; i < 3; i++) { // Simulate 3 attacks
            ennemi.attaquer(personnage, logInit);
            totalDegats += ennemi.getForce();
        }

        // Calculate expected PV after the attacks
        int expectedPV = initialPV - totalDegats;
        int actualPV = personnage.getPV();
        logInit.logMaker("PV attendus après les attaques: " + expectedPV);
        logInit.logMaker("PV réels après les attaques: " + actualPV);

        // Verify if the actual PV matches the expected PV
        if (actualPV == expectedPV) {
            System.out.println("testCalculDegats passed ✅");
        } else {
            System.out.println("testCalculDegats failed ❌");
        }

        scanner.close();
    }

    public static void testAttackDamage(LogInit logInit) {
        Creeper creeper = new Creeper("TestCreeper", 100, 100);
        Brigants brigant = new Brigants("TestBrigant", 100, 100);

        System.out.println("PV de l'ennemi avant l'attaque: " + brigant.getPV());

        creeper.attaquer( brigant, 1,logInit);

        System.out.println("PV de l'ennemi après l'attaque: " + brigant.getPV());

        if (brigant.getPV() == 0) {
            System.out.println("testAttackDamage passed");
        } else {
            System.out.println("testAttackDamage failed");
        }
    }

}