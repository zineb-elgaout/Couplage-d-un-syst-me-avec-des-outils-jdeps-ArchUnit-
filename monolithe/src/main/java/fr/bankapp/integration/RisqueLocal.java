package fr.bankapp.integration;

import java.math.BigDecimal;

import fr.bankapp.contrats.DecisionRisque;
import fr.bankapp.contrats.ErreurRisque;
import fr.bankapp.contrats.EvaluationRisque;
import fr.bankapp.contrats.ServiceRisque;

import fr.bankapp.risque.RisqueService;
import fr.bankapp.risque.RisqueException;

/** Façade locale vers l'application Risque historique. */
public class RisqueLocal implements ServiceRisque {

    private final RisqueService risque = new RisqueService();

    @Override
    public EvaluationRisque evaluerRisque(
            String idClient,
            BigDecimal montant,
            int dureeMois) {

        try {
            var evaluation = risque.evaluerRisque(
                idClient,
                montant,
                dureeMois
            );

            return new EvaluationRisque(
                evaluation.score(),
                DecisionRisque.valueOf(evaluation.decision().name())
            );

        } catch (RisqueException e) {
            throw new ErreurRisque(e.code(), idClient);
        }
    }
}
