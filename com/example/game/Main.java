// Gabin Demé & Victor Gilet - FISA TI 27
package com.example.game;

import com.example.LogInit;
import com.example.ennemis.*;
import com.example.personnages.Creeper;
import com.example.personnages.Guerisseur;
import com.example.personnages.Hitman;
import com.example.personnages.Personnage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe principale du jeu "Beat them all".
 * Cette classe gère le flux principal du jeu, y compris l'initialisation, le choix du personnage,
 * le lancement des niveaux, et la gestion des rencontres et des combats.
 */
public class Main {
    public static Random random = new Random(); // Initialise le champ random
    public static boolean[] niveauxTermines = new boolean[10]; // Initialise le tableau avec une taille de 10
    public static boolean finirJeuCalled = false;

    /**
     * Point d'entrée principal du jeu.
     * Initialise les journaux, demande à l'utilisateur de choisir un personnage et lance le premier niveau.
     *
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        random = new Random();
        LogInit logInit = new LogInit();
        try {
            String timecode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            logInit.initializeLog("gamelog/game_" + timecode + ".log");
            Scanner scanner = new Scanner(System.in);
            logInit.logMaker("Game started");

            List<Carte> mesCartes = initialiserJeu(logInit);
            Personnage personnage = choisirPersonnage(scanner, logInit);

            if (!lancerNiveau(scanner, logInit, mesCartes)) {
                scanner.close();
                logInit.closeLog();
                return;
            }

            jouerNiveau(scanner, logInit, personnage, mesCartes); // Passe la liste complète des objets Carte

            scanner.close();
            logInit.closeLog();
        } catch (IOException e) {
            logInit.logMaker("Une erreur s'est produite.");
            e.printStackTrace();
        }
    }

    /**
     * Initialise le jeu en générant les cartes.
     *
     * @param logInit l'objet de gestion des journaux
     * @return la liste des cartes générées
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public static List<Carte> initialiserJeu(LogInit logInit) throws IOException {
        logInit.logMaker("Génération des cartes...");
        List<Carte> mesCartes = Carte.genererCartes();
        logInit.logMaker("Cartes générées");
        return mesCartes;
    }

    /**
     * Permet au joueur de choisir un personnage parmi les options disponibles.
     * Affiche les personnages disponibles et enregistre le choix du joueur.
     *
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     * @param logInit l'objet de gestion des journaux
     * @return le personnage choisi par le joueur
     */
    public static Personnage choisirPersonnage(Scanner scanner, LogInit logInit) {
        logInit.logMaker("Bienvenue dans le jeu Beat them all ! \n");
        logInit.logMaker("Affichage des personnages disponibles...");
        logInit.logMaker("Personnages disponibles :");
        logInit.logMaker("1. Hitman");
        logInit.logMaker("2. Creeper");
        logInit.logMaker("3. Guerisseur\n");
        logInit.logMaker("Demande de choix de personnage");
        logInit.logMaker("Choisissez votre personnage (1-3) : ");
        int choixPersonnage = scanner.nextInt();
        Personnage personnage;
        switch (choixPersonnage) {
            case 1:
                personnage = new Hitman("HITMAN");
                break;
            case 2:
                personnage = new Creeper("CREEPER");
                break;
            case 3:
                personnage = new Guerisseur("GUERISSEUR");
                break;
            default:
                logInit.logMaker("Choix invalide. Personnage par défaut: Hitman.");
                personnage = new Hitman("HITMAN");
                break;
        }
        logInit.logMaker("Personnage choisi: " + personnage.getNom());
        return personnage;
    }

