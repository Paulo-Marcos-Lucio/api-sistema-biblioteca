# api-sistema-biblioteca
API REST de um sistema de biblioteca básico

# Sistema de Biblioteca - API REST

## 🚀 Tecnologias
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL/MySQL
- Flyway
- Maven

## 📋 Funcionalidades
- CRUD de Autores
- CRUD de Livros
- CRUD de Usuários
- Sistema de Empréstimos
- Controle de Estoque
- Validações de Negócio

## 🏗️ Arquitetura
- API REST seguindo padrões RESTful
- Arquitetura em camadas (Controller, Service, Repository)
- DTOs para entrada e saída
- Tratamento global de exceções


## 🔧 Como Rodar
1. Clone o repositório
2. Configure o banco de dados em application.properties
3. Execute: mvn spring-boot:run
4. Acesse: http://localhost:8080

## 📚 Endpoints Principais
- GET    /autores
- POST   /autores
- GET    /livros
- POST   /emprestimos


## 🎯 Aprendizados
Este projeto foi desenvolvido para demonstrar conhecimentos em:
- Relacionamentos JPA (OneToMany, ManyToOne)
- Validações de negócio
- Controle transacional
- Migrations com Flyway
