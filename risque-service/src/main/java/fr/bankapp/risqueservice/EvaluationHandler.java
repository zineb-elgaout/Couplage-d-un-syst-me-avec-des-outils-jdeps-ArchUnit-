package fr.bankapp.risqueservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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

    private final ObjectMapper json = new ObjectMapper();


    @Override
    public void handle(HttpExchange echange) throws IOException {
        Map<String, String> p = parametres(echange);

        try {
            Evaluation evaluation = moteur.evaluer(
                p.get("idClient"),
                new BigDecimal(p.getOrDefault("montant", "0")),
                Integer.parseInt(p.getOrDefault("dureeMois", "0"))
            );

            repondre(
                echange,
                200,
                json.writeValueAsString(evaluation)
            );

        } catch (ErreurMetier e) {
            int codeHttp = e.code().equals("CLIENT_INCONNU") ? 404 : 400;

            repondre(
                echange,
                codeHttp,
                "{\"erreur\":\"" + e.code() + "\"}"
            );

        } catch (NumberFormatException e) {
            repondre(
                echange,
                400,
                "{\"erreur\":\"MONTANT_INVALIDE\"}"
            );
        }
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
