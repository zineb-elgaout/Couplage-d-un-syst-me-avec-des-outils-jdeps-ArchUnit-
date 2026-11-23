package fr.bankapp.contrats;

/** Sortie de l'opération evaluerRisque : un score de 0 à 100 et une décision. */
public record EvaluationRisque(int score, DecisionRisque decision) {
}
