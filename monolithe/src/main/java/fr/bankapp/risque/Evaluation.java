package fr.bankapp.risque;

/** Résultat d'une évaluation : score de 0 à 100 et décision. */
public record Evaluation(int score, DecisionRisque decision) {
}
