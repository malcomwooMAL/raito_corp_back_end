package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.PerfilAcesso;
import com.projetoIntegrador.RaitoCorp.cadastro.model.Usuario;
import com.projetoIntegrador.RaitoCorp.cadastro.model.UsuariosPerfis;
import com.projetoIntegrador.RaitoCorp.cadastro.model.UsuariosPerfisId;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.PerfilAcessoRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.UsuarioRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.UsuariosPerfisRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.UsuarioPerfilService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioPerfilServiceTest {

    @Mock
    private UsuariosPerfisRepository usuariosPerfisRepository;

    @Mock
    private PerfilAcessoRepository perfilAcessoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioPerfilService service;

    @Test
    void deveAtribuirPerfilAoUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID idPerfil = UUID.randomUUID();

        UsuariosPerfisId id = new UsuariosPerfisId(idUsuario, idPerfil);
        UsuariosPerfis vinculo = new UsuariosPerfis();
        vinculo.setId(id);

        when(usuariosPerfisRepository.save(any(UsuariosPerfis.class))).thenReturn(vinculo);

        UsuariosPerfis resultado = service.atribuirPerfil(idUsuario, idPerfil);

        assertNotNull(resultado);
        assertEquals(idUsuario, resultado.getId().getIdUsuario());
        assertEquals(idPerfil, resultado.getId().getIdPerfil());
        verify(usuariosPerfisRepository, times(1)).save(any(UsuariosPerfis.class));
    }

    @Test
    void deveRemoverPerfilDoUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID idPerfil = UUID.randomUUID();

        service.removerPerfil(idUsuario, idPerfil);

        verify(usuariosPerfisRepository, times(1)).deleteById(any(UsuariosPerfisId.class));
    }

    @Test
    void deveListarPerfisDeUmUsuario() {
        UUID idUsuario = UUID.randomUUID();
        UUID idPerfil = UUID.randomUUID();

        UsuariosPerfis up = new UsuariosPerfis();
        up.setId(new UsuariosPerfisId(idUsuario, idPerfil));

        when(usuariosPerfisRepository.findAll()).thenReturn(List.of(up));

        PerfilAcesso perfil = new PerfilAcesso();
        perfil.setIdPerfil(idPerfil);
        perfil.setNome("ADMIN");
        perfil.setDescricao("Administrador do sistema");

        when(perfilAcessoRepository.findAllById(List.of(idPerfil))).thenReturn(List.of(perfil));

        List<PerfilAcesso> perfis = service.listarPerfisDetalhadosPorUsuario(idUsuario);

        assertEquals(1, perfis.size());
        assertEquals("ADMIN", perfis.get(0).getNome());
    }

    @Test
    void deveListarUsuariosPorPerfil() {
        UUID idPerfil = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();

        UsuariosPerfis up = new UsuariosPerfis();
        up.setId(new UsuariosPerfisId(idUsuario, idPerfil));

        when(usuariosPerfisRepository.findAll()).thenReturn(List.of(up));

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Emerson");

        when(usuarioRepository.findAllById(List.of(idUsuario))).thenReturn(List.of(usuario));

        List<Usuario> usuarios = service.listarUsuariosPorPerfil(idPerfil);

        assertEquals(1, usuarios.size());
        assertEquals("Emerson", usuarios.get(0).getNome());
    }
}
