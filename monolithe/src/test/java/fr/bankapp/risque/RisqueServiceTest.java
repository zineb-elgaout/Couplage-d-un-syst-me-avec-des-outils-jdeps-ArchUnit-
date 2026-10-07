package fr.bankapp.risque;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RisqueServiceTest {

    private final RisqueService service = new RisqueService();
    private final BigDecimal montant = new BigDecimal("20000");

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Accepte un client au score élevé")
    void accepteUnBonClient() {
        assertEquals(new Evaluation(72, DecisionRisque.ACCEPTE), service.evaluerRisque("C001", montant, 24));
    }

    @Test
    @DisplayName("Met à l'étude un client au score moyen")
    void metALEtudeUnScoreMoyen() {
        assertEquals(new Evaluation(68, DecisionRisque.A_ETUDIER), service.evaluerRisque("C002", montant, 24));
    }

    @Test
    @DisplayName("Retire 8 points par incident des douze derniers mois")
    void penaliseLesIncidents() {
        assertEquals(new Evaluation(48, DecisionRisque.REFUSE), service.evaluerRisque("C005", montant, 36));
    }

    @Test
    @DisplayName("Retire 5 points à un client de moins de 25 ans")
    void penaliseLesJeunes() {
        assertEquals(new Evaluation(40, DecisionRisque.REFUSE), service.evaluerRisque("C003", montant, 24));
    }

    @Test
    @DisplayName("Signale un client inconnu")
    void signaleUnClientInconnu() {
        RisqueException erreur = assertThrows(RisqueException.class,
                () -> service.evaluerRisque("C999", montant, 24));
        assertEquals("CLIENT_INCONNU", erreur.code());
    }

    @Test
    @DisplayName("Signale un montant invalide")
    void signaleUnMontantInvalide() {
        RisqueException erreur = assertThrows(RisqueException.class,
                () -> service.evaluerRisque("C001", BigDecimal.ZERO, 24));
        assertEquals("MONTANT_INVALIDE", erreur.code());
    }
}
