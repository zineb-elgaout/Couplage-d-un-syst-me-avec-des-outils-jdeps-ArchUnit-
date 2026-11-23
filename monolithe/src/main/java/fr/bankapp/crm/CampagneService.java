package fr.bankapp.crm;

import fr.bankapp.comptes.interne.CompteDAO;
import fr.bankapp.contrats.DecisionRisque;
import fr.bankapp.contrats.ServiceRisque;
import fr.bankapp.crm.interne.ClientDAO;
import fr.bankapp.integration.RisqueLocal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Campagnes commerciales : proposer une offre de crédit aux clients éligibles. */
public class CampagneService {

    static final BigDecimal SOLDE_MINIMUM = new BigDecimal("1000");

    private final MessagerieService messagerie = new MessagerieService();
    private final ServiceRisque risque;

    public CampagneService() {
        this(new RisqueLocal());
    }

    public CampagneService(ServiceRisque risque) {
        this.risque = risque;
    }

    /** Envoie l'offre aux clients éligibles et retourne leurs identifiants. */
    public List<String> proposerCredit(BigDecimal montant, int dureeMois) {
        List<String> cibles = new ArrayList<>();
        for (String idClient : new ClientDAO().tousLesIdentifiants()) {
            if (estEligible(idClient, montant, dureeMois)) {
                messagerie.envoyerSms(idClient, "Offre spéciale : crédit de " + montant + " sur " + dureeMois + " mois");
                cibles.add(idClient);
            }
        }
        return cibles;
    }

    private boolean estEligible(String idClient, BigDecimal montant, int dureeMois) {
        DecisionRisque decision = risque.evaluerRisque(idClient, montant, dureeMois).decision();
        BigDecimal solde = new CompteDAO().getSolde(idClient);
        return decision == DecisionRisque.ACCEPTE
            && solde.compareTo(SOLDE_MINIMUM) >= 0;
    }
}
