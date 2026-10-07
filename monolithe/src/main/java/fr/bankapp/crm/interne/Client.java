package fr.bankapp.crm.interne;

import java.time.LocalDate;

public record Client(String idClient, String nom, LocalDate dateNaissance, String telephone, String email) {
}
