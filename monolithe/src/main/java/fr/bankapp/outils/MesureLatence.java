package fr.bankapp.outils;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.contrats.ServiceRisque;
import fr.bankapp.integration.RisqueDistant;
import fr.bankapp.integration.RisqueLocal;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Compare le temps moyen d'une évaluation du risque : appel local (dans le monolithe)
 * et appel distant (service Risque, en HTTP). Le service Risque doit être démarré.
 */
public class MesureLatence {

    static final int ECHAUFFEMENT = 50;
    static final int APPELS = 500;

    public static void main(String[] args) {
        BaseDeDonnees.initialiser();
        double local = moyenne(new RisqueLocal());
        double distant = moyenne(new RisqueDistant());
        System.out.printf(Locale.FRANCE, "Appel local   : %.3f ms en moyenne sur %d appels%n", local, APPELS);
        System.out.printf(Locale.FRANCE, "Appel distant : %.3f ms en moyenne sur %d appels%n", distant, APPELS);
        System.out.printf(Locale.FRANCE, "L'appel distant est %.1f fois plus lent%n", distant / local);
    }

    static double moyenne(ServiceRisque service) {
        BigDecimal montant = new BigDecimal("20000");
        for (int i = 0; i < ECHAUFFEMENT; i++) {
            service.evaluerRisque("C001", montant, 24);
        }
        long debut = System.nanoTime();
        for (int i = 0; i < APPELS; i++) {
            service.evaluerRisque("C001", montant, 24);
        }
        return (System.nanoTime() - debut) / 1_000_000.0 / APPELS;
    }
}
