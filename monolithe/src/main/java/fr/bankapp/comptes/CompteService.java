package fr.bankapp.comptes;

import fr.bankapp.comptes.interne.CompteDAO;

import java.math.BigDecimal;

/** Service public de l'application Comptes. */
public class CompteService {

    private final CompteDAO dao = new CompteDAO();

    /** Solde cumulé de tous les comptes du client. */
    public BigDecimal consulterSolde(String idClient) {
        return dao.getSolde(idClient);
    }
}
