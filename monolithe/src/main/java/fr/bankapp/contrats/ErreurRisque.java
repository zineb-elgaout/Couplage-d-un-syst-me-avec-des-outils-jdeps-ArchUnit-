package fr.bankapp.contrats;

/** Erreurs prévues par le contrat : CLIENT_INCONNU, MONTANT_INVALIDE, SERVICE_INDISPONIBLE. */
public class ErreurRisque extends RuntimeException {

    private final String code;

    public ErreurRisque(String code, String detail) {
        super(code + " (" + detail + ")");
        this.code = code;
    }

    public String code() {
        return code;
    }
}
