package fr.bankapp.crm;

import com.acme.sms.AcmeSms;
import fr.bankapp.crm.interne.ClientDAO;

/** Service public de l'application CRM : les messages envoyés aux clients. */
public class MessagerieService {

    public void envoyerSms(String idClient, String texte) {
        String telephone = new ClientDAO().getTelephone(idClient);
        new AcmeSms("10.2.4.17").send(telephone, texte);
    }
}
