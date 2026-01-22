package br.com.helpper.helpper_api.CONFIG;

import br.com.helpper.helpper_api.ENTITY.TipoUsuario;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Configuration
@Profile("dev") // só roda no profile dev
public class SeedConfigPrestador {
/*
    @Bean
    CommandLineRunner seedUsuario(UsuarioRepository usuarioRepository,
                                  PasswordEncoder passwordEncoder) {

        return args -> {
            String email = "admin2@teste.com";

            if (!usuarioRepository.existsByEmail(email)) {
                Usuario u = new Usuario();
                u.setNome("Admin2");
                u.setEmail(email);
                u.setSenha(passwordEncoder.encode("123456"));
                u.setTipo(TipoUsuario.PRESTADOR);

                usuarioRepository.save(u);

                System.out.println("Usuário seed criado: " + email + " / senha: 123456");
            }
        };


    }
 */
}
