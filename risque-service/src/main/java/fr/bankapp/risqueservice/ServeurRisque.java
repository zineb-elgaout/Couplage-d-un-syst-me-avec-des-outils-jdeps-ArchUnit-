package fr.bankapp.risqueservice;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Le processus du service Risque.
 * Au démarrage, il initialise sa propre base, ouvre le port HTTP et publie son adresse dans l'annuaire.
 */
public class ServeurRisque {

    static final String CLE_ANNUAIRE = "risque.evaluation";

    public static void main(String[] args) throws IOException {
        int port = Integer.getInteger("bankapp.risque.port", 8081);
        BaseRisque.initialiser();

        HttpServer serveur = HttpServer.create(new InetSocketAddress(port), 0);
        serveur.createContext("/risque/evaluation", new EvaluationHandler(new MoteurRisque()));
        serveur.start();

        String adresse = "http://localhost:" + port + "/risque/evaluation";
        publier(adresse);
        System.out.println("Service Risque démarré. Contrat publié dans l'annuaire : " + CLE_ANNUAIRE + " = " + adresse);
    }

    /** Rôle de fournisseur : écrire son adresse dans l'annuaire de services. */
    static void publier(String adresse) throws IOException {
        Path annuaire = Path.of(System.getProperty("bankapp.annuaire", "annuaire/services.properties"));
        Properties services = new Properties();
        if (Files.exists(annuaire)) {
            try (var lecture = Files.newBufferedReader(annuaire)) {
                services.load(lecture);
            }
        }
        services.setProperty(CLE_ANNUAIRE, adresse);
        Files.createDirectories(annuaire.getParent());
        try (Writer ecriture = Files.newBufferedWriter(annuaire)) {
            services.store(ecriture, "Annuaire des services BankApp");
        }
    }
}
