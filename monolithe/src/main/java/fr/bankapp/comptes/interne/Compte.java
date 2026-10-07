package fr.bankapp.comptes.interne;

import java.math.BigDecimal;

public record Compte(String idCompte, String idClient, BigDecimal solde) {
}
