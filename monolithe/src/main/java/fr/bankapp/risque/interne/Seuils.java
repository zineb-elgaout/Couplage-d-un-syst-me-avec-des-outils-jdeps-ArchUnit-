package fr.bankapp.risque.interne;

/** Seuils de décision de l'application Risque, ajustés chaque trimestre par l'équipe Risque. */
public final class Seuils {

    private Seuils() {
    }

    public static int scoreAccepte() {
        return 70;
    }

    public static int scoreAEtudier() {
        return 50;
    }

    public static int montantSansEtude() {
        return 100_000;
    }
}
