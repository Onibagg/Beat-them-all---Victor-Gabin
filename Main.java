import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

import com.example.personnages.*;
import com.example.ennemis.*;
import com.example.LogInit;

public class Main {
    private static Random random;

    public static void main(String[] args) {
        random = new Random(); // Initialize the random variable
        LogInit logInit = new LogInit();
        try {
            String timecode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            logInit.initializeLog("com/example/gamelog/game_"+ timecode +".log");
            Scanner scanner = new Scanner(System.in);

            List<Carte> mesCartes = initialiserJeu(logInit);
            Personnage personnage = choisirPersonnage(scanner, logInit);

            if (!lancerNiveau(scanner, logInit, mesCartes)) {
                scanner.close();
                logInit.closeLog();
                return;
            }

            jouerNiveau(scanner, logInit, personnage, mesCartes); // Pass the entire list of Carte objects

            scanner.close();
            logInit.closeLog();
        } catch (IOException e) {
            logInit.logMaker("Une erreur s'est produite.");
            e.printStackTrace();
        }
    }

    private static List<Carte> initialiserJeu(LogInit logInit) throws IOException {
        logInit.logMaker("Génération des cartes...");
        List<Carte> mesCartes = Carte.genererCartes();
        logInit.logMaker("Cartes générées");
        return mesCartes;
    }

