package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.LoginRequestDTO;
import br.com.helpper.helpper_api.DTO.LoginResponseDTO;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import br.com.helpper.helpper_api.SECURITY.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(userPrincipal);

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(new LoginResponseDTO(
                jwt,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getClass().getSimpleName()
        ));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Email já está em uso!");
        }

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            return ResponseEntity.badRequest().body("CPF já está cadastrado!");
        }

        // Criptografa a senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso! ID: " + usuarioSalvo.getId());
    }
}