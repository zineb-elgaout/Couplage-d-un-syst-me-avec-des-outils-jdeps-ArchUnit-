package fr.bankapp.comptes;

import com.acme.sms.AcmeSms;
import fr.bankapp.comptes.interne.Compte;
import fr.bankapp.comptes.interne.CompteDAO;
import fr.bankapp.comptes.interne.MouvementDAO;
import fr.bankapp.crm.interne.ClientDAO;
import fr.bankapp.risque.SurveillanceService;

import java.math.BigDecimal;

/** Virements entre comptes de la banque. */
public class VirementService {

    private final CompteDAO comptes = new CompteDAO();
    private final MouvementDAO mouvements = new MouvementDAO();

    /** Effectue le virement. Retourne false si le montant est invalide. */
    public boolean virer(String compteSource, String compteDestination, BigDecimal montant) {
        if (montant.signum() <= 0) {
            return false;
        }
        comptes.ajouterAuSolde(compteSource, montant.negate());
        comptes.ajouterAuSolde(compteDestination, montant);
        mouvements.ajouter(compteSource, montant.negate(), "Virement vers " + compteDestination);
        mouvements.ajouter(compteDestination, montant, "Virement de " + compteSource);

        // Contrôle anti-fraude après chaque virement
        Compte source = comptes.trouver(compteSource);
        new SurveillanceService().analyser(source.idClient());

        // Alerte si le compte passe à découvert
        if (source.solde().signum() < 0) {
            String telephone = new ClientDAO().getTelephone(source.idClient());
            new AcmeSms("10.2.4.17").send(telephone, "Votre compte " + compteSource + " est à découvert");
        }
        return true;
    }
}
