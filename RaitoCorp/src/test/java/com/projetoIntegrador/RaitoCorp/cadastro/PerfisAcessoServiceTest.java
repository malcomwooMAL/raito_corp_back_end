package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.PerfilAcesso;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.PerfilAcessoRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.PerfisAcessoService;
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
class PerfisAcessoServiceTest {

    @Mock
    private PerfilAcessoRepository repository;

    @InjectMocks
    private PerfisAcessoService service;

    @Test
    void deveListarTodosPerfis() {
        when(repository.findAll()).thenReturn(List.of(new PerfilAcesso()));

        List<PerfilAcesso> resultado = service.listarTodos();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveCriarPerfil() {
        PerfilAcesso perfil = new PerfilAcesso();
        perfil.setNome("ADMIN");

        when(repository.save(any(PerfilAcesso.class))).thenReturn(perfil);

        PerfilAcesso resultado = service.criar(perfil);

        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNome());
    }

    @Test
    void deveAtualizarPerfil() {
        UUID id = UUID.randomUUID();
        PerfilAcesso existente = new PerfilAcesso();
        existente.setIdPerfil(id);
        existente.setNome("USER");

        PerfilAcesso atualizacao = new PerfilAcesso();
        atualizacao.setNome("SUPERUSER");

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(PerfilAcesso.class))).thenReturn(existente);

        PerfilAcesso resultado = service.atualizar(id, atualizacao);

        assertEquals("SUPERUSER", resultado.getNome());
    }

    @Test
    void deveLancarErroAoAtualizarPerfilInexistente() {
        UUID id = UUID.randomUUID();
        PerfilAcesso atualizacao = new PerfilAcesso();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.atualizar(id, atualizacao));
    }

    @Test
    void deveExcluirPerfil() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        service.excluir(id);

        verify(repository, times(1)).deleteById(id);
    }
}
