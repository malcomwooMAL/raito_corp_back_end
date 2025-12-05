# Raitô Corp API

Bem-vindo ao repositório oficial da API **Raitô Corp**. Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciar o ecossistema de e-commerce da Raitô Corp, incluindo catálogo de produtos, estoque, vendas e cadastro de usuários.

## 📋 Sumário
- [Descrição](#-descrição)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Configuração](#-instalação-e-configuração)
- [Como Rodar](#-como-rodar)
- [Documentação da API](#-documentação-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)

---

## 📖 Descrição

O sistema Raitô Corp é uma solução robusta para gerenciamento de lojas virtuais, focada em produtos de iluminação. A API fornece serviços para:

*   **Catálogo**: Gestão de produtos, categorias, características técnicas (iluminação), imagens e modelos 3D.
*   **Estoque**: Controle de entrada, saída, reserva e atualização de inventário.
*   **Vendas**: Gerenciamento de carrinhos de compras e processamento de pedidos.
*   **Cadastro**: Gestão completa de usuários, clientes, endereços e perfis de acesso (RBAC).

## 🚀 Tecnologias Utilizadas

*   **Java 21**: Linguagem de programação.
*   **Spring Boot 3.5.6**: Framework principal.
*   **Spring Data JPA**: Camada de persistência.
*   **PostgreSQL**: Banco de dados relacional.
*   **Maven**: Gerenciador de dependências e build.
*   **Docker**: Containerização (opcional, mas recomendado).

## 🛠 Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

*   [Java JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
*   [Maven](https://maven.apache.org/download.cgi)
*   [PostgreSQL](https://www.postgresql.org/download/) (ou Docker para rodar o banco em container)

## ⚙️ Instalação e Configuração

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/RaitoCorp.git
    cd RaitoCorp
    ```

2.  **Configuração do Banco de Dados:**
    Crie um banco de dados PostgreSQL chamado `raito_db`.

    Se estiver usando Docker, você pode subir um container rapidamente:
    ```bash
    docker run --name raito-postgres -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=raito_db -p 5432:5432 -d postgres
    ```

3.  **Configuração da Aplicação:**
    Verifique o arquivo `src/main/resources/application.properties`. As configurações padrão são:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/raito_db
    spring.datasource.username=postgres
    spring.datasource.password=12345
    server.port=8081
    ```
    Ajuste conforme necessário para o seu ambiente.

## ▶️ Como Rodar

Para executar a aplicação via terminal usando Maven:

```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação estará acessível em `http://localhost:8081`.

## 📚 Documentação da API

A documentação detalhada de todos os endpoints, incluindo parâmetros e exemplos de requisição/resposta, foi movida para um arquivo dedicado para manter este README limpo.

👉 **[Acesse a Referência da API (API_REFERENCE.md)](API_REFERENCE.md)**

## 📂 Estrutura do Projeto

A arquitetura do projeto segue uma divisão modular dentro do pacote `com.projetoIntegrador.RaitoCorp`:

*   `admin`: Funcionalidades administrativas.
*   `cadastro`: Gestão de Usuários, Clientes, Endereços, Credenciais e Perfis.
*   `catalogo`: Produtos, Categorias, Imagens, Características de Iluminação e Modelos 3D.
*   `estoque`: Controle de inventário.
*   `vendas`: Carrinho de compras e Pedidos.

Cada módulo possui suas camadas de `controller`, `service`, `repository`, `model` e `dto`.

---
Desenvolvido pela equipe Raitô Corp.
