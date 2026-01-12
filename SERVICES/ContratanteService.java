/*
* Contém a lógica de negócio da aplicação. É onde ficam as regras, validações e processamentos.
*/

package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.ContratanteDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.REPOSITORY.ContratanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratanteService {

    @Autowired
    private ContratanteRepository contratanteRepository;

    //criar um novo contratante
    public ContratanteDTO criar(Contratante contratante) {
        Contratante salvo = contratanteRepository.save(contratante);
        return new ContratanteDTO(salvo);
    }


    //buscar todos os contratantes
    public List<ContratanteDTO> listarTodos() {
        return contratanteRepository.findAll()
                .stream()
                .map(ContratanteDTO::new)
                .collect(Collectors.toList());
    }

    //buscar pelo id
    public ContratanteDTO buscarPorId(Long id) {
        Contratante contratante = contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));
        return new ContratanteDTO(contratante);
    }

    //Atualzar
    public ContratanteDTO atualizar(Long id, Contratante contratanteAtualizado){
        Contratante contratante = contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));

        contratante.setEndereco(contratanteAtualizado.getEndereco());
        contratante.setEmail(contratanteAtualizado.getEmail());
        contratante.setNome(contratanteAtualizado.getNome());
        contratante.setSenha(contratanteAtualizado.getSenha());
        contratante.setEndereco(contratanteAtualizado.getEndereco());

        Contratante salvo = contratanteRepository.save(contratante);
        return new ContratanteDTO(salvo);
    }

    //Deletar
    public void deletar(Long id){
        contratanteRepository.deleteById(id);
    }
}
