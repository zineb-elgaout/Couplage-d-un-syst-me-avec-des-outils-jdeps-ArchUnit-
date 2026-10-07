package fr.bankapp.risqueservice;

/** Réponse du service : score de 0 à 100 et décision (ACCEPTE, REFUSE, A_ETUDIER). */
public record Evaluation(int score, String decision) {
}
