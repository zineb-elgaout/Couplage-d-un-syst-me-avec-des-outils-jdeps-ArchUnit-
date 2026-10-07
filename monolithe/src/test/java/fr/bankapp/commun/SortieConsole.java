package fr.bankapp.commun;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Capture ce qui est écrit dans la console pendant un test (la librairie SMS n'offre pas d'autre moyen). */
public class SortieConsole implements AutoCloseable {

    private final PrintStream origine = System.out;
    private final ByteArrayOutputStream tampon = new ByteArrayOutputStream();

    public SortieConsole() {
        System.setOut(new PrintStream(tampon, true, StandardCharsets.UTF_8));
    }

    public String texte() {
        return tampon.toString(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {
        System.setOut(origine);
    }
}
