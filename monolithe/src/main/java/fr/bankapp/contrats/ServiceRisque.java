package fr.bankapp.contrats;

import java.math.BigDecimal;

/**
 * Contrat du service Risque (chapitre 1, slide 19).
 *
 * Opération : evaluerRisque
 * Entrée    : idClient, montant, dureeMois
 * Sortie    : score de 0 à 100, décision ACCEPTE, REFUSE ou A_ETUDIER
 * Erreurs   : CLIENT_INCONNU, MONTANT_INVALIDE
 * Qualité   : réponse en moins de 2 secondes
 */
public interface ServiceRisque {

    EvaluationRisque evaluerRisque(String idClient, BigDecimal montant, int dureeMois);
}
