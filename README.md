# BankApp

Système d'information d'une banque de détail, utilisé en TP du cours SOA.

- `monolithe/` : les cinq applications historiques (Comptes, Prêts, Risque, CRM, Mobile) dans un même projet, avec une base de données partagée.
- `risque-service/` : le futur service Risque, extrait du monolithe pendant le TP1.
- `annuaire/` : l'annuaire des services (fichier écrit par les fournisseurs, lu par les consommateurs).

Lancer les tests : `mvn -q test` (ou `.\mvnw.cmd -q test` sous Windows sans Maven).
Lancer le monolithe : `mvn -q -pl monolithe exec:java`.
