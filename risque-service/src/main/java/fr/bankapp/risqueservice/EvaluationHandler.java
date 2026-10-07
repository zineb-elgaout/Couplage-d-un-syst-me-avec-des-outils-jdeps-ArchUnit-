package fr.bankapp.risqueservice;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Point d'entrée HTTP de l'opération evaluerRisque.
 * GET /risque/evaluation?idClient=C001&montant=20000&dureeMois=24
 */
public class EvaluationHandler implements HttpHandler {

    private final MoteurRisque moteur;

    public EvaluationHandler(MoteurRisque moteur) {
        this.moteur = moteur;
    }

    @Override
    public void handle(HttpExchange echange) throws IOException {
        // À COMPLÉTER À L'ÉTAPE 3 : lire les paramètres, appeler le moteur, répondre en JSON.
        repondre(echange, 501, "{\"erreur\":\"NON_IMPLEMENTE\"}");
    }

    /** Lit les paramètres de l'URL : idClient=C001&montant=20000 devient une Map. */
    static Map<String, String> parametres(HttpExchange echange) {
        Map<String, String> valeurs = new HashMap<>();
        String requete = echange.getRequestURI().getRawQuery();
        if (requete != null) {
            for (String paire : requete.split("&")) {
                String[] cleValeur = paire.split("=", 2);
                valeurs.put(cleValeur[0], cleValeur.length > 1 ? URLDecoder.decode(cleValeur[1], StandardCharsets.UTF_8) : "");
            }
        }
        return valeurs;
    }

    /** Envoie une réponse JSON avec le code HTTP donné. */
    static void repondre(HttpExchange echange, int codeHttp, String json) throws IOException {
        byte[] corps = json.getBytes(StandardCharsets.UTF_8);
        echange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        echange.sendResponseHeaders(codeHttp, corps.length);
        try (OutputStream sortie = echange.getResponseBody()) {
            sortie.write(corps);
        }
    }
}
