package fr.bankapp.integration;

import fr.bankapp.contrats.EvaluationRisque;
import fr.bankapp.contrats.ServiceRisque;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

/**
 * Patron Strangler Fig : à chaque appel, l'aiguillage lit bascule.properties et envoie la demande
 * vers la réalisation locale (le monolithe) ou distante (le nouveau service).
 * On bascule donc sans redémarrer, et on peut revenir en arrière à tout moment.
 */
public class AiguillageRisque implements ServiceRisque {

    private final ServiceRisque local = new RisqueLocal();
    private final ServiceRisque distant = new RisqueDistant();

    @Override
    public EvaluationRisque evaluerRisque(String idClient, BigDecimal montant, int dureeMois) {
        String realisation = realisationChoisie();
        long debut = System.nanoTime();
        try {
            return (realisation.equals("distant") ? distant : local).evaluerRisque(idClient, montant, dureeMois);
        } finally {
            double millisecondes = (System.nanoTime() - debut) / 1_000_000.0;
            System.out.printf(Locale.FRANCE, "[Risque %s] %s évalué en %.2f ms%n", realisation, idClient, millisecondes);
        }
    }

    static String realisationChoisie() {
        Path fichier = Path.of(System.getProperty("bankapp.bascule", "bascule.properties"));
        Properties bascule = new Properties();
        try (Reader lecture = Files.newBufferedReader(fichier)) {
            bascule.load(lecture);
        } catch (IOException e) {
            return "local";
        }
        return bascule.getProperty("risque.realisation", "local").trim();
    }
}
