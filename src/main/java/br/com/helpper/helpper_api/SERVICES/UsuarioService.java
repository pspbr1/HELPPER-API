/*
 * UsuarioService.java
 * Serviço responsável por lógica de negócio relacionada a Usuários
 * Gerencia CRUD, atualização de perfil e upload de foto
 * NUNCA expõe senhas - utiliza BCrypt para segurança
 */
package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.AlterarSenhaDTO;
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

    @Value("${app.upload.dir:/uploads/usuarios}")
    private String uploadDir;

    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Busca usuário por ID
     * @param id ID do usuário
     * @return DTO sem senha
     */
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        return new UsuarioDTO(usuario);
    }

    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return DTO sem senha
     */
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioDTO(usuario);
    }

    /**
     * Atualiza dados comuns do usuário (nome, email, CPF)
     * NÃO atualiza senha - use alterarSenha()
     */
    public UsuarioDTO atualizarDadosComuns(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);

        // Verifica se email já existe em outro usuário
        if (!usuario.getEmail().equals(dto.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());

        Usuario salvo = usuarioRepository.save(usuario);
        return new UsuarioDTO(salvo);
    }

    /**
     * Altera senha do usuário com validações de segurança
     */
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);

        // Valida senha atual
        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        // Valida se senhas conferem
        if (!dto.getNovaSenha().equals(dto.getConfirmarNovaSenha())) {
            throw new RuntimeException("Senhas não conferem");
        }

        // Valida se nova senha é diferente da antiga
        if (dto.getSenhaAtual().equals(dto.getNovaSenha())) {
            throw new RuntimeException("Nova senha deve ser diferente da atual");
        }

        // Criptografa e salva nova senha
        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepository.save(usuario);
    }

    /**
     * Deleta usuário por ID
     */
    public UsuarioDTO deletarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
        return new UsuarioDTO(usuario);
    }

    /**
     * Atualiza foto de perfil do usuário
     * @param id ID do usuário
     * @param arquivoFoto Arquivo da foto
     * @return DTO atualizado
     */
    public UsuarioDTO atualizarFotoPerfil(Long id, MultipartFile arquivoFoto) throws IOException {
        Usuario usuario = buscarEntidadePorId(id);

        // Valida tipo de arquivo
        String contentType = arquivoFoto.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Arquivo deve ser uma imagem");
        }

        // Gera nome único para o arquivo
        String extensao = extrairExtensao(arquivoFoto.getOriginalFilename());
        String nomeArquivo = id + "_" + Instant.now().toEpochMilli() + "." + extensao;

        // Cria diretório se não existir
        Path diretorio = Paths.get(uploadDir);
        Files.createDirectories(diretorio);

        // Salva arquivo
        Path destino = diretorio.resolve(nomeArquivo);
        Files.copy(arquivoFoto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        // Atualiza URL no banco
        String urlFoto = baseUrl + "/arquivos/usuarios/" + nomeArquivo;
        usuario.setFotoPerfilUrl(urlFoto);
        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioDTO(salvo);
    }

    /**
     * Busca entidade Usuario por ID
     * Método privado auxiliar
     */
    private Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    /**
     * Extrai extensão do arquivo
     */
    private String extrairExtensao(String nomeArquivo) {
        if (nomeArquivo == null || !nomeArquivo.contains(".")) {
            return "jpg";
        }
        return nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1).toLowerCase();
    }
}