/*
* Recebe as requisições HTTP (do Postman/Frontend) e devolve respostas.
*/


package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.ContratanteDTO;
import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.SERVICES.ContratanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratantes")
public class ContratanteController {

    @Autowired
    private ContratanteService contratanteService;

    // Metodo POST /api/contratantes
    @PostMapping
    public ResponseEntity<ContratanteDTO> criar(@RequestBody Contratante contratante) {
        ContratanteDTO dto = contratanteService.criar(contratante);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Metodo GET /api/contratantes - Listar todos
    @GetMapping
    public ResponseEntity<List<ContratanteDTO>> listarTodos() {
        List<ContratanteDTO> lista = contratanteService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET /api/contratantes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContratanteDTO> buscarPorId(@PathVariable Long id) {
        ContratanteDTO dto = contratanteService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // Metodo PUT /api/contratantes/{id} - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ContratanteDTO> atualizar(
            @PathVariable Long id,
            @RequestBody Contratante contratante
    ) {
        ContratanteDTO dto = contratanteService.atualizar(id, contratante);
        return ResponseEntity.ok(dto);
    }

    // Metodo DELETE /api/contratantes/{id} - Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}