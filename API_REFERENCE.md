# Referência da API - Raitô Corp

Este documento detalha os endpoints disponíveis na API Raitô Corp.

Base URL: `http://localhost:8081`

## Sumário

1.  [Catálogo](#1-catálogo)
    *   [Produtos](#11-produtos)
    *   [Categorias](#12-categorias)
    *   [Características de Iluminação](#13-características-de-iluminação)
    *   [Imagens de Produtos](#14-imagens-de-produtos)
    *   [Modelos 3D](#15-modelos-3d)
2.  [Estoque](#2-estoque)
3.  [Vendas](#3-vendas)
    *   [Carrinho](#31-carrinho)
    *   [Pedidos](#32-pedidos)
4.  [Cadastro](#4-cadastro)
    *   [Usuários](#41-usuários)
    *   [Credenciais](#42-credenciais)
    *   [Clientes](#43-clientes)
    *   [Endereços](#44-endereços)
    *   [Perfis de Acesso](#45-perfis-de-acesso)
    *   [Usuários ⇄ Perfis](#46-usuários--perfis)

---

## 1. Catálogo

### 1.1 Produtos

**Listar Produtos**
*   `GET /api/produtos`
    *   Parâmetros (Query):
        *   `emDestaque` (opcional, boolean): Se `true`, retorna apenas produtos em destaque.

**Listar Produtos com Estoque**
*   `GET /api/produtos/com-estoque`
    *   Retorna uma lista de DTOs contendo dados do produto e a quantidade em estoque.

**Buscar Produto por ID**
*   `GET /api/produtos/{id}`

**Buscar Produto por ID com Estoque**
*   `GET /api/produtos/{id}/com-estoque`

**Criar Produto**
*   `POST /api/produtos`
    *   Body (JSON):
        ```json
        {
          "nome": "Lâmpada LED 9W",
          "descricao": "Alta eficiência energética",
          "preco": 29.90,
          "ativo": true
        }
        ```

**Atualizar Produto**
*   `PUT /api/produtos/{id}`
    *   Atualiza todos os campos do produto.
    *   Body (JSON): Objeto `Produto` completo.

**Atualizar Produto Parcial**
*   `PATCH /api/produtos/{id}`
    *   Atualiza campos parciais do produto.

**Excluir Produto**
*   `DELETE /api/produtos/{id}`

**Associar Categoria a Produto**
*   `POST /api/produtos/{idProduto}/categorias/{idCategoria}`

**Associar Categoria por Nome**
*   `POST /api/produtos/{idProduto}/categoria-nome/{nomeCategoria}`

**Listar Categorias de um Produto**
*   `GET /api/produtos/{idProduto}/categorias`

**Remover Categoria de um Produto**
*   `DELETE /api/produtos/{idProduto}/categorias/{idCategoria}`

**Upload de Imagem (Arquivo)**
*   `POST /api/produtos/{idProduto}/imagem`
    *   Form-Data:
        *   `imagem`: Arquivo (`.png`, `.jpg`, etc.)

---

### 1.2 Categorias

**Criar Categoria**
*   `POST /api/categorias`
    *   Body (JSON):
        ```json
        {
          "nome": "Luminárias",
          "descricao": "Produtos do tipo luminária"
        }
        ```

**Listar Todas as Categorias**
*   `GET /api/categorias`

**Listar Categorias Ativas**
*   `GET /api/categorias/ativas`

**Buscar Categoria por ID**
*   `GET /api/categorias/{id}`

**Atualizar Categoria**
*   `PUT /api/categorias/{id}`

**Atualizar Status da Categoria**
*   `PUT /api/categorias/{id}/status`
    *   Parâmetros (Query):
        *   `ativo` (boolean)

**Deletar Categoria**
*   `DELETE /api/categorias/{id}`

---

### 1.3 Características de Iluminação

**Adicionar Característica a Produto**
*   `POST /api/iluminacao/produto/{idProduto}`
    *   Parâmetros (Query):
        *   `potencia` (String, required)
        *   `temperaturaCor` (String, required)
        *   `fluxoLuminoso` (String, required)
        *   `tensao` (String, optional)
        *   `eficiencia` (String, optional)
        *   `indiceProtecao` (String, optional)
        *   `regulavel` (boolean, default=false)
    *   Exemplo: `/api/iluminacao/produto/{uuid}?potencia=9W&temperaturaCor=3000K&fluxoLuminoso=900lm`

**Listar por Produto**
*   `GET /api/iluminacao/produto/{idProduto}`

**Buscar por ID**
*   `GET /api/iluminacao/{id}`

**Atualizar Característica**
*   `PUT /api/iluminacao/{id}`
    *   Parâmetros (Query): `potencia`, `temperaturaCor`, `fluxoLuminoso`, `tensao`, `eficiencia`, `indiceProtecao`, `regulavel`.

**Excluir Característica**
*   `DELETE /api/iluminacao/{id}`

---

### 1.4 Imagens de Produtos

Gerenciamento de URLs de imagens (não confundir com upload de arquivo no endpoint de Produtos).

**Listar Imagens por Produto**
*   `GET /api/imagens/produto/{idProduto}`

**Adicionar URL de Imagem**
*   `POST /api/imagens/produto/{idProduto}`
    *   Parâmetros (Query):
        *   `url` (String)
        *   `principal` (boolean, default=false)

**Excluir Imagem**
*   `DELETE /api/imagens/{id}`

---

### 1.5 Modelos 3D

**Listar Modelos por Produto**
*   `GET /api/modelos3d/produto/{idProduto}`

**Adicionar Modelo 3D**
*   `POST /api/modelos3d/produto/{idProduto}`
    *   Parâmetros (Query):
        *   `nomeArquivo`
        *   `url`
        *   `formato`

**Excluir Modelo 3D**
*   `DELETE /api/modelos3d/{id}`

---

## 2. Estoque

Todos os endpoints utilizam Query Parameters.

**Listar Todo Estoque**
*   `GET /api/estoque`

**Buscar Estoque por Produto**
*   `GET /api/estoque/{idProduto}`

**Adicionar Produto ao Estoque**
*   `POST /api/estoque/adicionar`
    *   Query Params: `idProduto`, `quantidade`

**Atualizar Quantidade**
*   `PUT /api/estoque/atualizar`
    *   Query Params: `idProduto`, `quantidade`

**Reservar Produto**
*   `PUT /api/estoque/reservar`
    *   Query Params: `idProduto`, `quantidade`

**Liberar Reserva**
*   `PUT /api/estoque/liberar`
    *   Query Params: `idProduto`, `quantidade`

**Movimentar Saída**
*   `PUT /api/estoque/saida`
    *   Query Params: `idProduto`, `quantidade`

---

## 3. Vendas

### 3.1 Carrinho

**Criar Carrinho (ou obter existente)**
*   `POST /api/carrinho/criar`
    *   Body (JSON):
        ```json
        {
          "idCliente": "UUID"
        }
        ```

**Listar Itens do Carrinho**
*   `GET /api/carrinho/{idCarrinho}/itens`

**Adicionar Item ao Carrinho**
*   `POST /api/carrinho/{idCarrinho}/adicionar`
    *   Body (JSON):
        ```json
        {
          "idProduto": "UUID",
          "quantidade": 2,
          "preco": 59.90
        }
        ```

**Remover Item**
*   `DELETE /api/carrinho/{idCarrinho}/remover/{idProduto}`

**Limpar Carrinho**
*   `DELETE /api/carrinho/{idCarrinho}/limpar`

**Calcular Total**
*   `GET /api/carrinho/{idCarrinho}/total`

### 3.2 Pedidos

**Listar Todos os Pedidos**
*   `GET /api/pedidos`

**Listar Pedidos por Cliente**
*   `GET /api/pedidos/cliente/{idCliente}`

**Buscar Pedido por ID**
*   `GET /api/pedidos/{idPedido}`

**Finalizar Pedido**
*   `POST /api/pedidos/finalizar`
    *   Query Params:
        *   `idCliente`
        *   `idCarrinho`
        *   `idEnderecoEntrega` (opcional)

**Atualizar Status do Pedido**
*   `PUT /api/pedidos/{idPedido}/status`
    *   Body (JSON):
        ```json
        {
          "status": "ENVIADO"
        }
        ```

---

## 4. Cadastro

### 4.1 Usuários

**Criar Usuário**
*   `POST /api/usuarios/criar`
    *   Body (JSON):
        ```json
        {
          "nome": "Emerson",
          "sobrenome": "Araújo",
          "tipoUsuario": "cliente"
        }
        ```

**Listar Usuários**
*   `GET /api/usuarios/listar`

**Buscar Usuário por ID**
*   `GET /api/usuarios/{id}`

**Deletar Usuário**
*   `DELETE /api/usuarios/{id}`

### 4.2 Credenciais

**Criar Credencial**
*   `POST /api/credenciais/criar`
    *   Body (JSON):
        ```json
        {
          "idUsuario": "UUID",
          "email": "teste@teste.com",
          "senhaHash": "123456"
        }
        ```

**Login**
*   `POST /api/credenciais/login`
    *   Body (JSON):
        ```json
        {
          "email": "teste@teste.com",
          "senha": "123456"
        }
        ```

**Buscar por Email**
*   `GET /api/credenciais/email/{email}`

### 4.3 Clientes

**Criar Cliente**
*   `POST /api/clientes/criar`
    *   Body (JSON):
        ```json
        {
          "idUsuario": "UUID",
          "cpf": "12345678901",
          "data_nascimento": "1999-05-10",
          "celular": "62999999999"
        }
        ```

**Listar Clientes**
*   `GET /api/clientes/listar`

**Buscar por ID**
*   `GET /api/clientes/{id}`

**Buscar por CPF**
*   `GET /api/clientes/cpf/{cpf}`

**Listar por Usuário**
*   `GET /api/clientes/usuario/{idUsuario}`

**Deletar Cliente**
*   `DELETE /api/clientes/{id}`

### 4.4 Endereços

**Criar Endereço**
*   `POST /api/enderecos/criar`
    *   Body (JSON):
        ```json
        {
          "idCliente": "UUID",
          "cep": "74000000",
          "rua": "Av. Goiás",
          "numero": "120",
          "complemento": "Qd 05 Lt 10",
          "bairro": "Centro",
          "cidade": "Goiânia",
          "estado": "GO",
          "enderecoPrincipal": true
        }
        ```

**Listar Endereços**
*   `GET /api/enderecos/listar`

**Listar Endereços por Cliente**
*   `GET /api/enderecos/cliente/{idCliente}`

**Buscar por ID**
*   `GET /api/enderecos/{id}`

**Deletar Endereço**
*   `DELETE /api/enderecos/{id}`

### 4.5 Perfis de Acesso

**Listar Perfis**
*   `GET /api/perfis`

**Criar Perfil**
*   `POST /api/perfis`
    *   Body (JSON):
        ```json
        {
          "nome": "ADMIN",
          "descricao": "Acesso total ao sistema"
        }
        ```

**Atualizar Perfil**
*   `PUT /api/perfis/{id}`

**Excluir Perfil**
*   `DELETE /api/perfis/{id}`

### 4.6 Usuários ⇄ Perfis

**Atribuir Perfil a Usuário**
*   `POST /api/usuarios-perfis/atribuir`
    *   Query Params: `idUsuario`, `idPerfil`

**Remover Perfil de Usuário**
*   `DELETE /api/usuarios-perfis/remover`
    *   Query Params: `idUsuario`, `idPerfil`

**Listar Perfis de um Usuário**
*   `GET /api/usuarios-perfis/listar-perfis-usuario`
    *   Query Param: `idUsuario`

**Listar Usuários de um Perfil**
*   `GET /api/usuarios-perfis/listar-usuarios-perfil`
    *   Query Param: `idPerfil`

**Listar Todos os Vínculos**
*   `GET /api/usuarios-perfis/listar-todos`
