# Diagramas de Classes

Este documento contém os diagramas de classes do projeto RaitoCorp, organizados por módulo.

## Módulo Admin

```mermaid
classDiagram
    class AdminController {
        -adminService: AdminService
        +listarProdutosAdmin(): ResponseEntity~List~ProdutoAdminDTO~~
        +listarPedidosAdmin(): ResponseEntity~List~PedidoAdminDTO~~
    }

    class AdminService {
        -produtoRepository: ProdutoRepository
        -estoqueRepository: EstoqueRepository
        -itemPedidoRepository: ItemPedidoRepository
        -pedidoRepository: PedidoRepository
        -usuarioRepository: UsuarioRepository
        -credencialService: CredencialService
        -produtoService: ProdutoService
        +listarProdutosComMetricas(): List~ProdutoAdminDTO~
        +listarPedidosComClientes(): List~PedidoAdminDTO~
    }

    AdminController --> AdminService
```

## Módulo Cadastro

```mermaid
classDiagram
    class Usuario {
        -idUsuario: UUID
        -nome: String
        -sobrenome: String
        -tipoUsuario: String
        -ativo: boolean
        -criadoEm: LocalDateTime
        +getIdUsuario(): UUID
        +setIdUsuario(UUID)
        +getNome(): String
        +setNome(String)
        +getSobrenome(): String
        +setSobrenome(String)
        +getTipoUsuario(): String
        +setTipoUsuario(String)
        +isAtivo(): boolean
        +setAtivo(boolean)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
    }

    class Cliente {
        -idCliente: UUID
        -idUsuario: UUID
        -cpf: String
        -data_nascimento: Date
        -celular: String
        -criadoEm: LocalDateTime
        +getIdCliente(): UUID
        +setIdCliente(UUID)
        +getIdUsuario(): UUID
        +setIdUsuario(UUID)
        +getCpf(): String
        +setCpf(String)
        +getData_nascimento(): Date
        +setData_nascimento(Date)
        +getCelular(): String
        +setCelular(String)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
    }

    class Endereco {
        -idEndereco: UUID
        -idCliente: UUID
        -cep: String
        -rua: String
        -numero: String
        -complemento: String
        -bairro: String
        -cidade: String
        -estado: String
        -tipoEndereco: String
        -enderecoPrincipal: boolean
        -criadoEm: LocalDateTime
        +getIdEndereco(): UUID
        +setIdEndereco(UUID)
        +getIdCliente(): UUID
        +setIdCliente(UUID)
        +getCep(): String
        +setCep(String)
        +getRua(): String
        +setRua(String)
        +getNumero(): String
        +setNumero(String)
        +getComplemento(): String
        +setComplemento(String)
        +getBairro(): String
        +setBairro(String)
        +getCidade(): String
        +setCidade(String)
        +getEstado(): String
        +setEstado(String)
        +getTipoEndereco(): String
        +setTipoEndereco(String)
        +isEnderecoPrincipal(): boolean
        +setEnderecoPrincipal(boolean)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
    }

    class Credencial {
        -idCredencial: UUID
        -idUsuario: UUID
        -email: String
        -senhaHash: String
        -tentativasLogin: int
        -bloqueado: boolean
        -ultimoLogin: LocalDateTime
        -criadoEm: LocalDateTime
        +getIdCredencial(): UUID
        +setIdCredencial(UUID)
        +getIdUsuario(): UUID
        +setIdUsuario(UUID)
        +getEmail(): String
        +setEmail(String)
        +getSenhaHash(): String
        +setSenhaHash(String)
        +getTentativasLogin(): int
        +setTentativasLogin(int)
        +isBloqueado(): boolean
        +setBloqueado(boolean)
        +getUltimoLogin(): LocalDateTime
        +setUltimoLogin(LocalDateTime)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
    }

    class PerfilAcesso {
        -idPerfil: UUID
        -nome: String
        -descricao: String
        +getIdPerfil(): UUID
        +setIdPerfil(UUID)
        +getNome(): String
        +setNome(String)
        +getDescricao(): String
        +setDescricao(String)
    }

    class UsuariosPerfis {
        -id: UsuariosPerfisId
        +getId(): UsuariosPerfisId
        +setId(UsuariosPerfisId)
        +setIdUsuario(UUID)
        +setIdPerfil(UUID)
        +getIdUsuario(): UUID
        +getIdPerfil(): UUID
    }

    class UsuariosPerfisId {
        -idUsuario: UUID
        -idPerfil: UUID
        +getIdUsuario(): UUID
        +setIdUsuario(UUID)
        +getIdPerfil(): UUID
        +setIdPerfil(UUID)
        +equals(Object): boolean
        +hashCode(): int
    }

    class UsuarioController {
        -usuarioService: UsuarioService
        +criarUsuario(Usuario): ResponseEntity~Usuario~
        +listarUsuarios(): List~Usuario~
        +buscarPorId(UUID): ResponseEntity~Usuario~
        +deletar(UUID): ResponseEntity~String~
    }

    class UsuarioService {
        -usuarioRepo: UsuarioRepository
        +criarUsuario(Usuario): Usuario
        +listarUsuarios(): List~Usuario~
        +buscarPorId(UUID): Optional~Usuario~
        +deletarUsuario(UUID): void
    }

    class UsuarioRepository {
        <<interface>>
    }

    UsuarioController --> UsuarioService
    UsuarioService --> UsuarioRepository
    UsuarioService ..> Usuario : uses
    UsuariosPerfis *-- UsuariosPerfisId
```

