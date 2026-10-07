-- Migration des données depuis RISK_DB et CRM_DB du monolithe.
-- En production, ce script serait une extraction suivie d'une synchronisation.
INSERT INTO T_SCORE VALUES ('C001', 72), ('C002', 68), ('C003', 45), ('C004', 61), ('C005', 64), ('C006', 78);
INSERT INTO T_INCIDENT VALUES
    ('C005', DATEADD('MONTH', -2, CURRENT_DATE)),
    ('C005', DATEADD('MONTH', -5, CURRENT_DATE));
INSERT INTO T_PROFIL VALUES
    ('C001', DATEADD('YEAR', -41, CURRENT_DATE)),
    ('C002', DATEADD('YEAR', -36, CURRENT_DATE)),
    ('C003', DATEADD('YEAR', -22, CURRENT_DATE)),
    ('C004', DATEADD('YEAR', -48, CURRENT_DATE)),
    ('C005', DATEADD('YEAR', -31, CURRENT_DATE)),
    ('C006', DATEADD('YEAR', -44, CURRENT_DATE));