    /**
     * Lance le niveau choisi par le joueur.
     * Affiche les niveaux disponibles et demande à l'utilisateur de confirmer le lancement du niveau.
     *
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     * @param logInit l'objet de gestion des journaux
     * @param mesCartes la liste des cartes du jeu
     * @return true si le niveau est lancé, false sinon
     */
    public static boolean lancerNiveau(Scanner scanner, LogInit logInit, List<Carte> mesCartes) {
        niveauxTermines = new boolean[mesCartes.size()];

        for (int i = 0; i < mesCartes.size(); i++) {
            logInit.logMaker("Affichage des niveaux disponibles");
            logInit.logMaker("Niveau " + (i + 1) + ": " + mesCartes.get(i).getNom() + (niveauxTermines[i] ? " (Terminé)" : ""));

            logInit.logMaker("Demande de confirmation pour lancer le niveau");
            logInit.logMaker("Voulez-vous lancer ce niveau (o/n) ou quitter (q) ? ");
            String choix = scanner.next();
            if (choix.equalsIgnoreCase("q")) {
                logInit.logMaker("L'utilisateur a choisi de quitter. Fin du programme.");
                return false;
            } else if (choix.equalsIgnoreCase("n")) {
                logInit.logMaker("L'utilisateur a choisi de ne pas lancer le niveau. Fin du programme.");
                return false;
            } else if (choix.equalsIgnoreCase("o")) {
                Carte carte = mesCartes.get(i);
                logInit.logMaker("Niveau " + (i + 1) + " lancé: " + carte.getNom());
                carte.afficherCarte();
                logInit.logMaker("Affichage de la carte");
                return true;
            } else {
                logInit.logMaker("Choix invalide. Veuillez entrer 'o' pour lancer, 'n' pour ne pas lancer ou 'q' pour quitter.");
                i--; // Répéter l'invite du niveau actuel
            }
        }
        return true;
    }

    /**
     * Gère le déroulement du niveau en fonction des actions du joueur.
     * Le joueur peut avancer, reculer ou rencontrer des ennemis et des coffres.
     *
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     * @param logInit l'objet de gestion des journaux
     * @param personnage le personnage du joueur
     * @param mesCartes la liste des cartes du jeu
     */
    public static void jouerNiveau(Scanner scanner, LogInit logInit, Personnage personnage, List<Carte> mesCartes) {
        int position = 0;
        int currentLevel = 0;

        while (currentLevel < mesCartes.size()) {
            Carte carte = mesCartes.get(currentLevel);
            int longueurParcours = carte.getLongueurParcours();

            while (position < longueurParcours) {
                logInit.logMaker(personnage.getNom() + " est à la position " + position + " sur la carte.");
                logInit.logMaker("Demande de déplacement");

                if (position == 0) {
                    logInit.logMaker("Vous êtes au début de la carte, appuyez sur (a) pour avancer !");
                } else {
                    logInit.logMaker("Voulez-vous avancer (a) ou reculer (r) ? ");
                }

                String choix = scanner.next();

                if (choix.equalsIgnoreCase("a")) {
                    position++;
                    logInit.logMaker(personnage.getNom() + " avance.");
                } else if (choix.equalsIgnoreCase("r") && position > 0) {
                    position--;
                    logInit.logMaker(personnage.getNom() + " recule.");
                } else {
                    logInit.logMaker("Choix de déplacement invalide. Veuillez entrer 'a' pour avancer" + (position > 0 ? " ou 'r' pour reculer." : "."));
                    continue;
                }

                gererRencontres(logInit, personnage, scanner);
            }

            logInit.logMaker(personnage.getNom() + " a atteint la fin de la carte.");
            logInit.logMaker("PV restants à la fin de la carte: " + personnage.getPV());

            niveauxTermines[currentLevel] = true;

            if (currentLevel < mesCartes.size() - 1) {
                String choixNiveau;
                do {
                    logInit.logMaker("Voulez-vous passer au niveau suivant (o/n) ?");
                    choixNiveau = scanner.next();
                    if (choixNiveau.equalsIgnoreCase("o")) {
                        currentLevel++;
                        logInit.logMaker("Passage au niveau " + (currentLevel + 1) + ": " + mesCartes.get(currentLevel).getNom());
                        position = 0; // Réinitialiser la position pour le niveau suivant
                        logInit.logMaker("Position réinitialisée à " + position);
                        personnage.resetCapaciteUtilisee(); // Réinitialiser la capacité utilisée pour le niveau suivant
                        break;
                    } else if (choixNiveau.equalsIgnoreCase("n")) {
                        return;
                    } else {
                        logInit.logMaker("Choix invalide. Veuillez entrer 'o' pour passer au niveau suivant ou 'n' pour quitter.");
                    }
                } while (true);
            } else {
                logInit.logMaker(personnage.getNom() + " a terminé tous les niveaux. Félicitations !");
                break;
            }
        }
    }

