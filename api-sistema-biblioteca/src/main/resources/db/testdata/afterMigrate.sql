-- ================================================
-- DESABILITAR CONSTRAINTS PARA LIMPEZA
-- ================================================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE tbitem_emprestimo;
TRUNCATE TABLE tbemprestimo;
TRUNCATE TABLE tblivro;
TRUNCATE TABLE tbusuario;
TRUNCATE TABLE tbautor;

SET FOREIGN_KEY_CHECKS = 1;

-- ================================================
-- POPULAÇÃO DE AUTORES
-- ================================================
INSERT INTO tbautor (nome) VALUES
('Machado de Assis'),
('Clarice Lispector'),
('Jorge Amado'),
('Graciliano Ramos'),
('José Saramago');

-- ================================================
-- POPULAÇÃO DE USUÁRIOS
-- ================================================
INSERT INTO tbusuario (nome, email) VALUES
('Carlos Silva', 'carlos.silva@email.com'),
('Fernanda Souza', 'fernanda.souza@email.com'),
('Lucas Pereira', 'lucas.pereira@email.com'),
('Juliana Rocha', 'juliana.rocha@email.com'),
('Rafael Gomes', 'rafael.gomes@email.com');

-- ================================================
-- POPULAÇÃO DE LIVROS
-- ================================================
INSERT INTO tblivro (titulo, isbn, estoque, autor_id) VALUES
('Dom Casmurro', 'ISBN-001', 10, 1),
('A Hora da Estrela', 'ISBN-002', 5, 2),
('Capitães da Areia', 'ISBN-003', 7, 3),
('Vidas Secas', 'ISBN-004', 3, 4),
('Ensaio sobre a Cegueira', 'ISBN-005', 8, 5);

-- ================================================
-- POPULAÇÃO DE EMPRÉSTIMOS
-- ================================================
INSERT INTO tbemprestimo (usuario_id, data_criacao, ativo) VALUES
(1, NOW() - INTERVAL 10 DAY, FALSE),
(2, NOW() - INTERVAL 5 DAY, TRUE),
(3, NOW() - INTERVAL 7 DAY, TRUE),
(4, NOW() - INTERVAL 15 DAY, FALSE),
(5, NOW(), TRUE);

-- ================================================
-- POPULAÇÃO DE ITENS DE EMPRÉSTIMOS
-- ================================================
INSERT INTO tbitem_emprestimo (emprestimo_id, livro_id, quantidade) VALUES
(1, 1, 1),
(2, 2, 1),
(2, 3, 2),
(3, 4, 1),
(4, 5, 1);
