package com.projetoIntegrador.RaitoCorp.vendas;

import com.projetoIntegrador.RaitoCorp.vendas.model.Carrinho;
import com.projetoIntegrador.RaitoCorp.vendas.model.ItemCarrinho;
import com.projetoIntegrador.RaitoCorp.vendas.repository.CarrinhoRepository;
import com.projetoIntegrador.RaitoCorp.vendas.repository.ItemCarrinhoRepository;
import com.projetoIntegrador.RaitoCorp.vendas.service.CarrinhoService;
import com.projetoIntegrador.RaitoCorp.estoque.repository.EstoqueRepository;
import com.projetoIntegrador.RaitoCorp.estoque.model.Estoque;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Test
    void deveObterOuCriarCarrinho() {
        UUID idCliente = UUID.randomUUID();
        Carrinho carrinho = new Carrinho();
        carrinho.setIdCliente(idCliente);

        when(carrinhoRepository.findByIdCliente(idCliente)).thenReturn(Optional.empty());
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho resultado = carrinhoService.obterOuCriarCarrinho(idCliente);

        assertNotNull(resultado);
        assertEquals(idCliente, resultado.getIdCliente());
    }

    @Test
    void deveAdicionarItemAoCarrinho() {
        UUID idCarrinho = UUID.randomUUID();
        UUID idProduto = UUID.randomUUID();
        BigDecimal preco = BigDecimal.TEN;

        Estoque estoque = new Estoque();
        estoque.setQuantidade(100);
        when(estoqueRepository.findByIdProduto(idProduto)).thenReturn(Optional.of(estoque));

        when(itemCarrinhoRepository.findById(any(ItemCarrinho.PK.class))).thenReturn(Optional.empty());
        when(itemCarrinhoRepository.save(any(ItemCarrinho.class))).thenReturn(new ItemCarrinho());

        carrinhoService.adicionarItem(idCarrinho, idProduto, 2, preco);

        verify(itemCarrinhoRepository, times(1)).save(any(ItemCarrinho.class));
    }

    @Test
    void deveCalcularTotal() {
        UUID idCarrinho = UUID.randomUUID();
        ItemCarrinho item1 = new ItemCarrinho();
        item1.setQuantidade(2);
        item1.setPrecoUnitario(BigDecimal.valueOf(10));

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setQuantidade(1);
        item2.setPrecoUnitario(BigDecimal.valueOf(20));

        when(itemCarrinhoRepository.findByIdCarrinho(idCarrinho)).thenReturn(List.of(item1, item2));

        BigDecimal total = carrinhoService.calcularTotal(idCarrinho);

        assertEquals(BigDecimal.valueOf(40), total);
    }

    @Test
    void deveLimparCarrinho() {
        UUID idCarrinho = UUID.randomUUID();
        ItemCarrinho item = new ItemCarrinho();
        when(itemCarrinhoRepository.findByIdCarrinho(idCarrinho)).thenReturn(List.of(item));
        doNothing().when(itemCarrinhoRepository).deleteAll(anyList());

        carrinhoService.limparCarrinho(idCarrinho);

        verify(itemCarrinhoRepository, times(1)).deleteAll(anyList());
    }
}
