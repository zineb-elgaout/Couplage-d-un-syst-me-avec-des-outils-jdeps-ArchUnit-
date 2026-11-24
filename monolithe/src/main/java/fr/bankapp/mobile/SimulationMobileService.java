package fr.bankapp.mobile;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.contrats.DecisionRisque;
import fr.bankapp.contrats.ServiceRisque;

import java.math.BigDecimal;

/** Simulation de prêt dans l'application mobile : réponse immédiate « pré-accord » ou non. */
public class SimulationMobileService {

    static final BigDecimal TAUX_APPORT = new BigDecimal("0.10");

    private final ServiceRisque risque;

    public SimulationMobileService(ServiceRisque risque) {
        this.risque = risque;
    }

    public boolean preAccord(String idClient, BigDecimal montant, int dureeMois) {
        DecisionRisque decision = risque.evaluerRisque(idClient, montant, dureeMois).decision();
        BigDecimal solde = BaseDeDonnees.jdbc().queryForObject(
            "SELECT COALESCE(SUM(SOLDE), 0) FROM CPT_DB.T_COMPTE WHERE ID_CLIENT = ?",
            BigDecimal.class,
            idClient
        );
        return decision != DecisionRisque.REFUSE
            && solde.compareTo(montant.multiply(TAUX_APPORT)) >= 0;
    }
}
