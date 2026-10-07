package fr.bankapp.mobile.interne;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Ce que l'écran d'accueil de l'application mobile affiche. */
public record ResumeMobile(String idClient, BigDecimal solde, LocalDate prochaineEcheance) {
}
