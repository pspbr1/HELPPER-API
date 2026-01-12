package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.PrestadorDTO;
import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.REPOSITORY.PrestadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestadorService {
    
    @Autowired
    private PrestadorRepository prestadorRepository;
    
    //criar novo prestador
    public PrestadorDTO criar(Prestador prestador) {
        Prestador salvo = prestadorRepository.save(prestador);
        return new PrestadorDTO(salvo);
    }


    //buscar todos os Prestadors
    public List<PrestadorDTO> listarTodos() {
        return prestadorRepository.findAll()
                .stream()
                .map(PrestadorDTO::new)
                .collect(Collectors.toList());
    }

    //buscar pelo id
    public PrestadorDTO buscarPorId(Long id) {
        Prestador Prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        return new PrestadorDTO(Prestador);
    }

    //Atualzar
    public PrestadorDTO atualizar(Long id, Prestador prestadorAtualizado){
        Prestador Prestador = prestadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        Prestador.setServicos(prestadorAtualizado.getServicos());
        Prestador.setEmail(prestadorAtualizado.getEmail());
        Prestador.setNome(prestadorAtualizado.getNome());
        Prestador.setSenha(prestadorAtualizado.getSenha());
        Prestador.setBioProfissional(prestadorAtualizado.getBioProfissional());

        Prestador salvo = prestadorRepository.save(Prestador);
        return new PrestadorDTO(salvo);
    }

    //Deletar
    public void deletar(Long id){
        prestadorRepository.deleteById(id);
    }
    
}
