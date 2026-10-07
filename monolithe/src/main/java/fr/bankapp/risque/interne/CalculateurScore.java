package fr.bankapp.risque.interne;

import fr.bankapp.crm.interne.ClientDAO;

import java.time.LocalDate;
import java.time.Period;

/**
 * Le calcul officiel du score : score de base, moins 8 points par incident
 * des douze derniers mois, moins 5 points pour un client de moins de 25 ans.
 */
public class CalculateurScore {

    static final int PENALITE_INCIDENT = 8;
    static final int PENALITE_JEUNE = 5;

    private final ScoreDAO scores = new ScoreDAO();
    private final IncidentDAO incidents = new IncidentDAO();

    /** Retourne -1 si le client n'a pas de score de base. */
    public int calculer(String idClient) {
        Integer base = scores.scoreDeBase(idClient).orElse(null);
        if (base == null) {
            return -1;
        }
        int score = base - PENALITE_INCIDENT * incidents.compterDepuis(idClient, LocalDate.now().minusMonths(12));
        LocalDate naissance = new ClientDAO().getDateNaissance(idClient);
        if (Period.between(naissance, LocalDate.now()).getYears() < 25) {
            score -= PENALITE_JEUNE;
        }
        return Math.max(0, Math.min(100, score));
    }
}