## Módulo Catálogo

```mermaid
classDiagram
    class Produto {
        -id: UUID
        -nome: String
        -descricao: String
        -preco: BigDecimal
        -ativo: Boolean
        -emDestaque: Boolean
        -isNovidade: Boolean
        -isPromocao: Boolean
        -precoOriginal: BigDecimal
        -imagemUrl: String
        +getId(): UUID
        +setId(UUID)
        +getNome(): String
        +setNome(String)
        +getDescricao(): String
        +setDescricao(String)
        +getPreco(): BigDecimal
        +setPreco(BigDecimal)
        +isAtivo(): Boolean
        +setAtivo(Boolean)
        +isEmDestaque(): Boolean
        +setEmDestaque(Boolean)
        +getImagemUrl(): String
        +setImagemUrl(String)
        +isNovidade(): Boolean
        +setNovidade(Boolean)
        +isPromocao(): Boolean
        +setPromocao(Boolean)
        +getPrecoOriginal(): BigDecimal
        +setPrecoOriginal(BigDecimal)
    }

    class Categoria {
        -id: UUID
        -nome: String
        -descricao: String
        -ativo: Boolean
        +getId(): UUID
        +setId(UUID)
        +getNome(): String
        +setNome(String)
        +getDescricao(): String
        +setDescricao(String)
        +getAtivo(): Boolean
        +setAtivo(Boolean)
    }

    class ProdutoCategoria {
        -id: ProdutoCategoriaId
        -produto: Produto
        -categoria: Categoria
        +getId(): ProdutoCategoriaId
        +setId(ProdutoCategoriaId)
        +getProduto(): Produto
        +setProduto(Produto)
        +getCategoria(): Categoria
        +setCategoria(Categoria)
    }

    class ProdutoCategoriaId {
        -produtoId: UUID
        -categoriaId: UUID
        +getProdutoId(): UUID
        +setProdutoId(UUID)
        +getCategoriaId(): UUID
        +setCategoriaId(UUID)
    }

    class ImagemProduto {
        -id: UUID
        -produto: Produto
        -url: String
        -principal: boolean
        +getId(): UUID
        +getProduto(): Produto
        +setProduto(Produto)
        +getUrl(): String
        +setUrl(String)
        +isPrincipal(): boolean
        +setPrincipal(boolean)
    }

    class CaracteristicaIluminacao {
        -id: UUID
        -produto: Produto
        -potencia: String
        -temperaturaCor: String
        -fluxoLuminoso: String
        -tensao: String
        -eficiencia: String
        -indiceProtecao: String
        -regulavel: boolean
        +getId(): UUID
        +setId(UUID)
        +getProduto(): Produto
        +setProduto(Produto)
        +getPotencia(): String
        +setPotencia(String)
        +getTemperaturaCor(): String
        +setTemperaturaCor(String)
        +getFluxoLuminoso(): String
        +setFluxoLuminoso(String)
        +getTensao(): String
        +setTensao(String)
        +getEficiencia(): String
        +setEficiencia(String)
        +getIndiceProtecao(): String
        +setIndiceProtecao(String)
        +isRegulavel(): boolean
        +setRegulavel(boolean)
    }

    class Modelo3D {
        -id: UUID
        -produto: Produto
        -nomeArquivo: String
        -url: String
        -formato: String
        +getId(): UUID
        +getProduto(): Produto
        +setProduto(Produto)
        +getNomeArquivo(): String
        +setNomeArquivo(String)
        +getUrl(): String
        +setUrl(String)
        +getFormato(): String
        +setFormato(String)
    }

    ProdutoCategoria *-- ProdutoCategoriaId
    ProdutoCategoria --> Produto
    ProdutoCategoria --> Categoria
    ImagemProduto --> Produto
    CaracteristicaIluminacao --> Produto
    Modelo3D --> Produto
```

