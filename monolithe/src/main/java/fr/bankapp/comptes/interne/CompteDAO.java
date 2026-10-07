package fr.bankapp.comptes.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.math.BigDecimal;

/** Accès à la table des comptes. Classe interne à l'application Comptes. */
public class CompteDAO {

    public BigDecimal getSolde(String idClient) {
        return BaseDeDonnees.jdbc().queryForObject(
                "SELECT COALESCE(SUM(SOLDE), 0) FROM CPT_DB.T_COMPTE WHERE ID_CLIENT = ?", BigDecimal.class, idClient);
    }

    public Compte trouver(String idCompte) {
        return BaseDeDonnees.jdbc().queryForObject(
                "SELECT ID_COMPTE, ID_CLIENT, SOLDE FROM CPT_DB.T_COMPTE WHERE ID_COMPTE = ?",
                (rs, n) -> new Compte(rs.getString(1), rs.getString(2), rs.getBigDecimal(3)), idCompte);
    }

    public void ajouterAuSolde(String idCompte, BigDecimal montant) {
        BaseDeDonnees.jdbc().update(
                "UPDATE CPT_DB.T_COMPTE SET SOLDE = SOLDE + ? WHERE ID_COMPTE = ?", montant, idCompte);
    }
}
