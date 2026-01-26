/*
 * Contém a lógica de negócio da aplicação. É onde ficam as regras, validações e processamentos.
 */

package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.ContratanteDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.REPOSITORY.ContratanteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratanteService {

    private final ContratanteRepository contratanteRepository;
    private final PasswordEncoder passwordEncoder;

    public ContratanteService(ContratanteRepository contratanteRepository, PasswordEncoder passwordEncoder) {
        this.contratanteRepository = contratanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //criar um novo contratante
    public ContratanteDTO criar(ContratanteDTO dto) {
        Contratante contr = new Contratante();
        contr.setEmail(dto.getEmail());
        contr.setNome(dto.getNome());
        contr.setCpf(dto.getCpf());
        contr.setTelefone(dto.getTelefone());
        contr.setEndereco(dto.getEndereco());
        contr.setSenha(passwordEncoder.encode(dto.getSenha())); // senha na abstrata

        Contratante salvo = contratanteRepository.save(contr);
        return new ContratanteDTO(salvo);
    }



    //buscar todos os contratantes
    public List<ContratanteDTO> listarTodos() {
        return contratanteRepository.findAll()
                .stream()
                .map(ContratanteDTO::new)
                .toList();
    }

    //buscar pelo id
    public ContratanteDTO buscarPorId(Long id) {
        Contratante contratante = buscarEntidadePorId(id);
        return new ContratanteDTO(contratante);
    }

    //Atualzar
    public ContratanteDTO atualizar(Long id, Contratante contratanteAtualizado){
        Contratante contratante = buscarEntidadePorId(id);

        contratante.setEndereco(contratanteAtualizado.getEndereco());
        //contratante.setEmail(contratanteAtualizado.getEmail());
        //contratante.setNome(contratanteAtualizado.getNome());
        //contratante.setSenha(contratanteAtualizado.getSenha());

        Contratante salvo = contratanteRepository.save(contratante);
        return new ContratanteDTO(salvo);
    }

    //Deletar
    public void deletar(Long id){
        contratanteRepository.deleteById(id);
    }

    private Contratante buscarEntidadePorId(Long id) {
        return contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));
    }
}