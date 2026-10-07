package fr.bankapp.crm;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.commun.SortieConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CampagneServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Propose le crédit aux seuls clients peu risqués et solvables")
    void cibleLesClientsEligibles() {
        try (SortieConsole console = new SortieConsole()) {
            List<String> cibles = new CampagneService().proposerCredit(new BigDecimal("20000"), 36);
            assertEquals(List.of("C001", "C006"), cibles);
            assertTrue(console.texte().contains("SMS -> 0666666666 : Offre spéciale"));
        }
    }
}
