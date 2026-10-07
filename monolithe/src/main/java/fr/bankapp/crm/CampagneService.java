package fr.bankapp.crm;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.comptes.interne.CompteDAO;
import fr.bankapp.crm.interne.ClientDAO;
import fr.bankapp.risque.interne.Seuils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Campagnes commerciales : proposer une offre de crédit aux clients éligibles. */
public class CampagneService {

    static final int MARGE_COMMERCIALE = 5;
    static final BigDecimal SOLDE_MINIMUM = new BigDecimal("1000");

    private final MessagerieService messagerie = new MessagerieService();

    /** Envoie l'offre aux clients éligibles et retourne leurs identifiants. */
    public List<String> proposerCredit(BigDecimal montant, int dureeMois) {
        List<String> cibles = new ArrayList<>();
        for (String idClient : new ClientDAO().tousLesIdentifiants()) {
            if (estEligible(idClient)) {
                messagerie.envoyerSms(idClient, "Offre spéciale : crédit de " + montant + " sur " + dureeMois + " mois");
                cibles.add(idClient);
            }
        }
        return cibles;
    }

    private boolean estEligible(String idClient) {
        Integer score = BaseDeDonnees.jdbc().queryForObject(
                "SELECT SCORE FROM RISK_DB.T_SCORE WHERE ID = ?", Integer.class, idClient);
        BigDecimal solde = new CompteDAO().getSolde(idClient);
        return score >= Seuils.scoreAccepte() - MARGE_COMMERCIALE && solde.compareTo(SOLDE_MINIMUM) >= 0;
    }
}
