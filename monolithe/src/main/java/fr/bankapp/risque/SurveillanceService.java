package fr.bankapp.risque;

import fr.bankapp.comptes.interne.Mouvement;
import fr.bankapp.comptes.interne.MouvementDAO;
import fr.bankapp.risque.interne.IncidentDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Détection des opérations inhabituelles : chaque gros retrait récent devient un incident. */
public class SurveillanceService {

    static final BigDecimal SEUIL_RETRAIT = new BigDecimal("5000");

    /** Retourne le nombre d'incidents créés. */
    public int analyser(String idClient) {
        List<Mouvement> retraits = new MouvementDAO()
                .retraitsImportants(idClient, SEUIL_RETRAIT, LocalDate.now().minusDays(7));
        IncidentDAO incidents = new IncidentDAO();
        for (Mouvement retrait : retraits) {
            incidents.ajouter(idClient, "RETRAIT_INHABITUEL");
        }
        return retraits.size();
    }
}
