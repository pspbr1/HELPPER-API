package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.UsuarioDTO;
import br.com.helpper.helpper_api.DTO.UsuarioRequestDTO;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Buscar usuário (qualquer tipo) por ID
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        return new UsuarioDTO(usuario);
    }

    // Buscar por email (útil pra login depois)
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }

    // Atualizar dados comuns (nome, email, senha) independente de ser Prestador ou Contratante
    public UsuarioDTO atualizarDadosComuns(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);

        if (temTexto(dto.getNome())) {
            usuario.setNome(dto.getNome());
        }
        if (temTexto(dto.getEmail())) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (temTexto(dto.getCpf())) {
            usuario.setCpf(dto.getCpf());
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return new UsuarioDTO(salvo);
    }

    public UsuarioDTO deletarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);

        usuarioRepository.delete(usuario);

        return new UsuarioDTO(usuario);
    }

    private Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Value("${app.upload.dir:/uploads/usuarios}")
    private String uploadDir;
    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public UsuarioDTO atualizarFotoPerfil(Long id, MultipartFile arquivoFoto) throws IOException {
        Usuario usuario = buscarEntidadePorId(id);
        String extensao = extrairExtensao(arquivoFoto.getOriginalFilename());
        String nomeFt = id + "_" + Instant.now().toEpochMilli() + "." + extensao;

        Path destino = Paths.get(uploadDir, nomeFt);

        Files.createDirectories(destino.getParent());

        Files.copy(arquivoFoto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        String urlFoto = baseUrl + "/arquivos/usuarios/" + nomeFt;
        usuario.setFotoPerfilUrl(urlFoto);
        Usuario salvo = usuarioRepository.save(usuario);
        return new UsuarioDTO(salvo);
    }
    private String extrairExtensao(String arquivo) {
        if (arquivo == null) {
            return "jpg";
        }
        int indice = arquivo.lastIndexOf('.');
        if (indice < 0 || indice == arquivo.length() - 1) {
            return "jpg";
        }
        return arquivo.substring(indice + 1).toLowerCase();
    }

    private boolean temTexto(String valor) {
        return valor != null && !valor.isBlank();
    }
}
