package br.com.helpper.helpper_api.CONTROLLER;
import br.com.helpper.helpper_api.DTO.AtualizarStatusRequest;
import br.com.helpper.helpper_api.DTO.ServicoDTO;
import br.com.helpper.helpper_api.ENTITY.Servico;
import br.com.helpper.helpper_api.SERVICES.ProfissionalServicosService;
import br.com.helpper.helpper_api.SERVICES.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;
    @Autowired
    private ProfissionalServicosService profissionalServicosService;

    @PostMapping
    public ResponseEntity<ServicoDTO> create(@RequestBody Servico servico) {
        ServicoDTO  servicoDTO = servicoService.criar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ServicoDTO> atualizarStatus(@PathVariable long id, @RequestBody AtualizarStatusRequest request) {
        ServicoDTO servicoDTO = servicoService.atualizarStatus(id, request.status());
        return ResponseEntity.ok(servicoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{servicoId}/profissionais/{prestadorId}")
    public ResponseEntity<Void> vincularPrestador(
            @PathVariable Long servicoId,
            @PathVariable Long prestadorId
    ) {
        profissionalServicosService.vincularPrestadorAoServico(prestadorId, servicoId);
        return ResponseEntity.noContent().build();
    }
}
