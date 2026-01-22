package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.UsuarioDTO;
import br.com.helpper.helpper_api.DTO.UsuarioRequestDTO;
import br.com.helpper.helpper_api.ENTITY.Usuario;
import br.com.helpper.helpper_api.REPOSITORY.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCpf(dto.getCpf());

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
}
