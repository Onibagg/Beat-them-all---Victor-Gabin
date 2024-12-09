package com.example.gamelog;

import com.example.personnages.*;
import com.example.ennemis.*;
import com.example.LogInit;
import com.example.game.*;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * La classe GameTests contient des méthodes de test pour vérifier le bon fonctionnement du jeu.
 */
public class GameTests {

    /**
     * Méthode principale pour exécuter les tests.
     *
     * @param args Les arguments de la ligne de commande.
     */
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
        //testPersonnageDiesBeforeFinishLine(logInit); --> OK

        logInit.closeLog();
    }

    /**
     * Teste si la position ne peut pas être inférieure à zéro.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
    public static void testReculerAPositionZero(LogInit logInit) {
        Personnage personnage = new Creeper("TestCreeper");
        Scanner scanner = new Scanner(System.in);

        // Simule la position initiale à 0
        int position = 0;
        System.out.println("Position avant tentative de recul: " + position);
        logInit.logMaker("Position avant tentative de recul: " + position);
        // Tente de reculer lorsque la position est à 0
        try {
            position--;
            if (position < 0) {
                position = 0; // Assure que la position ne descend pas en dessous de 0
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


    /**
     * Teste si la capacité spéciale du personnage est correctement réinitialisée.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
    public static void testResetCapaciteUtilisee(LogInit logInit) {
        Personnage personnage = new Creeper("TestCreeper");

        // Simule l'utilisation de la capacité spéciale
        personnage.utiliserCapaciteSpeciale();
        ((Creeper) personnage).capaciteUtilisee = true; // Défini manuellement à true pour le test

        // Réinitialise l'utilisation de la capacité spéciale
        personnage.resetCapaciteUtilisee();

        // Vérifie si l'utilisation de la capacité spéciale a été réinitialisée
        if (!personnage.isCapaciteUtilisee()) {
            System.out.println("testResetCapaciteUtilisee passed");
        } else {
            System.out.println("testResetCapaciteUtilisee failed");
        }
    }

    /**
     * Teste le pourcentage d'apparition du Sniper parmi les ennemis.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
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

    /**
     * Teste le calcul des dégâts infligés par un ennemi à un personnage.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
    public static void testCalculDegats(LogInit logInit) {
        logInit.logMaker("Test de calcul des dégâts");
        Personnage personnage = new Creeper("TestCreeper");
        Ennemis ennemi = new Brigants("TestBrigant");
        Scanner scanner = new Scanner(System.in);

        // PV initiaux du personnage
        int initialPV = personnage.getPV();
        logInit.logMaker("PV initiaux du personnage: " + initialPV);

        // Simule le combat
        int totalDegats = 0;
        for (int i = 0; i < 3; i++) { // Simule 3 attaques
            ennemi.attaquer(personnage, logInit);
            totalDegats += ennemi.getForce();
        }

        // Calcule les PV attendus après les attaques
        int expectedPV = initialPV - totalDegats;
        int actualPV = personnage.getPV();
        logInit.logMaker("PV attendus après les attaques: " + expectedPV);
        logInit.logMaker("PV réels après les attaques: " + actualPV);

        // Vérifie si les PV réels correspondent aux PV attendus
        if (actualPV == expectedPV) {
            System.out.println("testCalculDegats passed ✅");
        } else {
            System.out.println("testCalculDegats failed ❌");
        }

        scanner.close();
    }

    /**
     * Teste les dégâts d'attaque infligés par un Creeper à un Brigant.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
    public static void testAttackDamage(LogInit logInit) {
        Creeper creeper = new Creeper("TestCreeper", 100, 100);
        Brigants brigant = new Brigants("TestBrigant", 100, 100);

        System.out.println("PV de l'ennemi avant l'attaque: " + brigant.getPV());

        creeper.attaquer(brigant, 1, logInit);

        System.out.println("PV de l'ennemi après l'attaque: " + brigant.getPV());

        if (brigant.getPV() == 0) {
            System.out.println("testAttackDamage passed");
        } else {
            System.out.println("testAttackDamage failed");
        }
    }

    /**
     * Teste si le jeu se termine correctement lorsque le personnage meurt avant la ligne d'arrivée.
     *
     * @param logInit L'instance de LogInit pour enregistrer les logs.
     */
    public static void testPersonnageDiesBeforeFinishLine(LogInit logInit) {
        try {
            logInit.initializeLog("game_log.txt");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to initialize log");
        }

        // Réinitialise le drapeau
        Main.finirJeuCalled = false;

        // Crée un héros avec des PV bas pour simuler la mort
        Creeper personnage = new Creeper("Testpersonnage", 10, 100);
        Brigants ennemi = new Brigants("Testennemi", 100, 100);

        // Simule le combat jusqu'à ce que le héros meure
        while (personnage.getPV() > 0) {
            ennemi.attaquer(personnage, logInit);
        }
        Main.gererRencontres(logInit, personnage, new Scanner(System.in));

        assertTrue(Main.finirJeuCalled, "Le jeu ne se termine pas correctement lorsque le personnage meurt");
        logInit.closeLog();
    }
}