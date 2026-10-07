package com.acme.sms;

/**
 * Librairie cliente du fournisseur de SMS Acme.
 * FAUSSE LIBRAIRIE pour le TP : elle écrit dans la console, elle n'envoie rien.
 */
public class AcmeSms {

    private final String passerelle;

    /** @param passerelle adresse IP de la passerelle SMS du fournisseur */
    public AcmeSms(String passerelle) {
        this.passerelle = passerelle;
    }

    public void send(String telephone, String texte) {
        System.out.println("[AcmeSms " + passerelle + "] SMS -> " + telephone + " : " + texte);
    }
}
