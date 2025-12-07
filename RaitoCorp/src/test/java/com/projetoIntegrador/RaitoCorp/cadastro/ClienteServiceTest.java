package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.Cliente;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.ClienteRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.ClienteService;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveCriarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setCelular("11987654321");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.criarCliente(cliente);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCpf());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void deveBuscarClientePorId() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getIdCliente());
    }

    @Test
    void deveBuscarClientePorCpf() {
        String cpf = "12345678900";
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);

        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarPorCpf(cpf);

        assertTrue(resultado.isPresent());
        assertEquals(cpf, resultado.get().getCpf());
    }

    @Test
    void deveBuscarClientesPorUsuario() {
        UUID idUsuario = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setIdUsuario(idUsuario);

        when(clienteRepository.findByIdUsuario(idUsuario)).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.buscarPorUsuario(idUsuario);

        assertEquals(1, resultado.size());
        assertEquals(idUsuario, resultado.get(0).getIdUsuario());
    }

    @Test
    void deveListarTodosClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente()));

        List<Cliente> resultado = clienteService.listarClientes();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveDeletarCliente() {
        UUID id = UUID.randomUUID();
        doNothing().when(clienteRepository).deleteById(id);

        clienteService.deletarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }
}
