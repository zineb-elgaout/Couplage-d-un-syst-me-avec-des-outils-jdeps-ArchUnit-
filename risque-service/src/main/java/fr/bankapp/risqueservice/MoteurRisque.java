package fr.bankapp.risqueservice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Le calcul officiel du risque, déplacé hors du monolithe par l'équipe Risque.
 * Même règle qu'avant : score de base, moins 8 points par incident des douze derniers mois,
 * moins 5 points avant 25 ans ; ACCEPTE à partir de 70, A_ETUDIER à partir de 50.
 */
public class MoteurRisque {

    public Evaluation evaluer(String idClient, BigDecimal montant, int dureeMois) {
        if (montant == null || montant.signum() <= 0 || dureeMois <= 0) {
            throw new ErreurMetier("MONTANT_INVALIDE");
        }
        List<Integer> base = BaseRisque.jdbc().queryForList(
                "SELECT SCORE FROM T_SCORE WHERE ID_CLIENT = ?", Integer.class, idClient);
        if (base.isEmpty()) {
            throw new ErreurMetier("CLIENT_INCONNU");
        }
        int incidents = BaseRisque.jdbc().queryForObject(
                "SELECT COUNT(*) FROM T_INCIDENT WHERE ID_CLIENT = ? AND DATE_INCIDENT >= ?",
                Integer.class, idClient, LocalDate.now().minusMonths(12));
        LocalDate naissance = BaseRisque.jdbc().queryForObject(
                "SELECT DATE_NAISSANCE FROM T_PROFIL WHERE ID_CLIENT = ?", LocalDate.class, idClient);

        int score = base.get(0) - 8 * incidents;
        if (Period.between(naissance, LocalDate.now()).getYears() < 25) {
            score -= 5;
        }
        score = Math.max(0, Math.min(100, score));
        return new Evaluation(score, decider(score, montant));
    }

    private String decider(int score, BigDecimal montant) {
        if (score < 50) {
            return "REFUSE";
        }
        if (score < 70 || montant.compareTo(BigDecimal.valueOf(100_000)) > 0) {
            return "A_ETUDIER";
        }
        return "ACCEPTE";
    }
}
