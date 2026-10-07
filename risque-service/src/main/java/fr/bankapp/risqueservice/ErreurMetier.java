package fr.bankapp.risqueservice;

/** Erreur prévue par le contrat : CLIENT_INCONNU ou MONTANT_INVALIDE. */
public class ErreurMetier extends RuntimeException {

    private final String code;

    public ErreurMetier(String code) {
        super(code);
        this.code = code;
    }

    public String code() {
        return code;
    }
}
