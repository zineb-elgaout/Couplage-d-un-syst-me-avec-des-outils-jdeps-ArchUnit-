package fr.bankapp.comptes;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.commun.SortieConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VirementServiceTest {

    private final VirementService service = new VirementService();
    private final CompteService comptes = new CompteService();

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Débite la source et crédite la destination")
    void deplaceLeMontant() {
        try (SortieConsole console = new SortieConsole()) {
            assertTrue(service.virer("CPT-006", "CPT-002", new BigDecimal("700")));
            assertEquals(new BigDecimal("11300.00"), comptes.consulterSolde("C006"));
            assertEquals(new BigDecimal("1000.00"), comptes.consulterSolde("C002"));
        }
    }

    @Test
    @DisplayName("Alerte le client dont le compte passe à découvert")
    void alerteSiDecouvert() {
        try (SortieConsole console = new SortieConsole()) {
            service.virer("CPT-002", "CPT-001", new BigDecimal("500"));
            assertTrue(console.texte().contains("SMS -> 0622222222 : Votre compte CPT-002 est à découvert"));
        }
    }

    @Test
    @DisplayName("Refuse un montant négatif")
    void refuseUnMontantNegatif() {
        assertFalse(service.virer("CPT-001", "CPT-002", new BigDecimal("-10")));
    }
}
