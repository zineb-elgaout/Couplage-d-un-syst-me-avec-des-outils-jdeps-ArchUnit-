package fr.bankapp.mobile.interne;

import java.security.SecureRandom;

/** Codes de connexion à usage unique. */
public class GenerateurCode {

    private final SecureRandom aleatoire = new SecureRandom();

    public String nouveauCode() {
        return String.format("%06d", aleatoire.nextInt(1_000_000));
    }
}
