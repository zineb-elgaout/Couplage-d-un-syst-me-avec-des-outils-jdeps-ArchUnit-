package fr.bankapp.prets;

import java.math.BigDecimal;

/** Demande de prêt déposée par un client. */
public record Demande(String idClient, BigDecimal montant, int dureeMois) {
}
