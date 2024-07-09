# Projeto Desafio - Documentação de Instalação e Uso

Este documento fornece instruções detalhadas sobre como configurar e executar a aplicação Spring Boot do Projeto Desafio.

## Pré-requisitos

Antes de iniciar, certifique-se de ter o seguinte software instalado em sua máquina:

- Java JDK 17 ou superior
- Maven 3.6 ou superior

## Como Rodar a Aplicação

Para iniciar a aplicação, siga os passos abaixo:

1. Abra um terminal na raiz do projeto.
2. Execute o seguinte comando para compilar a aplicação e iniciar o servidor:

   ```bash
   mvn spring-boot:run
A aplicação será acessível via http://localhost:8080.

Acessando a Documentação da API
A documentação da API, gerada pelo Swagger, pode ser acessada após iniciar a aplicação. Para visualizar a documentação, visite:

Swagger UI

Esta página detalha todos os endpoints disponíveis, parâmetros, e respostas esperadas, facilitando o teste e integração com a API.

Acessando o Banco de Dados H2 em Memória
A aplicação utiliza um banco de dados H2 em memória para desenvolvimento e testes. Para acessar o console do H2 e gerenciar os dados diretamente, siga os passos abaixo:

Certifique-se de que a aplicação esteja rodando.

Acesse o console do H2 via navegador através do endereço:

H2 Console

Conecte-se ao banco de dados utilizando as seguintes credenciais:

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password: password


Após inserir as informações, clique em "Connect" para acessar o banco de dados.