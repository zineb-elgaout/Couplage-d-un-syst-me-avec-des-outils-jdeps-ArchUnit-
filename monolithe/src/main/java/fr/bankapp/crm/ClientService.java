package fr.bankapp.crm;

import fr.bankapp.crm.interne.Client;
import fr.bankapp.crm.interne.ClientDAO;

/** Service public de l'application CRM : la fiche client. */
public class ClientService {

    public Client fiche(String idClient) {
        return new ClientDAO().trouver(idClient);
    }
}
