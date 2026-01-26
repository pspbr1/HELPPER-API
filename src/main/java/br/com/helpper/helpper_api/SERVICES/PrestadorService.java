package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.PrestadorDTO;
import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.REPOSITORY.PrestadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestadorService {

    private final PrestadorRepository prestadorRepository;
    private final PasswordEncoder passwordEncoder;

    public PrestadorService(PrestadorRepository prestadorRepository, PasswordEncoder passwordEncoder) {
        this.prestadorRepository = prestadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //criar novo prestador
    public PrestadorDTO criar(PrestadorDTO prestador) {
        Prestador prestadorAux = new Prestador();
        prestadorAux.setNome(prestador.getNome());
        prestadorAux.setCpf(prestador.getCpf());
        prestadorAux.setEmail(prestador.getEmail());
        prestadorAux.setSenha(passwordEncoder.encode(prestador.getSenha()));
        Prestador salvo = prestadorRepository.save(prestadorAux);
        return new PrestadorDTO(salvo);
    }


    //buscar todos os Prestadors
    public List<PrestadorDTO> listarTodos() {
        return prestadorRepository.findAll()
                .stream()
                .map(PrestadorDTO::new)
                .toList();
    }

    //buscar pelo id
    public PrestadorDTO buscarPorId(Long id) {
        Prestador prestador = buscarEntidadePorId(id);
        return new PrestadorDTO(prestador);
    }

    //Atualzar
    public PrestadorDTO atualizar(Long id, Prestador prestadorAtualizado){
        Prestador prestador = buscarEntidadePorId(id);

        prestador.setServicos(prestadorAtualizado.getServicos());
        prestador.setEmail(prestadorAtualizado.getEmail());
        prestador.setNome(prestadorAtualizado.getNome());
        prestador.setSenha(prestadorAtualizado.getSenha());
        prestador.setBioProfissional(prestadorAtualizado.getBioProfissional());

        Prestador salvo = prestadorRepository.save(prestador);
        return new PrestadorDTO(salvo);
    }

    //Deletar
    public void deletar(Long id){
        prestadorRepository.deleteById(id);
    }

    private Prestador buscarEntidadePorId(Long id) {
        return prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
    }
}