package com.projetoIntegrador.RaitoCorp.catalogo;

import com.projetoIntegrador.RaitoCorp.catalogo.dto.ProdutoComEstoqueDTO;
import com.projetoIntegrador.RaitoCorp.catalogo.model.Categoria;
import com.projetoIntegrador.RaitoCorp.catalogo.model.Produto;
import com.projetoIntegrador.RaitoCorp.catalogo.model.ProdutoCategoria;
import com.projetoIntegrador.RaitoCorp.catalogo.repository.CategoriaRepository;
import com.projetoIntegrador.RaitoCorp.catalogo.repository.ProdutoCategoriaRepository;
import com.projetoIntegrador.RaitoCorp.catalogo.repository.ProdutoRepository;
import com.projetoIntegrador.RaitoCorp.catalogo.service.ProdutoService;
import com.projetoIntegrador.RaitoCorp.estoque.model.Estoque;
import com.projetoIntegrador.RaitoCorp.estoque.repository.EstoqueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoCategoriaRepository produtoCategoriaRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveListarTodosProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(new Produto()));

        List<Produto> resultado = produtoService.listarTodos();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveListarProdutosComEstoque() {
        Produto produto = new Produto();
        produto.setId(UUID.randomUUID());

        Estoque estoque = new Estoque();
        estoque.setQuantidade(10);

        when(produtoRepository.findAll()).thenReturn(List.of(produto));
        when(estoqueRepository.findByIdProduto(produto.getId())).thenReturn(Optional.of(estoque));

        List<ProdutoComEstoqueDTO> resultado = produtoService.listarTodosComEstoque();

        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(0).getQuantidadeEstoque());
    }

    @Test
    void deveCriarProduto() {
        Produto produto = new Produto();
        produto.setNome("Smartphone");

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = produtoService.criarProduto(produto);

        assertNotNull(resultado);
        assertEquals("Smartphone", resultado.getNome());
    }

    @Test
    void deveAtualizarProduto() {
        UUID id = UUID.randomUUID();
        Produto existente = new Produto();
        existente.setId(id);
        existente.setNome("Antigo");

        Produto atualizacao = new Produto();
        atualizacao.setNome("Novo");
        atualizacao.setDescricao("Desc");
        atualizacao.setPreco(BigDecimal.TEN);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(produtoRepository.save(any(Produto.class))).thenReturn(existente);

        Produto resultado = produtoService.atualizarProduto(id, atualizacao);

        assertEquals("Novo", resultado.getNome());
    }

    @Test
    void deveExcluirProduto() {
        UUID id = UUID.randomUUID();
        when(produtoRepository.existsById(id)).thenReturn(true);
        doNothing().when(produtoCategoriaRepository).deleteByProdutoId(id);
        doNothing().when(produtoRepository).deleteById(id);

        produtoService.excluirProduto(id);

        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    void deveAssociarCategoria() {
        UUID idProduto = UUID.randomUUID();
        UUID idCategoria = UUID.randomUUID();

        Produto produto = new Produto();
        produto.setId(idProduto);

        Categoria categoria = new Categoria();
        categoria.setId(idCategoria);

        when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produto));
        when(categoriaRepository.findById(idCategoria)).thenReturn(Optional.of(categoria));
        when(produtoCategoriaRepository.save(any(ProdutoCategoria.class))).thenReturn(new ProdutoCategoria());

        assertDoesNotThrow(() -> produtoService.associarCategoria(idProduto, idCategoria));
        verify(produtoCategoriaRepository, times(1)).save(any(ProdutoCategoria.class));
    }

    @Test
    void deveFazerUploadImagem() throws IOException {
        UUID idProduto = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(idProduto);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn("conteudo".getBytes());
        when(file.getContentType()).thenReturn("image/png");

        when(produtoRepository.findById(idProduto)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto resultado = produtoService.uploadImagem(idProduto, file);

        assertNotNull(resultado.getImagemUrl());
        assertTrue(resultado.getImagemUrl().startsWith("data:image/png;base64,"));
    }
}
