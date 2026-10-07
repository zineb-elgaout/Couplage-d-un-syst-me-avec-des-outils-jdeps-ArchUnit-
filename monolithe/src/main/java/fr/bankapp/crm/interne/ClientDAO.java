package fr.bankapp.crm.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.time.LocalDate;
import java.util.List;

/** Accès à la fiche client. Classe interne à l'application CRM. */
public class ClientDAO {

    public Client trouver(String idClient) {
        return BaseDeDonnees.jdbc().queryForObject(
                "SELECT ID_CLIENT, NOM, DATE_NAISSANCE, TELEPHONE, EMAIL FROM CRM_DB.T_CLIENT WHERE ID_CLIENT = ?",
                (rs, n) -> new Client(rs.getString(1), rs.getString(2), rs.getDate(3).toLocalDate(),
                        rs.getString(4), rs.getString(5)), idClient);
    }

    public String getTelephone(String idClient) {
        return trouver(idClient).telephone();
    }

    public LocalDate getDateNaissance(String idClient) {
        return trouver(idClient).dateNaissance();
    }

    public List<String> tousLesIdentifiants() {
        return BaseDeDonnees.jdbc().queryForList("SELECT ID_CLIENT FROM CRM_DB.T_CLIENT ORDER BY ID_CLIENT", String.class);
    }
}
