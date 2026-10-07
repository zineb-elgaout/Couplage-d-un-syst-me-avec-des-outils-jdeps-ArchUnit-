package fr.bankapp.mobile;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.risque.interne.ScoreDAO;

import java.math.BigDecimal;

/** Simulation de prêt dans l'application mobile : réponse immédiate « pré-accord » ou non. */
public class SimulationMobileService {

    static final int SCORE_PRE_ACCORD = 55;
    static final BigDecimal TAUX_APPORT = new BigDecimal("0.10");

    public boolean preAccord(String idClient, BigDecimal montant, int dureeMois) {
        int score = new ScoreDAO().scoreDeBase(idClient).orElse(0);
        BigDecimal solde = BaseDeDonnees.jdbc().queryForObject(
                "SELECT COALESCE(SUM(SOLDE), 0) FROM CPT_DB.T_COMPTE WHERE ID_CLIENT = ?", BigDecimal.class, idClient);
        return score >= SCORE_PRE_ACCORD && solde.compareTo(montant.multiply(TAUX_APPORT)) >= 0;
    }
}
