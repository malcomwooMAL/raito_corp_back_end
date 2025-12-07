package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.Usuario;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.UsuarioRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioPadrao;

    @BeforeEach
    void setUp() {
        usuarioPadrao = new Usuario();
        usuarioPadrao.setIdUsuario(UUID.randomUUID());
        usuarioPadrao.setNome("Lucas");
        usuarioPadrao.setSobrenome("Pereira");
        usuarioPadrao.setTipoUsuario("administrador");
    }

    @Test
    void deveCriarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioPadrao);

        Usuario salvo = usuarioService.criarUsuario(usuarioPadrao);

        assertNotNull(salvo);
        assertEquals("Lucas", salvo.getNome());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioRepository.findById(usuarioPadrao.getIdUsuario())).thenReturn(Optional.of(usuarioPadrao));

        Optional<Usuario> resultado = usuarioService.buscarPorId(usuarioPadrao.getIdUsuario());

        assertTrue(resultado.isPresent());
        assertEquals("Lucas", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioAoBuscarIdInexistente() {
        UUID idInexistente = UUID.randomUUID();
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorId(idInexistente);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveListarTodosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioPadrao));

        List<Usuario> lista = usuarioService.listarUsuarios();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("Lucas", lista.get(0).getNome());
    }

    @Test
    void deveDeletarUsuario() {
        UUID id = usuarioPadrao.getIdUsuario();
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.deletarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
