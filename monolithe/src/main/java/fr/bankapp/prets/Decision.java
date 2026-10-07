package fr.bankapp.prets;

/** Réponse de la banque à une demande de prêt. */
public record Decision(StatutPret statut, String motif) {

    public static Decision accord() {
        return new Decision(StatutPret.ACCORDE, "Prêt accordé");
    }

    public static Decision refus(String motif) {
        return new Decision(StatutPret.REFUSE, motif);
    }

    public static Decision enEtude(String motif) {
        return new Decision(StatutPret.EN_ETUDE, motif);
    }
}
