package fr.bankapp.prets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulateurPretTest {

    @Test
    @DisplayName("Calcule la mensualité d'un prêt de 30 000 sur 36 mois à 5 %")
    void calculeLaMensualite() {
        assertEquals(new BigDecimal("899.13"), new SimulateurPret().mensualite(new BigDecimal("30000"), 36));
    }
}
