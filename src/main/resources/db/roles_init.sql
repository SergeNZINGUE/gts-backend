-- Initialisation des rôles TerraOps
-- À exécuter une seule fois sur la base de données cible

INSERT INTO role (code_type_role, rolename, date_creation, date_modification)
SELECT 'MANAGER', 'MANAGER', CURDATE(), CURDATE()
WHERE NOT EXISTS (SELECT 1 FROM role WHERE code_type_role = 'MANAGER');

INSERT INTO role (code_type_role, rolename, date_creation, date_modification)
SELECT 'OPERATEUR', 'OPERATEUR', CURDATE(), CURDATE()
WHERE NOT EXISTS (SELECT 1 FROM role WHERE code_type_role = 'OPERATEUR');

INSERT INTO role (code_type_role, rolename, date_creation, date_modification)
SELECT 'COMPTABLE', 'COMPTABLE', CURDATE(), CURDATE()
WHERE NOT EXISTS (SELECT 1 FROM role WHERE code_type_role = 'COMPTABLE');

-- Vérification
SELECT id, code_type_role, rolename FROM role ORDER BY id;
