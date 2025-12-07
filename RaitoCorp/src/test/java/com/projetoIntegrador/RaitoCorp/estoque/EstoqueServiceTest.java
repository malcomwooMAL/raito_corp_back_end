package com.projetoIntegrador.RaitoCorp.estoque;

import com.projetoIntegrador.RaitoCorp.estoque.model.Estoque;
import com.projetoIntegrador.RaitoCorp.estoque.repository.EstoqueRepository;
import com.projetoIntegrador.RaitoCorp.estoque.service.EstoqueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void deveAtualizarQuantidade() {
        UUID idProduto = UUID.randomUUID();
        Estoque estoque = new Estoque();
        estoque.setIdProduto(idProduto);
        estoque.setQuantidade(10);

        when(estoqueRepository.findByIdProduto(idProduto)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        estoqueService.atualizarQuantidade(idProduto, 15);

        assertEquals(15, estoque.getQuantidade());
        verify(estoqueRepository, times(1)).save(estoque);
    }

    @Test
    void deveAdicionarProduto() {
        UUID idProduto = UUID.randomUUID();
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(i -> i.getArguments()[0]);

        Estoque resultado = estoqueService.adicionarProduto(idProduto, 5);

        assertEquals(5, resultado.getQuantidade());
        assertEquals(idProduto, resultado.getIdProduto());
    }

    @Test
    void deveMovimentarSaida() {
        UUID idProduto = UUID.randomUUID();
        Estoque estoque = new Estoque();
        estoque.setIdProduto(idProduto);
        estoque.setQuantidade(10);

        when(estoqueRepository.findByIdProduto(idProduto)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenReturn(estoque);

        estoqueService.movimentarSaida(idProduto, 5);

        assertEquals(5, estoque.getQuantidade());
    }

    @Test
    void deveLancarErroSeEstoqueInsuficiente() {
        UUID idProduto = UUID.randomUUID();
        Estoque estoque = new Estoque();
        estoque.setIdProduto(idProduto);
        estoque.setQuantidade(2);

        when(estoqueRepository.findByIdProduto(idProduto)).thenReturn(Optional.of(estoque));

        assertThrows(RuntimeException.class, () -> estoqueService.movimentarSaida(idProduto, 5));
    }
}
