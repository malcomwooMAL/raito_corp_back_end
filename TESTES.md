# Documentação de Testes - RaitoCorp

Este documento descreve a estratégia de testes adotada para o projeto RaitoCorp, cobrindo os módulos de Cadastro, Catálogo, Estoque, Vendas e Admin.

## Visão Geral

O projeto utiliza **JUnit 5** e **Mockito** para testes unitários, garantindo que a lógica de negócios nos serviços seja validada isoladamente das dependências externas (como banco de dados). Além disso, testes de integração básicos verificam o carregamento do contexto do Spring Boot.

## Como Executar os Testes

Para executar todos os testes, utilize o comando Maven na raiz do projeto (ou dentro da pasta `RaitoCorp`):

```bash
mvn test
```

## Cobertura por Módulo

### 1. Módulo Cadastro (`com.projetoIntegrador.RaitoCorp.cadastro`)

Responsável pela gestão de usuários, clientes, endereços e perfis.

*   **`UsuarioServiceTest`**:
    *   Criação de usuário.
    *   Busca por ID (existente e inexistente).
    *   Listagem de todos os usuários.
    *   Exclusão de usuário.
*   **`ClienteServiceTest`**:
    *   Criação de cliente com validação de campos (CPF, celular).
    *   Busca por ID, CPF e Usuário vinculado.
*   **`EnderecoServiceTest`**:
    *   Criação de endereços.
    *   Listagem por cliente.
    *   Busca por ID.
*   **`CredencialServiceTest`**:
    *   Criação de credenciais com criptografia de senha (BCrypt).
    *   Autenticação (sucesso, senha incorreta, email inexistente).
*   **`UsuarioPerfilServiceTest`**:
    *   Atribuição e remoção de perfis a usuários.
    *   Listagem de perfis por usuário e usuários por perfil.
*   **`PerfisAcessoServiceTest`**:
    *   CRUD completo de perfis de acesso.

### 2. Módulo Catálogo (`com.projetoIntegrador.RaitoCorp.catalogo`)

Responsável pela gestão de produtos e categorias.

*   **`ProdutoServiceTest`**:
    *   Listagem de produtos (simples e com estoque).
    *   Criação e atualização completa de produtos.
    *   Exclusão de produtos (com remoção de dependências).
    *   Associação de categorias.
    *   Upload de imagem (conversão para Base64).
*   **`CategoriaServiceTest`**:
    *   CRUD de categorias.
    *   Ativação/Inativação de categorias.

### 3. Módulo Estoque (`com.projetoIntegrador.RaitoCorp.estoque`)

Responsável pelo controle de quantidade de produtos.

*   **`EstoqueServiceTest`**:
    *   Atualização de quantidade (entrada/saída).
    *   Adição de novos produtos ao estoque.
    *   Validação de estoque insuficiente (exceções).

### 4. Módulo Vendas (`com.projetoIntegrador.RaitoCorp.vendas`)

Responsável pelo fluxo de compras.

*   **`CarrinhoServiceTest`**:
    *   Criação ou recuperação de carrinho.
    *   Adição de itens (com validação de estoque).
    *   Cálculo de total.
    *   Limpeza do carrinho.
*   **`PedidoServiceTest`**:
    *   **Finalização de Pedido**: Testa o fluxo completo de transformar itens do carrinho em pedido, baixando estoque e limpando o carrinho.
    *   Atualização de status do pedido.
    *   Listagem de pedidos por cliente.

### 5. Módulo Admin (`com.projetoIntegrador.RaitoCorp.admin`)

*   **`AdminServiceTest`**:
    *   Verificação de estrutura básica para lógica administrativa futura.

## Configuração de Testes

Os testes de integração utilizam um banco de dados em memória **H2** para não afetar o banco de dados de desenvolvimento/produção. A configuração encontra-se em `src/test/resources/application.properties`.
