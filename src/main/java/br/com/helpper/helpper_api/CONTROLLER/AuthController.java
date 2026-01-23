package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.LoginRequestDTO;
import br.com.helpper.helpper_api.DTO.LoginResponseDTO;
import br.com.helpper.helpper_api.DTO.RegisterRequestDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.ENTITY.Prestador;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email já está em uso!");
        }

        if (usuarioRepository.existsByCpf(request.getCpf())) {
            return ResponseEntity.badRequest().body("CPF já está cadastrado!");
        }

        if (!request.getSenha().equals(request.getConfirmarSenha())) {
            return ResponseEntity.badRequest().body("As senhas não conferem.");
        }

        if (Boolean.FALSE.equals(request.getTermosAceitos())) {
            return ResponseEntity.badRequest().body("Os termos de uso devem ser aceitos.");
        }

        Usuario usuario = switch (request.getTipo()) {
            case PRESTADOR -> new Prestador();
            case CONTRATANTE -> new Contratante();
        };

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setCpf(request.getCpf());

        // Criptografa a senha
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário registrado com sucesso! ID: " + usuarioSalvo.getId());
    }
}
