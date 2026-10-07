package fr.bankapp.comptes.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Accès à l'historique des mouvements. Classe interne à l'application Comptes. */
public class MouvementDAO {

    public void ajouter(String idCompte, BigDecimal montant, String libelle) {
        BaseDeDonnees.jdbc().update(
                "INSERT INTO CPT_DB.T_MOUVEMENT (ID_COMPTE, DATE_MVT, MONTANT, LIBELLE) VALUES (?, CURRENT_DATE, ?, ?)",
                idCompte, montant, libelle);
    }

    public List<Mouvement> derniers(String idClient, int nombre) {
        return BaseDeDonnees.jdbc().query("""
                SELECT m.ID_COMPTE, m.DATE_MVT, m.MONTANT, m.LIBELLE
                FROM CPT_DB.T_MOUVEMENT m JOIN CPT_DB.T_COMPTE c ON c.ID_COMPTE = m.ID_COMPTE
                WHERE c.ID_CLIENT = ? ORDER BY m.DATE_MVT DESC, m.ID DESC LIMIT ?""",
                (rs, n) -> new Mouvement(rs.getString(1), rs.getDate(2).toLocalDate(), rs.getBigDecimal(3), rs.getString(4)),
                idClient, nombre);
    }

    /** Retraits d'un montant supérieur au seuil, effectués depuis la date donnée. */
    public List<Mouvement> retraitsImportants(String idClient, BigDecimal seuil, LocalDate depuis) {
        return BaseDeDonnees.jdbc().query("""
                SELECT m.ID_COMPTE, m.DATE_MVT, m.MONTANT, m.LIBELLE
                FROM CPT_DB.T_MOUVEMENT m JOIN CPT_DB.T_COMPTE c ON c.ID_COMPTE = m.ID_COMPTE
                WHERE c.ID_CLIENT = ? AND m.MONTANT < 0 AND -m.MONTANT > ? AND m.DATE_MVT >= ?""",
                (rs, n) -> new Mouvement(rs.getString(1), rs.getDate(2).toLocalDate(), rs.getBigDecimal(3), rs.getString(4)),
                idClient, seuil, depuis);
    }
}
