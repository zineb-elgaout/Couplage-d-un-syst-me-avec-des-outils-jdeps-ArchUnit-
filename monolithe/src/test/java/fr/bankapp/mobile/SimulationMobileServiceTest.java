package fr.bankapp.mobile;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationMobileServiceTest {

    private final SimulationMobileService service = new SimulationMobileService();

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Donne un pré-accord à un client solvable et peu risqué")
    void preAccordePourUnBonClient() {
        assertTrue(service.preAccord("C001", new BigDecimal("20000"), 24));
    }

    @Test
    @DisplayName("Refuse le pré-accord à un client risqué")
    void refusePourUnClientRisque() {
        assertFalse(service.preAccord("C003", new BigDecimal("5000"), 12));
    }
}
