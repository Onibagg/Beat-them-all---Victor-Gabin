package com.example.gamelog;

import com.example.personnages.*;
import com.example.ennemis.*;
import com.example.LogInit;
import com.example.game.*;
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
        //testGererRencontres(logInit);
        //testJouerNiveau(logInit);
        //testPourcentageApparitionSniper(logInit); --> OK
        //testGererCoffre(logInit);

        logInit.closeLog();
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
}