CREATE TABLE tbautor (
	
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(255) NOT NULL
);


CREATE TABLE tbusuario (
	
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE tblivro (
	
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	titulo VARCHAR(255) NOT NULL,
	isbn VARCHAR(255) NOT NULL,
	estoque INT NOT NULL,
	autor_id BIGINT NOT NULL,
	CONSTRAINT fk_livro_tbautor FOREIGN KEY (autor_id) REFERENCES tbautor(id)
);

CREATE TABLE tbemprestimo (
	
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	usuario_id BIGINT NOT NULL,
	data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ativo BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT fk_emprestimo_tbusuario FOREIGN KEY (usuario_id) REFERENCES tbusuario(id)
	
);

CREATE TABLE tbitem_emprestimo (
	
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	emprestimo_id BIGINT NOT NULL,
	livro_id BIGINT NOT NULL,
	quantidade INT NOT NULL,
	CONSTRAINT fk_itememprestimo_tbemprestimo FOREIGN KEY (emprestimo_id) REFERENCES tbemprestimo (id),
	CONSTRAINT fk_tbitememprestimo_tblivro FOREIGN KEY (livro_id) REFERENCES tblivro (id)
);