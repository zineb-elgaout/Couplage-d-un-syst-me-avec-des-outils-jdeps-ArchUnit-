package fr.bankapp.mobile;

import com.acme.sms.AcmeSms;
import fr.bankapp.crm.interne.ClientDAO;
import fr.bankapp.mobile.interne.GenerateurCode;

/** Connexion à l'application mobile. */
public class AppMobileService {

    private final GenerateurCode generateur = new GenerateurCode();

    /** Envoie un code de connexion à usage unique et le retourne pour vérification. */
    public String envoyerCodeConnexion(String idClient) {
        String code = generateur.nouveauCode();
        String telephone = new ClientDAO().getTelephone(idClient);
        new AcmeSms("10.2.4.17").send(telephone, "Votre code BankApp : " + code);
        return code;
    }
}
