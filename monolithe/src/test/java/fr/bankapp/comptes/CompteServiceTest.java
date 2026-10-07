package fr.bankapp.comptes;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompteServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Consulte le solde cumulé d'un client")
    void consulteLeSolde() {
        assertEquals(new BigDecimal("5000.00"), new CompteService().consulterSolde("C001"));
    }
}
