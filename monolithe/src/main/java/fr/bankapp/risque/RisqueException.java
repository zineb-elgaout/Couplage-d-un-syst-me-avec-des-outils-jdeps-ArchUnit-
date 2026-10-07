package fr.bankapp.risque;

/** Erreur métier de l'évaluation du risque : CLIENT_INCONNU ou MONTANT_INVALIDE. */
public class RisqueException extends RuntimeException {

    private final String code;

    public RisqueException(String code, String message) {
        super(code + " : " + message);
        this.code = code;
    }

    public String code() {
        return code;
    }
}
