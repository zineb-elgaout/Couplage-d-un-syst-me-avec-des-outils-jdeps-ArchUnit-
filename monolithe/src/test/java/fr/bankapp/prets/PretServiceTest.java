package fr.bankapp.prets;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.commun.SortieConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PretServiceTest {

    private PretService service;

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
        service = new PretService();
    }

    @Test
    @DisplayName("Accorde le prêt à un client solvable et peu risqué")
    void accordeAuClientSolvable() {
        try (SortieConsole console = new SortieConsole()) {
            Decision decision = service.traiter(new Demande("C001", new BigDecimal("20000"), 24));
            assertEquals(StatutPret.ACCORDE, decision.statut());
        }
    }

    @Test
    @DisplayName("Refuse le prêt si le solde ne couvre pas 10 % du montant")
    void refuseSiApportInsuffisant() {
        try (SortieConsole console = new SortieConsole()) {
            Decision decision = service.traiter(new Demande("C002", new BigDecimal("10000"), 12));
            assertEquals(StatutPret.REFUSE, decision.statut());
            assertEquals("Apport insuffisant : 1000.00 exigés", decision.motif());
        }
    }

    @Test
    @DisplayName("Refuse le prêt à un client trop risqué")
    void refuseSiRisqueTropEleve() {
        try (SortieConsole console = new SortieConsole()) {
            Decision decision = service.traiter(new Demande("C003", new BigDecimal("5000"), 12));
            assertEquals(StatutPret.REFUSE, decision.statut());
            assertEquals("Risque trop élevé", decision.motif());
        }
    }

    @Test
    @DisplayName("Envoie un accusé de réception au client")
    void envoieUnAccuseDeReception() {
        try (SortieConsole console = new SortieConsole()) {
            service.traiter(new Demande("C001", new BigDecimal("20000"), 24));
            assertTrue(console.texte().contains("SMS -> 0611111111 : Demande reçue"));
        }
    }
}
