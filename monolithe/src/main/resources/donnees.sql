-- Six clients de démonstration. Les dates sont relatives au jour du lancement.

INSERT INTO CRM_DB.T_CLIENT VALUES
    ('C001', 'Amina Alaoui',   DATEADD('YEAR', -41, CURRENT_DATE), '0611111111', 'amina.alaoui@mail.ma'),
    ('C002', 'Karim Bennani',  DATEADD('YEAR', -36, CURRENT_DATE), '0622222222', 'karim.bennani@mail.ma'),
    ('C003', 'Sara Idrissi',   DATEADD('YEAR', -22, CURRENT_DATE), '0633333333', 'sara.idrissi@mail.ma'),
    ('C004', 'Omar Tazi',      DATEADD('YEAR', -48, CURRENT_DATE), '0644444444', 'omar.tazi@mail.ma'),
    ('C005', 'Nadia Fassi',    DATEADD('YEAR', -31, CURRENT_DATE), '0655555555', 'nadia.fassi@mail.ma'),
    ('C006', 'Hicham Berrada', DATEADD('YEAR', -44, CURRENT_DATE), '0666666666', 'hicham.berrada@mail.ma');

INSERT INTO CPT_DB.T_COMPTE VALUES
    ('CPT-001', 'C001',  5000.00),
    ('CPT-002', 'C002',   300.00),
    ('CPT-003', 'C003',  8000.00),
    ('CPT-004', 'C004',  -150.00),
    ('CPT-005', 'C005',  4000.00),
    ('CPT-006', 'C006', 12000.00);

INSERT INTO CPT_DB.T_MOUVEMENT (ID_COMPTE, DATE_MVT, MONTANT, LIBELLE) VALUES
    ('CPT-001', DATEADD('DAY', -20, CURRENT_DATE),  9000.00, 'Salaire'),
    ('CPT-001', DATEADD('DAY', -12, CURRENT_DATE), -4000.00, 'Loyer'),
    ('CPT-005', DATEADD('DAY',  -3, CURRENT_DATE), -6000.00, 'Retrait guichet'),
    ('CPT-006', DATEADD('DAY',  -5, CURRENT_DATE), 15000.00, 'Salaire');

INSERT INTO RISK_DB.T_SCORE VALUES
    ('C001', 72, DATEADD('DAY', -30, CURRENT_DATE)),
    ('C002', 68, DATEADD('DAY', -30, CURRENT_DATE)),
    ('C003', 45, DATEADD('DAY', -30, CURRENT_DATE)),
    ('C004', 61, DATEADD('DAY', -30, CURRENT_DATE)),
    ('C005', 64, DATEADD('DAY', -30, CURRENT_DATE)),
    ('C006', 78, DATEADD('DAY', -30, CURRENT_DATE));

INSERT INTO RISK_DB.T_INCIDENT (ID_CLIENT, DATE_INCIDENT, TYPE_INCIDENT) VALUES
    ('C005', DATEADD('MONTH', -2, CURRENT_DATE), 'IMPAYE'),
    ('C005', DATEADD('MONTH', -5, CURRENT_DATE), 'DECOUVERT_NON_AUTORISE');

-- Un prêt en cours pour Amina Alaoui
INSERT INTO PRET_DB.T_PRET (ID_PRET, ID_CLIENT, MONTANT, DUREE_MOIS, STATUT) VALUES (1, 'C001', 30000.00, 36, 'ACCORDE');
INSERT INTO PRET_DB.T_ECHEANCE VALUES
    (1, 1, DATEADD('DAY', 10, CURRENT_DATE), 899.00),
    (1, 2, DATEADD('DAY', 40, CURRENT_DATE), 899.00),
    (1, 3, DATEADD('DAY', 70, CURRENT_DATE), 899.00);
