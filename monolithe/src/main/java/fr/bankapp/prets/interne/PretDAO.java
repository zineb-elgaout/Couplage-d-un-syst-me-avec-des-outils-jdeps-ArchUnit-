package fr.bankapp.prets.interne;

import fr.bankapp.commun.BaseDeDonnees;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;

/** Accès à la table des prêts. Classe interne à l'application Prêts. */
public class PretDAO {

    /** Enregistre le prêt et retourne son identifiant. */
    public int enregistrer(String idClient, BigDecimal montant, int dureeMois, String statut) {
        GeneratedKeyHolder cle = new GeneratedKeyHolder();
        BaseDeDonnees.jdbc().update(connexion -> {
            PreparedStatement ps = connexion.prepareStatement(
                    "INSERT INTO PRET_DB.T_PRET (ID_CLIENT, MONTANT, DUREE_MOIS, STATUT) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, idClient);
            ps.setBigDecimal(2, montant);
            ps.setInt(3, dureeMois);
            ps.setString(4, statut);
            return ps;
        }, cle);
        return cle.getKey().intValue();
    }

    public int compterParStatut(String statut) {
        return BaseDeDonnees.jdbc().queryForObject(
                "SELECT COUNT(*) FROM PRET_DB.T_PRET WHERE STATUT = ?", Integer.class, statut);
    }
}
