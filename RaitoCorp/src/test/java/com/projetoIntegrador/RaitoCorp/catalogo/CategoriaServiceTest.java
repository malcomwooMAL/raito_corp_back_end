package com.projetoIntegrador.RaitoCorp.catalogo;

import com.projetoIntegrador.RaitoCorp.catalogo.model.Categoria;
import com.projetoIntegrador.RaitoCorp.catalogo.repository.CategoriaRepository;
import com.projetoIntegrador.RaitoCorp.catalogo.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveSalvarCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = categoriaService.salvar(categoria);

        assertNotNull(resultado);
        assertEquals("Eletrônicos", resultado.getNome());
        assertTrue(resultado.getAtivo()); // Deve definir ativo como true por padrão
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void deveListarTodasCategorias() {
        when(categoriaRepository.findAll()).thenReturn(List.of(new Categoria()));

        List<Categoria> resultado = categoriaService.listarTodas();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveListarCategoriasAtivas() {
        when(categoriaRepository.findByAtivoTrue()).thenReturn(List.of(new Categoria()));

        List<Categoria> resultado = categoriaService.listarAtivas();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarCategoriaPorId() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria();
        categoria.setId(id);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        Optional<Categoria> resultado = categoriaService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
    }

    @Test
    void deveAtualizarCategoria() {
        UUID id = UUID.randomUUID();
        Categoria existente = new Categoria();
        existente.setId(id);
        existente.setNome("Antigo");

        Categoria atualizacao = new Categoria();
        atualizacao.setNome("Novo");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(existente);

        Categoria resultado = categoriaService.atualizar(id, atualizacao);

        assertEquals("Novo", resultado.getNome());
    }

    @Test
    void deveAtualizarStatusCategoria() {
        UUID id = UUID.randomUUID();
        Categoria existente = new Categoria();
        existente.setId(id);
        existente.setAtivo(true);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(existente);

        Categoria resultado = categoriaService.atualizarStatus(id, false);

        assertFalse(resultado.getAtivo());
    }

    @Test
    void deveDeletarCategoria() {
        UUID id = UUID.randomUUID();
        when(categoriaRepository.existsById(id)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(id);

        categoriaService.deletar(id);

        verify(categoriaRepository, times(1)).deleteById(id);
    }
}