## Módulo Estoque

```mermaid
classDiagram
    class Estoque {
        -idEstoque: UUID
        -idProduto: UUID
        -quantidade: Integer
        -reservado: Integer
        -atualizadoEm: LocalDateTime
        +getIdEstoque(): UUID
        +setIdEstoque(UUID)
        +getIdProduto(): UUID
        +setIdProduto(UUID)
        +getQuantidade(): Integer
        +setQuantidade(Integer)
        +getReservado(): Integer
        +setReservado(Integer)
        +getAtualizadoEm(): LocalDateTime
        +setAtualizadoEm(LocalDateTime)
    }
```

## Módulo Vendas

```mermaid
classDiagram
    class Carrinho {
        -idCarrinho: UUID
        -idCliente: UUID
        -criadoEm: LocalDateTime
        -itens: List~ItemCarrinho~
        +getIdCarrinho(): UUID
        +setIdCarrinho(UUID)
        +getIdCliente(): UUID
        +setIdCliente(UUID)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
        +getItens(): List~ItemCarrinho~
        +setItens(List~ItemCarrinho~)
    }

    class ItemCarrinho {
        -idCarrinho: UUID
        -idProduto: UUID
        -quantidade: Integer
        -precoUnitario: BigDecimal
        -carrinho: Carrinho
        +getIdCarrinho(): UUID
        +setIdCarrinho(UUID)
        +getIdProduto(): UUID
        +setIdProduto(UUID)
        +getQuantidade(): Integer
        +setQuantidade(Integer)
        +getPrecoUnitario(): BigDecimal
        +setPrecoUnitario(BigDecimal)
    }

    class ItemCarrinhoPK {
        +idCarrinho: UUID
        +idProduto: UUID
        +hashCode(): int
        +equals(Object): boolean
    }

    class Pedido {
        -idPedido: UUID
        -idCliente: UUID
        -idEnderecoEntrega: UUID
        -valorTotal: BigDecimal
        -status: String
        -criadoEm: LocalDateTime
        -itens: List~ItemPedido~
        +getIdPedido(): UUID
        +setIdPedido(UUID)
        +getIdCliente(): UUID
        +setIdCliente(UUID)
        +getIdEnderecoEntrega(): UUID
        +setIdEnderecoEntrega(UUID)
        +getValorTotal(): BigDecimal
        +setValorTotal(BigDecimal)
        +getStatus(): String
        +setStatus(String)
        +getCriadoEm(): LocalDateTime
        +setCriadoEm(LocalDateTime)
        +getItens(): List~ItemPedido~
        +setItens(List~ItemPedido~)
    }

    class ItemPedido {
        -idPedido: UUID
        -idProduto: UUID
        -quantidade: Integer
        -precoUnitario: BigDecimal
        -pedido: Pedido
        +getIdPedido(): UUID
        +setIdPedido(UUID)
        +getIdProduto(): UUID
        +setIdProduto(UUID)
        +getQuantidade(): Integer
        +setQuantidade(Integer)
        +getPrecoUnitario(): BigDecimal
        +setPrecoUnitario(BigDecimal)
    }

    class ItemPedidoPK {
        +idPedido: UUID
        +idProduto: UUID
        +hashCode(): int
        +equals(Object): boolean
    }

    Carrinho "1" *-- "*" ItemCarrinho
    ItemCarrinho --> Carrinho
    ItemCarrinho ..> ItemCarrinhoPK : uses
    Pedido "1" *-- "*" ItemPedido
    ItemPedido --> Pedido
    ItemPedido ..> ItemPedidoPK : uses
```
