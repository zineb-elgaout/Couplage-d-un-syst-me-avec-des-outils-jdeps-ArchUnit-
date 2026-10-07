package fr.bankapp.comptes.interne;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Mouvement(String idCompte, LocalDate date, BigDecimal montant, String libelle) {
}
