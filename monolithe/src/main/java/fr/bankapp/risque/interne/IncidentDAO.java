package fr.bankapp.risque.interne;

import fr.bankapp.commun.BaseDeDonnees;

import java.time.LocalDate;

/** Accès aux incidents de paiement. Classe interne à l'application Risque. */
public class IncidentDAO {

    public int compterDepuis(String idClient, LocalDate depuis) {
        return BaseDeDonnees.jdbc().queryForObject(
                "SELECT COUNT(*) FROM RISK_DB.T_INCIDENT WHERE ID_CLIENT = ? AND DATE_INCIDENT >= ?",
                Integer.class, idClient, depuis);
    }

    public void ajouter(String idClient, String type) {
        BaseDeDonnees.jdbc().update(
                "INSERT INTO RISK_DB.T_INCIDENT (ID_CLIENT, DATE_INCIDENT, TYPE_INCIDENT) VALUES (?, CURRENT_DATE, ?)",
                idClient, type);
    }
}
