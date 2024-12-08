import java.util.ArrayList;
import java.util.List;

public class Carte {
    private String lieu;
    private String nom;
    private String debut;
    private String fin;
    private int longueurParcours;

    public Carte(String lieu, String nom, String debut, String fin, int longueurParcours) {
        this.lieu = lieu;
        this.nom = nom;
        this.debut = debut;
        this.fin = fin;
        this.longueurParcours = longueurParcours;
    }

    public void afficherCarte() {
        System.out.println("Lieu: " + lieu);
        System.out.println("Nom: " + nom);
        System.out.println("Début: " + debut);
        System.out.println("Fin: " + fin);
        System.out.println("Longueur du parcours: " + longueurParcours + " km");
    }

    public int getLongueurParcours() {
        return longueurParcours;
    }

    public String getNom() {
        return nom;
    }

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