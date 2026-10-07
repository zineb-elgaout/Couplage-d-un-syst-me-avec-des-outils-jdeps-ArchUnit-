package fr.bankapp.prets.interne;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Echeance(int idPret, int numero, LocalDate date, BigDecimal montant) {
}
