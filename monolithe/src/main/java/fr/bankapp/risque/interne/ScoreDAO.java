package fr.bankapp.risque.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.util.List;
import java.util.Optional;

/** Accès aux scores de base. Classe interne à l'application Risque. */
public class ScoreDAO {

    public Optional<Integer> scoreDeBase(String idClient) {
        List<Integer> scores = BaseDeDonnees.jdbc().queryForList(
                "SELECT SCORE FROM RISK_DB.T_SCORE WHERE ID = ?", Integer.class, idClient);
        return scores.stream().findFirst();
    }
}
