package fr.bankapp.integration;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Côté consommateur : rechercher l'adresse d'un service dans l'annuaire, au lieu de l'écrire en dur. */
public final class Annuaire {

    private Annuaire() {
    }

    public static String rechercher(String cle) {
        Path fichier = Path.of(System.getProperty("bankapp.annuaire", "../annuaire/services.properties"));
        Properties services = new Properties();
        try (Reader lecture = Files.newBufferedReader(fichier)) {
            services.load(lecture);
        } catch (IOException e) {
            throw new IllegalStateException("Annuaire illisible : " + fichier.toAbsolutePath(), e);
        }
        String adresse = services.getProperty(cle);
        if (adresse == null) {
            throw new IllegalStateException("Aucun service publié sous la clé " + cle);
        }
        return adresse;
    }
}
