package fr.bankapp.comptes;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReleveServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Le relevé liste les mouvements puis les échéances de prêt")
    void listeMouvementsEtEcheances() {
        List<String> releve = new ReleveService().releve("C001");
        assertEquals(5, releve.size());
        assertTrue(releve.get(0).contains("Loyer"));
        assertTrue(releve.get(2).contains("Échéance prêt n°1"));
    }
}
