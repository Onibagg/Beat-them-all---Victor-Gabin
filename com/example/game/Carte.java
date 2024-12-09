package com.example.game;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Carte représente une carte dans le jeu avec un lieu, un nom, un point de départ, un point d'arrivée et une longueur de parcours.
 */
public class Carte {
    private String lieu;
    private String nom;
    private String debut;
    private String fin;
    private int longueurParcours;

    /**
     * Constructeur pour créer une nouvelle carte.
     *
     * @param lieu le lieu de la carte
     * @param nom le nom de la carte
     * @param debut le point de départ de la carte
     * @param fin le point d'arrivée de la carte
     * @param longueurParcours la longueur du parcours de la carte en kilomètres
     */
    public Carte(String lieu, String nom, String debut, String fin, int longueurParcours) {
        this.lieu = lieu;
        this.nom = nom;
        this.debut = debut;
        this.fin = fin;
        this.longueurParcours = longueurParcours;
    }

    /**
     * Affiche les détails de la carte.
     */
    public void afficherCarte() {
        System.out.println("Lieu: " + lieu);
        System.out.println("Nom: " + nom);
        System.out.println("Début: " + debut);
        System.out.println("Fin: " + fin);
        System.out.println("Longueur du parcours: " + longueurParcours + " km");
    }

    /**
     * Renvoie la longueur du parcours de la carte.
     *
     * @return la longueur du parcours en kilomètres
     */
    public int getLongueurParcours() {
        return longueurParcours;
    }

    /**
     * Renvoie le nom de la carte.
     *
     * @return le nom de la carte
     */
    public String getNom() {
        return nom;
    }

    /**
     * Génère une liste de cartes prédéfinies.
     *
     * @return une liste de cartes
     */
    public static List<Carte> genererCartes() {
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        cartes.add(new Carte("Paris", "Tour de Paris", "Louvre", "Tour Eiffel", 5));
        cartes.add(new Carte("New York", "Tour de New York", "Central Park", "Statue de la Liberté", 7));
        cartes.add(new Carte("Tokyo", "Tour de Tokyo", "Shibuya", "Tokyo Tower", 6));
        cartes.add(new Carte("Londres", "Tour de Londres", "Big Ben", "London Eye", 4));
        cartes.add(new Carte("Rome", "Tour de Rome", "Colisée", "Vatican", 5));
        return cartes;
    }
}