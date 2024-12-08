package com.example.personnages;

import com.example.ennemis.Ennemis;
import com.example.LogInit;
import java.util.Scanner;

public interface Personnage {
    String getNom();
    int getPV();
    void setPV(int PV);
    void utiliserCapaciteSpeciale();
    void combattre(Ennemis ennemi, LogInit logInit, Scanner scanner);
    void resetCapaciteUtilisee(); // Nouvelle méthode pour réinitialiser la capacité utilisée
    boolean isCapaciteUtilisee();
    void utiliserCapacite();
}