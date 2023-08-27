-- Limpa todas as tabelas
SET session_replication_role = replica;
DELETE FROM _user;
DELETE FROM address;
DELETE FROM profile;
DELETE FROM restaurant;
TRUNCATE address RESTART IDENTITY CASCADE;
TRUNCATE profile RESTART IDENTITY CASCADE;
TRUNCATE restaurant RESTART IDENTITY CASCADE;
SET session_replication_role = DEFAULT;

    -- Insere dados na tabela address
INSERT INTO address(street, number, complement, district, city, state, zip) VALUES
('Rua A', '100', 'Apto 10', 'Centro', 'Guarujá', 'SP', '12345-123'),
('Rua B', '101', 'Apto 11', 'Centro', 'Guarujá', 'SP', '12345-124'),
('Rua C', '102', 'Apto 12', 'Leste', 'Guarujá', 'SP', '12345-125'),
('Rua D', '103', 'Apto 13', 'Leste', 'Guarujá', 'SP', '12345-126'),
('Rua E', '104', 'Apto 14', 'Oeste', 'Santos', 'SP', '12345-127'),
('Rua F', '105', 'Apto 15', 'Oeste', 'Santos', 'SP', '12345-128'),
('Rua G', '106', 'Apto 16', 'Norte', 'Santos', 'SP', '12345-129'),
('Rua H', '107', 'Apto 17', 'Norte', 'São Vicente', 'SP', '12345-130'),
('Rua I', '108', 'Apto 18', 'Sul', 'São Vicente', 'SP', '12345-131'),
('Rua J', '109', 'Apto 19', 'Sul', 'São Vicente', 'SP', '12345-132');

-- Insere dados na tabela restaurant (considerando que os IDs gerados para address vão de 1 a 10)
INSERT INTO restaurant(name, slug, cnpj, phone, score, address_id, created_at, updated_at) VALUES
('Restaurante A', 'restaurante-a', '11223344556677', '+551234567890', 4.5, 1, NOW(), NOW()),
('Restaurante B', 'restaurante-b', '11223344556678', '+551234567891', 4.0, 2, NOW(), NOW()),
('Restaurante C', 'restaurante-c', '11223344556679', '+551234567892', 3.5, 3, NOW(), NOW()),
('Restaurante D', 'restaurante-d', '11223344556680', '+551234567893', 3.0, 4, NOW(), NOW()),
('Restaurante E', 'restaurante-e', '11223344556681', '+551234567894', 5.0, 5, NOW(), NOW()),
('Restaurante F', 'restaurante-f', '11223344556682', '+551234567895', 4.7, 6, NOW(), NOW()),
('Restaurante G', 'restaurante-g', '11223344556683', '+551234567896', 4.3, 7, NOW(), NOW()),
('Restaurante H', 'restaurante-h', '11223344556684', '+551234567897', 4.8, 8, NOW(), NOW()),
('Restaurante I', 'restaurante-i', '11223344556685', '+551234567898', 4.2, 9, NOW(), NOW()),
('Restaurante J', 'restaurante-j', '11223344556686', '+551234567899', 4.9, 10, NOW(), NOW());