    private static Personnage choisirPersonnage(Scanner scanner, LogInit logInit) {
        logInit.logMaker("Affichage des personnages disponibles...");
        System.out.println("Personnages disponibles :");
        System.out.println("1. Hitman");
        System.out.println("2. Creeper");
        System.out.println("3. Guerisseur");

        logInit.logMaker("Demande de choix de personnage");
        System.out.print("Choisissez votre personnage (1-3) : ");
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
                System.out.println("Choix invalide. Personnage par défaut: Hitman.");
                personnage = new Hitman("HITMAN");
                break;
        }
        logInit.logMaker("Personnage choisi: " + personnage.getNom());
        return personnage;
    }

    private static boolean[] niveauxTermines;

    private static boolean lancerNiveau(Scanner scanner, LogInit logInit, List<Carte> mesCartes) {
        niveauxTermines = new boolean[mesCartes.size()];

        while (true) {
            System.out.println("Niveaux disponibles :");
            for (int i = 0; i < mesCartes.size(); i++) {
                String status = niveauxTermines[i] ? " (Terminé)" : "";
                System.out.println("Niveau " + (i + 1) + ": " + mesCartes.get(i).getNom() + status);
            }

            System.out.print("Choisissez un niveau à lancer (1-" + mesCartes.size() + ") ou 'q' pour quitter : ");
            String choix = scanner.next();
            if (choix.equalsIgnoreCase("q")) {
                logInit.logMaker("L'utilisateur a choisi de quitter. Fin du programme.");
                return false;
            }

            try {
                int niveauChoisi = Integer.parseInt(choix) - 1;
                if (niveauChoisi >= 0 && niveauChoisi < mesCartes.size()) {
                    Carte carte = mesCartes.get(niveauChoisi);
                    logInit.logMaker("Niveau " + (niveauChoisi + 1) + " lancé: " + carte.getNom());
                    carte.afficherCarte();
                    logInit.logMaker("Affichage de la carte");
                    return true;
                } else {
                    System.out.println("Choix invalide. Veuillez entrer un numéro de niveau valide.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide. Veuillez entrer un numéro de niveau valide.");
            }
        }
    }

    private static void jouerNiveau(Scanner scanner, LogInit logInit, Personnage personnage, List<Carte> mesCartes) {
        Random random = new Random();
        int position = 0;
        int currentLevel = 0;

        while (currentLevel < mesCartes.size()) {
            Carte carte = mesCartes.get(currentLevel);
            int longueurParcours = carte.getLongueurParcours();

            while (position < longueurParcours) {
                logInit.logMaker(personnage.getNom() + " est à la position " + position + " sur la carte.");
                System.out.print("Voulez-vous avancer (a) ou reculer (r) ? ");
                logInit.logMaker("Demande de déplacement");
                String choix = scanner.next();

                if (choix.equalsIgnoreCase("a")) {
                    position++;
                    System.out.println(personnage.getNom() + " avance.");
                    logInit.logMaker(personnage.getNom() + " avance.");
                } else if (choix.equalsIgnoreCase("r")) {
                    position = Math.max(0, position - 1);
                    System.out.println(personnage.getNom() + " recule.");
                    logInit.logMaker(personnage.getNom() + " recule.");
                } else {
                    System.out.println("Choix invalide. Veuillez entrer 'a' pour avancer ou 'r' pour reculer.");
                    logInit.logMaker("Choix de déplacement invalide");
                    continue;
                }

                gererRencontres(logInit, personnage, scanner);
            }

            System.out.println(personnage.getNom() + " a atteint la fin de la carte.");
            logInit.logMaker(personnage.getNom() + " a atteint la fin de la carte.");
            System.out.println("PV restants à la fin de la carte: " + personnage.getPV());
            logInit.logMaker("PV restants à la fin de la carte: " + personnage.getPV());

            niveauxTermines[currentLevel] = true;

            if (currentLevel < mesCartes.size() - 1) {
                System.out.print("Voulez-vous passer au niveau suivant (o/n) ? ");
                String choixNiveau = scanner.next();
                if (choixNiveau.equalsIgnoreCase("o")) {
                    currentLevel++;
                    System.out.println("Vous allez passer au niveau " + (currentLevel + 1) + ": " + mesCartes.get(currentLevel).getNom());
                    logInit.logMaker("Passage au niveau " + (currentLevel + 1) + ": " + mesCartes.get(currentLevel).getNom());
                    position = 0; // Reset position for the next level
                } else {
                    break;
                }
            } else {
                System.out.println("Vous avez terminé tous les niveaux. Félicitations !");
                logInit.logMaker(personnage.getNom() + " a terminé tous les niveaux.");
                break;
            }
        }
    }

    private static Ennemis selectionnerEnnemi() {
        int chance = random.nextInt(100);
        if (chance < 40) {
            return new Brigants("Brigant");
        } else if (chance < 60) {
            return new Catcheurs("Catcheur");
        } else if (chance < 80) {
            return new CRS("CRS");
        } else if (chance < 95) {
            return new Sniper("Sniper");
        } else {
            return new EnnemisSpeciaux("Ennemi spécial");
        }
    }

    private static void gererRencontres(LogInit logInit, Personnage personnage, Scanner scanner) {
        personnage.capaciteUtilisee = false; // Reset special attack availability at the beginning of each level
        if (random.nextInt(100) < 30) { // 30% chance to encounter an enemy
            Ennemis ennemi = selectionnerEnnemi();
            System.out.println(personnage.getNom() + " rencontre un " + ennemi.getNom() + " !");
            logInit.logMaker(personnage.getNom() + " rencontre un " + ennemi.getNom() + " !");
            personnage.combattre(ennemi, logInit, scanner);
            System.out.println("PV restants après le combat: " + personnage.getPV());
            logInit.logMaker("PV restants après le combat: " + personnage.getPV());
            if (personnage.getPV() <= 0) {
                System.out.println(personnage.getNom() + " a été vaincu. Fin du jeu.");
                logInit.logMaker(personnage.getNom() + " a été vaincu. Fin du jeu.");
                scanner.close();
                logInit.closeLog();
                System.exit(0);
            }
        } else if (random.nextInt(100) < 15) { // 15% chance to find a chest
            System.out.println(personnage.getNom() + " trouve un coffre !");
            logInit.logMaker(personnage.getNom() + " trouve un coffre !");
            gererCoffre(logInit, personnage, scanner);
        }
    }

    private static void gererCoffre(LogInit logInit, Personnage personnage, Scanner scanner) {
        System.out.print("Voulez-vous ouvrir le coffre (o/n) ? ");
        String choix = scanner.next();
        if (choix.equalsIgnoreCase("o")) {
            if (random.nextInt(100) < 50) { // 50% chance to get a heal
                int healAmount = random.nextInt(31) + 10; // Random heal amount between 10 and 40
                personnage.setPV(personnage.getPV() + healAmount);
                System.out.println("Vous avez trouvé une potion de soin ! Vous récupérez " + healAmount + " PV.");
                logInit.logMaker(personnage.getNom() + " a trouvé une potion de soin et récupère " + healAmount + " PV.");
            } else { // 50% chance to get an empty or useless chest
                System.out.println("Le coffre est vide ou contient des objets inutiles.");
                logInit.logMaker("Le coffre trouvé par " + personnage.getNom() + " est vide ou contient des objets inutiles.");
            }
        } else {
            System.out.println("Vous avez décidé de ne pas ouvrir le coffre.");
            logInit.logMaker(personnage.getNom() + " a décidé de ne pas ouvrir le coffre.");
        }
    }
}