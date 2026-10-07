package fr.bankapp.mobile;

import fr.bankapp.comptes.CompteService;
import fr.bankapp.mobile.interne.ResumeMobile;
import fr.bankapp.prets.interne.Echeance;
import fr.bankapp.prets.interne.EcheanceDAO;

import java.time.LocalDate;
import java.util.List;

/** Écran d'accueil de l'application mobile. */
public class TableauDeBordMobile {

    public ResumeMobile resume(String idClient) {
        List<Echeance> echeances = new EcheanceDAO().prochaines(idClient);
        LocalDate prochaine = echeances.isEmpty() ? null : echeances.get(0).date();
        return new ResumeMobile(idClient, new CompteService().consulterSolde(idClient), prochaine);
    }
}
