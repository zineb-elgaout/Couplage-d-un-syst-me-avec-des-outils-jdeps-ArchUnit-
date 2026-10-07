package fr.bankapp.comptes;

import fr.bankapp.comptes.interne.Mouvement;
import fr.bankapp.comptes.interne.MouvementDAO;
import fr.bankapp.prets.interne.Echeance;
import fr.bankapp.prets.interne.EcheanceDAO;

import java.util.ArrayList;
import java.util.List;

/** Relevé de compte : derniers mouvements et prochaines échéances de prêt. */
public class ReleveService {

    public List<String> releve(String idClient) {
        List<String> lignes = new ArrayList<>();
        for (Mouvement m : new MouvementDAO().derniers(idClient, 5)) {
            lignes.add(m.date() + "  " + m.libelle() + "  " + m.montant());
        }
        for (Echeance e : new EcheanceDAO().prochaines(idClient)) {
            lignes.add(e.date() + "  Échéance prêt n°" + e.idPret() + "  -" + e.montant());
        }
        return lignes;
    }
}
