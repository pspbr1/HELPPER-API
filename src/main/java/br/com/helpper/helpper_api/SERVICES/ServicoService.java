package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.DTO.ServicoDTO;
import br.com.helpper.helpper_api.ENTITY.Servico;
import br.com.helpper.helpper_api.ENTITY.StatusEnum;
import br.com.helpper.helpper_api.REPOSITORY.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public ServicoDTO criar(Servico servico) {
        Servico salvar = servicoRepository.save(servico);
        return new ServicoDTO(salvar);
    }

    public ServicoDTO atualizarStatus(long id, StatusEnum novoStatus){
        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(novoStatus);
        Servico salvar = servicoRepository.save(servico);

        return new ServicoDTO(salvar);
    }

    public void deletarServico(long id) { }
}
