package fr.bankapp.prets.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.math.BigDecimal;
import java.util.List;

/** Accès aux échéanciers des prêts. Classe interne à l'application Prêts. */
public class EcheanceDAO {

    /** Crée les trois premières échéances mensuelles d'un prêt. */
    public void creerEcheancier(int idPret, BigDecimal mensualite) {
        for (int numero = 1; numero <= 3; numero++) {
            BaseDeDonnees.jdbc().update(
                    "INSERT INTO PRET_DB.T_ECHEANCE VALUES (?, ?, DATEADD('MONTH', ?, CURRENT_DATE), ?)",
                    idPret, numero, numero, mensualite);
        }
    }

    public List<Echeance> prochaines(String idClient) {
        return BaseDeDonnees.jdbc().query("""
                SELECT e.ID_PRET, e.NUMERO, e.DATE_ECHEANCE, e.MONTANT
                FROM PRET_DB.T_ECHEANCE e JOIN PRET_DB.T_PRET p ON p.ID_PRET = e.ID_PRET
                WHERE p.ID_CLIENT = ? AND e.DATE_ECHEANCE >= CURRENT_DATE ORDER BY e.DATE_ECHEANCE""",
                (rs, n) -> new Echeance(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate(), rs.getBigDecimal(4)),
                idClient);
    }
}
