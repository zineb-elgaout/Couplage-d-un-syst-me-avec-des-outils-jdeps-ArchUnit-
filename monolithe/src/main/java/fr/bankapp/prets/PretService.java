package fr.bankapp.prets;

import com.acme.sms.AcmeSms;
import fr.bankapp.commun.BaseDeDonnees;
import fr.bankapp.comptes.interne.CompteDAO;
import fr.bankapp.crm.interne.ClientDAO;
import fr.bankapp.prets.interne.EcheanceDAO;
import fr.bankapp.prets.interne.PretDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Service public de l'application Prêts : instruit les demandes de prêt. */
public class PretService {

    static final BigDecimal TAUX_APPORT = new BigDecimal("0.10");
    static final int SCORE_MINIMUM = 60;

    public Decision traiter(Demande d) {
        // Solde du client
        BigDecimal solde = new CompteDAO().getSolde(d.idClient());

        // Score de risque du client
        Integer score = BaseDeDonnees.jdbc().queryForObject(
                "SELECT score FROM RISK_DB.T_SCORE WHERE id = ?", Integer.class, d.idClient());

        // Accusé de réception
        String telephone = new ClientDAO().getTelephone(d.idClient());
        new AcmeSms("10.2.4.17").send(telephone, "Demande reçue");

        Decision decision = decider(d, solde, score);
        enregistrer(d, decision);
        return decision;
    }

    private Decision decider(Demande d, BigDecimal solde, int score) {
        BigDecimal apportExige = d.montant().multiply(TAUX_APPORT).setScale(2, RoundingMode.HALF_UP);
        if (solde.compareTo(apportExige) < 0) {
            return Decision.refus("Apport insuffisant : " + apportExige + " exigés");
        }
        if (score < SCORE_MINIMUM) {
            return Decision.refus("Risque trop élevé");
        }
        return Decision.accord();
    }

    private void enregistrer(Demande d, Decision decision) {
        int idPret = new PretDAO().enregistrer(d.idClient(), d.montant(), d.dureeMois(), decision.statut().name());
        if (decision.statut() == StatutPret.ACCORDE) {
            BigDecimal mensualite = new SimulateurPret().mensualite(d.montant(), d.dureeMois());
            new EcheanceDAO().creerEcheancier(idPret, mensualite);
        }
    }
}
