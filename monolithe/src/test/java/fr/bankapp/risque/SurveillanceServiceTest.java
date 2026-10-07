package fr.bankapp.risque;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SurveillanceServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Transforme un gros retrait récent en incident")
    void detecteUnGrosRetrait() {
        assertEquals(1, new SurveillanceService().analyser("C005"));
    }

    @Test
    @DisplayName("Ne signale rien pour un client sans gros retrait")
    void neSignaleRienSansGrosRetrait() {
        assertEquals(0, new SurveillanceService().analyser("C001"));
    }
}
