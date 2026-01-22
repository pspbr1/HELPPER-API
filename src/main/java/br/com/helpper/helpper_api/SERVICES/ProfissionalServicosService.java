package br.com.helpper.helpper_api.SERVICES;

import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.ENTITY.ProfissionalServicos;
import br.com.helpper.helpper_api.ENTITY.Servico;
import br.com.helpper.helpper_api.REPOSITORY.PrestadorRepository;
import br.com.helpper.helpper_api.REPOSITORY.ProfissionalServicosRepository;
import br.com.helpper.helpper_api.REPOSITORY.ServicoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalServicosService {

    private ProfissionalServicosRepository profissionalServicosRepository;
    private ServicoRepository servicoRepository;
    private PrestadorRepository prestadorRepository;

    public ProfissionalServicosService(
            PrestadorRepository prestadorRepository,
            ServicoRepository servicoRepository,
            ProfissionalServicosRepository profissionalServicosRepository
    ){
        this.prestadorRepository = prestadorRepository;
        this.servicoRepository = servicoRepository;
        this.profissionalServicosRepository = profissionalServicosRepository;
    }

 public void vincularPrestadorAoServico(Long prestadorId, Long servicoId){
     Prestador p = prestadorRepository.findById(prestadorId).orElseThrow(() -> new RuntimeException("Prestador não localizado"));
     Servico s = servicoRepository.findById(servicoId).orElseThrow(()-> new RuntimeException("Serviço não localizado"));

     ProfissionalServicos ps = new ProfissionalServicos();
     ps.setProfissional(p);
     ps.setServico(s);

     profissionalServicosRepository.save(ps);
 }

}
