package fr.bankapp.crm;

import fr.bankapp.commun.BaseDeDonnees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientServiceTest {

    @BeforeEach
    void preparer() {
        BaseDeDonnees.initialiser();
    }

    @Test
    @DisplayName("Retourne la fiche du client")
    void retourneLaFiche() {
        assertEquals("Nadia Fassi", new ClientService().fiche("C005").nom());
    }
}
