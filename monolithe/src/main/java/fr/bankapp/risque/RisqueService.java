package fr.bankapp.risque;

import fr.bankapp.risque.interne.CalculateurScore;
import fr.bankapp.risque.interne.Seuils;

import java.math.BigDecimal;

/** Service public de l'application Risque : la seule évaluation officielle du risque. */
public class RisqueService {

    private final CalculateurScore calculateur = new CalculateurScore();

    public Evaluation evaluerRisque(String idClient, BigDecimal montant, int dureeMois) {
        if (montant == null || montant.signum() <= 0 || dureeMois <= 0) {
            throw new RisqueException("MONTANT_INVALIDE", "montant et durée doivent être positifs");
        }
        int score = calculateur.calculer(idClient);
        if (score < 0) {
            throw new RisqueException("CLIENT_INCONNU", idClient);
        }
        return new Evaluation(score, decider(score, montant));
    }

    private DecisionRisque decider(int score, BigDecimal montant) {
        if (score < Seuils.scoreAEtudier()) {
            return DecisionRisque.REFUSE;
        }
        if (score < Seuils.scoreAccepte() || montant.compareTo(BigDecimal.valueOf(Seuils.montantSansEtude())) > 0) {
            return DecisionRisque.A_ETUDIER;
        }
        return DecisionRisque.ACCEPTE;
    }
}
