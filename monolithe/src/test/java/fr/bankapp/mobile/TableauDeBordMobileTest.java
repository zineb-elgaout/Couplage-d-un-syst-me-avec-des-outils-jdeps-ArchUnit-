package fr.bankapp.mobile;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.mobile.interne.ResumeMobile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableauDeBordMobileTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Affiche le solde et la prochaine échéance")
    void afficheSoldeEtEcheance() {
        ResumeMobile resume = new TableauDeBordMobile().resume("C001");
        assertEquals(new BigDecimal("5000.00"), resume.solde());
        assertEquals(LocalDate.now().plusDays(10), resume.prochaineEcheance());
    }
}
