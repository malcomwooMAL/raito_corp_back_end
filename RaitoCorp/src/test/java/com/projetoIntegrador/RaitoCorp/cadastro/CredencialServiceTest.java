package com.projetoIntegrador.RaitoCorp.cadastro;

import com.projetoIntegrador.RaitoCorp.cadastro.model.Credencial;
import com.projetoIntegrador.RaitoCorp.cadastro.repository.CredencialRepository;
import com.projetoIntegrador.RaitoCorp.cadastro.service.CredencialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CredencialServiceTest {

    @Mock
    private CredencialRepository credencialRepository;

    @InjectMocks
    private CredencialService credencialService;

    @Test
    void deveCriarCredencialComSenhaCriptografada() {
        Credencial credencial = new Credencial();
        credencial.setEmail("teste@exemplo.com");
        credencial.setSenhaHash("123456");

        when(credencialRepository.save(any(Credencial.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Credencial resultado = credencialService.criarCredencial(credencial);

        assertNotNull(resultado.getSenhaHash());
        assertNotEquals("123456", resultado.getSenhaHash()); // A senha deve ser hashada, não plain text
        assertTrue(BCrypt.checkpw("123456", resultado.getSenhaHash()));
        verify(credencialRepository, times(1)).save(any(Credencial.class));
    }

    @Test
    void deveAutenticarCredencialValida() {
        String senhaHash = BCrypt.hashpw("senha123", BCrypt.gensalt());
        Credencial credencial = new Credencial(UUID.randomUUID(), "user@email.com", senhaHash);

        when(credencialRepository.findByEmail("user@email.com")).thenReturn(Optional.of(credencial));

        boolean resultado = credencialService.autenticar("user@email.com", "senha123");

        assertTrue(resultado);
    }

    @Test
    void deveRecusarSenhaInvalida() {
        String senhaHash = BCrypt.hashpw("correta", BCrypt.gensalt());
        Credencial credencial = new Credencial(UUID.randomUUID(), "user@email.com", senhaHash);

        when(credencialRepository.findByEmail("user@email.com")).thenReturn(Optional.of(credencial));

        boolean resultado = credencialService.autenticar("user@email.com", "errada");

        assertFalse(resultado);
    }

    @Test
    void deveRecusarEmailInexistente() {
        when(credencialRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        boolean resultado = credencialService.autenticar("naoexiste@email.com", "qualquer");

        assertFalse(resultado);
    }
}
