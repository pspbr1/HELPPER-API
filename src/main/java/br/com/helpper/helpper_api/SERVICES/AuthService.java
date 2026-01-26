/*
 * AuthService.java
 * Serviço responsável por autenticação e registro de usuários
 * Gerencia login, registro e geração de tokens JWT
 * Utiliza BCrypt para hash de senhas e validações de segurança
 */
package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.CONFIG.CriptoService;
import br.com.helpper.helpper_api.DTO.LoginRequestDTO;
import br.com.helpper.helpper_api.DTO.LoginResponseDTO;
import br.com.helpper.helpper_api.DTO.RegisterRequestDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.ENTITY.TipoUsuario;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import br.com.helpper.helpper_api.SECURITY.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CriptoService criptoService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager,
            CriptoService criptoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.criptoService = criptoService;
    }

    /**
     * Registra novo usuário (Contratante ou Prestador)
     * @param dto Dados do registro
     * @return Resposta com token JWT
     */
    @Transactional
    public LoginResponseDTO registrar(RegisterRequestDTO dto) {
        logger.info("Iniciando registro de novo usuário: {}", dto.getEmail());

        // Validações
        if (!dto.senhasConferem()) {
            logger.warn("Falha no registro: Senhas não conferem para o email {}", dto.getEmail());
            throw new RuntimeException("As senhas não conferem");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            logger.warn("Falha no registro: Email {} já cadastrado", dto.getEmail());
            throw new RuntimeException("Email já cadastrado");
        }

        // Cria usuário baseado no tipo
        Usuario usuario;
        if (dto.getTipo() == TipoUsuario.CONTRATANTE) {
            usuario = new Contratante();
        } else if (dto.getTipo() == TipoUsuario.PRESTADOR) {
            usuario = new Prestador();
        } else {
            logger.error("Falha no registro: Tipo de usuário inválido {}", dto.getTipo());
            throw new RuntimeException("Tipo de usuário inválido");
        }

        // Preenche dados comuns
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCpf(criptoService.encrypt(dto.getCpf()));

        // Salva no banco
        try {
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            logger.info("Usuário salvo com sucesso. ID: {}, Tipo: {}", usuarioSalvo.getId(), dto.getTipo());

            // Gera token JWT
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(usuarioSalvo.getEmail())
                    .password(usuarioSalvo.getSenha())
                    .authorities("ROLE_" + usuarioSalvo.getRoleName())
                    .build();

            String token = jwtTokenProvider.generateToken(userDetails);

            // Retorna resposta
            return new LoginResponseDTO(
                    token,
                    usuarioSalvo.getId(),
                    usuarioSalvo.getEmail(),
                    usuarioSalvo.getNome(),
                    usuarioSalvo.getRoleName()
            );
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário no banco de dados: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar o registro do usuário");
        }
    }

    /**
     * Realiza login do usuário
     * @param dto Credenciais de login
     * @return Resposta com token JWT
     */
    public LoginResponseDTO login(LoginRequestDTO dto) {
        // Autentica usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getSenha()
                )
        );

        // Busca usuário no banco
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gera token JWT
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);

        // Retorna resposta
        return new LoginResponseDTO(
                token,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRoleName()
        );
    }

    /**
     * Valida se um email já está cadastrado
     * @param email Email a verificar
     * @return true se disponível, false se já existe
     */
    public boolean emailDisponivel(String email) {
        return !usuarioRepository.existsByEmail(email);
    }
}