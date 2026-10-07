package fr.bankapp.risqueservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoteurRisqueTest {

    private final MoteurRisque moteur = new MoteurRisque();
    private final BigDecimal montant = new BigDecimal("20000");

    @BeforeEach
    void preparer() {
        BaseRisque.initialiser();
    }

    @Test
    @DisplayName("Donne le même résultat que l'application Risque du monolithe")
    void memeResultatQueLeMonolithe() {
        assertEquals(new Evaluation(72, "ACCEPTE"), moteur.evaluer("C001", montant, 24));
        assertEquals(new Evaluation(68, "A_ETUDIER"), moteur.evaluer("C002", montant, 24));
        assertEquals(new Evaluation(40, "REFUSE"), moteur.evaluer("C003", montant, 24));
        assertEquals(new Evaluation(48, "REFUSE"), moteur.evaluer("C005", montant, 36));
    }

    @Test
    @DisplayName("Respecte les erreurs du contrat")
    void erreursDuContrat() {
        assertEquals("CLIENT_INCONNU", assertThrows(ErreurMetier.class,
                () -> moteur.evaluer("C999", montant, 24)).code());
        assertEquals("MONTANT_INVALIDE", assertThrows(ErreurMetier.class,
                () -> moteur.evaluer("C001", BigDecimal.ZERO, 24)).code());
    }
}
