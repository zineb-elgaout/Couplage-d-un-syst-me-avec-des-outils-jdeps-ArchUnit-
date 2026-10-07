package fr.bankapp.mobile;

import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.commun.SortieConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppMobileServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Envoie au client un code de connexion à six chiffres")
    void envoieUnCode() {
        try (SortieConsole console = new SortieConsole()) {
            String code = new AppMobileService().envoyerCodeConnexion("C006");
            assertTrue(code.matches("\\d{6}"));
            assertTrue(console.texte().contains("SMS -> 0666666666 : Votre code BankApp : " + code));
        }
    }
}
