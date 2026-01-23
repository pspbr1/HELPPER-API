package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.RegisterRequestDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.ENTITY.TipoUsuario;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario register(RegisterRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso!");
        }

        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF já está cadastrado!");
        }

        if (!request.getSenha().equals(request.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não conferem.");
        }

        if (Boolean.FALSE.equals(request.getTermosAceitos())) {
            throw new IllegalArgumentException("Os termos de uso devem ser aceitos.");
        }

        TipoUsuario tipo = request.getTipo();
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de usuário é obrigatório.");
        }

        Usuario usuario = criarUsuarioPorTipo(request, tipo);
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setCpf(request.getCpf());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));

        return usuarioRepository.save(usuario);
    }

    private Usuario criarUsuarioPorTipo(RegisterRequestDTO request, TipoUsuario tipo) {
        return switch (tipo) {
            case PRESTADOR -> criarPrestador(request);
            case CONTRATANTE -> criarContratante(request);
        };
    }

    private Prestador criarPrestador(RegisterRequestDTO request) {
        if (isBlank(request.getCidade())) {
            throw new IllegalArgumentException("Cidade é obrigatória para prestadores.");
        }

        Prestador prestador = new Prestador();
        prestador.setCidade(request.getCidade());
        prestador.setBioProfissional(request.getBioProfissional());
        List<String> servicos = request.getServicos();
        if (servicos != null) {
            prestador.setServicos(servicos);
        }
        return prestador;
    }

    private Contratante criarContratante(RegisterRequestDTO request) {
        if (isBlank(request.getEndereco())) {
            throw new IllegalArgumentException("Endereço é obrigatório para contratantes.");
        }

        Contratante contratante = new Contratante();
        contratante.setEndereco(request.getEndereco());
        Integer telefone = request.getTelefone();
        if (telefone != null) {
            contratante.setTelefone(telefone);
        }
        return contratante;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