    /**
     * Sélectionne un ennemi aléatoire en fonction des probabilités définies.
     *
     * @return l'ennemi sélectionné
     */
    public static Ennemis selectionnerEnnemi() {
        int chance = random.nextInt(100);
        if (chance < 45) {
            return new Brigants("Brigant");
        } else if (chance < 65 && chance >= 45) {
            return new Catcheurs("Catcheur");
        } else if (chance < 85 && chance >= 65) {
            return new CRS("CRS");
        } else if (chance >= 85) {
            return new Sniper("Sniper");
        } else {
            return new Brigants("Brigant");
        }
    }

    /**
     * Gère les rencontres aléatoires avec des ennemis ou des coffres pendant le déplacement du personnage.
     *
     * @param logInit l'objet de gestion des journaux
     * @param personnage le personnage du joueur
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     */
    public static void gererRencontres(LogInit logInit, Personnage personnage, Scanner scanner) {
        if (random.nextInt(100) < 50) { // 50% de chance de rencontrer un ennemi
            Ennemis ennemi = selectionnerEnnemi();
            logInit.logMaker(personnage.getNom() + " rencontre un " + ennemi.getNom() + " (" + ennemi.getPV() + " PV) !");
            personnage.combattre(ennemi, logInit, scanner, random);
            logInit.logMaker("PV restants après le combat: " + personnage.getPV());
            if (personnage.getPV() <= 0) {
                finirJeuCalled = true;
                finirJeu(logInit, personnage.getNom(), scanner);
            }
        } else if (random.nextInt(100) < 25) { // 25% de chance de trouver un coffre
            logInit.logMaker(personnage.getNom() + " trouve un coffre !");
            gererCoffre(logInit, personnage, scanner);
        }
    }

    /**
     * Gère l'ouverture des coffres trouvés par le personnage.
     *
     * @param logInit l'objet de gestion des journaux
     * @param personnage le personnage du joueur
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     */
    public static void gererCoffre(LogInit logInit, Personnage personnage, Scanner scanner) {
        logInit.logMaker("Debut gestion coffre");
        logInit.logMaker("Demande d'ouverture ou non");
        logInit.logMaker("Voulez-vous ouvrir le coffre (o/n) ? ");
        String choix = scanner.next();
        if (choix.equalsIgnoreCase("o")) {
            if (random.nextInt(100) < 50) { // 50% de chance de trouver une potion de soin
                int healAmount = random.nextInt(31) + 10; // Quantité de soin aléatoire entre 10 et 40
                personnage.setPV(personnage.getPV() + healAmount);
                logInit.logMaker(personnage.getNom() + " a trouvé une potion de soin et récupère " + healAmount + " PV.");
            } else { // 50% de chance de trouver un coffre vide ou inutile
                logInit.logMaker("Le coffre trouvé par " + personnage.getNom() + " est vide ou contient des objets inutiles.");
            }
        } else {
            logInit.logMaker(personnage.getNom() + " a décidé de ne pas ouvrir le coffre.");
        }
    }

    /**
     * Termine le jeu et ferme les ressources.
     *
     * @param logInit l'objet de gestion des journaux
     * @param nom le nom du personnage
     * @param scanner l'objet Scanner pour lire les entrées utilisateur
     */
    public static void finirJeu(LogInit logInit, String nom, Scanner scanner) {
        logInit.logMaker("Le jeu est terminé.");
        scanner.close();
        logInit.closeLog();
        System.exit(0);
    }
}