package fr.bankapp.architecture;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Recherche de texte dans les sources du monolithe.
 * ArchUnit analyse le bytecode : il ne voit pas une requête SQL écrite dans une chaîne de caractères.
 * Cet utilitaire comble ce manque en lisant directement les fichiers .java.
 */
public final class SourcesJava {

    private static final Path RACINE = Path.of("src/main/java");

    private SourcesJava() {
    }

    /** Retourne « fichier:ligne » pour chaque occurrence du texte, hors du dossier exclu. */
    public static List<String> occurrences(String texte, String dossierExclu) {
        List<String> resultats = new ArrayList<>();
        try (Stream<Path> fichiers = Files.walk(RACINE)) {
            for (Path fichier : fichiers.filter(f -> f.toString().endsWith(".java")).toList()) {
                String relatif = RACINE.relativize(fichier).toString().replace('\\', '/');
                if (relatif.startsWith(dossierExclu)) {
                    continue;
                }
                List<String> lignes = Files.readAllLines(fichier);
                for (int i = 0; i < lignes.size(); i++) {
                    if (lignes.get(i).contains(texte)) {
                        resultats.add(relatif + ":" + (i + 1));
                    }
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return resultats;
    }
}
