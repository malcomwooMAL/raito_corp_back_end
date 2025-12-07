package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.Endereco;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.EnderecoRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.EnderecoService;
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
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    void deveCriarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setRua("Praça da Sé");

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        Endereco resultado = enderecoService.criarEndereco(endereco);

        assertNotNull(resultado);
        assertEquals("01001-000", resultado.getCep());
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    void deveListarTodosEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(List.of(new Endereco()));

        List<Endereco> resultado = enderecoService.listarEnderecos();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveListarEnderecosPorCliente() {
        UUID idCliente = UUID.randomUUID();
        Endereco endereco = new Endereco();
        endereco.setIdCliente(idCliente);

        when(enderecoRepository.findByIdCliente(idCliente)).thenReturn(List.of(endereco));

        List<Endereco> resultado = enderecoService.listarPorCliente(idCliente);

        assertEquals(1, resultado.size());
        assertEquals(idCliente, resultado.get(0).getIdCliente());
    }

    @Test
    void deveBuscarEnderecoPorId() {
        UUID id = UUID.randomUUID();
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(id);

        when(enderecoRepository.findById(id)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoService.buscarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getIdEndereco());
    }

    @Test
    void deveDeletarEndereco() {
        UUID id = UUID.randomUUID();
        doNothing().when(enderecoRepository).deleteById(id);

        enderecoService.deletarEndereco(id);

        verify(enderecoRepository, times(1)).deleteById(id);
    }
}
