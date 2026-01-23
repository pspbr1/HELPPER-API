package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.ServicoDTO;
import br.com.helpper.helpper_api.ENTITY.Servico;
import br.com.helpper.helpper_api.ENTITY.StatusEnum;
import br.com.helpper.helpper_api.REPOSITORY.ServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public ServicoDTO criar(Servico servico) {
        Servico salvar = servicoRepository.save(servico);
        return new ServicoDTO(salvar);
    }

    public ServicoDTO atualizarStatus(long id, StatusEnum novoStatus){
        Servico servico = buscarEntidadePorId(id);
        servico.setStatus(novoStatus);
        Servico salvar = servicoRepository.save(servico);

        return new ServicoDTO(salvar);
    }

    public void deletarServico(long id) {
        Servico servico = buscarEntidadePorId(id);
        servicoRepository.delete(servico);
    }

    private Servico buscarEntidadePorId(long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }
}
