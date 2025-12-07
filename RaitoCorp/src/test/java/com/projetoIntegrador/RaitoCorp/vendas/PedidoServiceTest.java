package com.projetoIntegrador.RaitoCorp.vendas;

import com.projetoIntegrador.RaitoCorp.estoque.service.EstoqueService;
import com.projetoIntegrador.RaitoCorp.vendas.model.ItemCarrinho;
import com.projetoIntegrador.RaitoCorp.vendas.model.Pedido;
import com.projetoIntegrador.RaitoCorp.vendas.repository.ItemPedidoRepository;
import com.projetoIntegrador.RaitoCorp.vendas.repository.PedidoRepository;
import com.projetoIntegrador.RaitoCorp.vendas.service.CarrinhoService;
import com.projetoIntegrador.RaitoCorp.vendas.service.PedidoService;
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
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private CarrinhoService carrinhoService;

    @Mock
    private EstoqueService estoqueService;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveFinalizarPedido() {
        UUID idCliente = UUID.randomUUID();
        UUID idCarrinho = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();
        UUID idProduto = UUID.randomUUID();

        ItemCarrinho item = new ItemCarrinho();
        item.setIdProduto(idProduto);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.TEN);

        when(carrinhoService.calcularTotal(idCarrinho)).thenReturn(BigDecimal.valueOf(20));
        when(carrinhoService.listarItens(idCarrinho)).thenReturn(List.of(item));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> {
            Pedido p = i.getArgument(0);
            p.setIdPedido(UUID.randomUUID());
            return p;
        });

        Pedido resultado = pedidoService.finalizarPedido(idCliente, idCarrinho, idEndereco);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(20), resultado.getValorTotal());
        assertEquals("PENDENTE", resultado.getStatus());

        verify(itemPedidoRepository, times(1)).save(any());
        verify(estoqueService, times(1)).movimentarSaida(idProduto, 2);
        verify(carrinhoService, times(1)).limparCarrinho(idCarrinho);
    }

    @Test
    void deveAtualizarStatus() {
        UUID id = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        pedido.setStatus("PENDENTE");

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.atualizarStatus(id, "ENVIADO");

        assertEquals("ENVIADO", resultado.getStatus());
    }

    @Test
    void deveListarTodos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido()));
        assertFalse(pedidoService.listarTodos().isEmpty());
    }

    @Test
    void deveListarPorCliente() {
        UUID idCliente = UUID.randomUUID();
        when(pedidoRepository.findByIdCliente(idCliente)).thenReturn(List.of(new Pedido()));
        assertFalse(pedidoService.listarPorCliente(idCliente).isEmpty());
    }
}
