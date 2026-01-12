package br.com.helpper.helpper_api.CONTROLLER;

import br.com.helpper.helpper_api.DTO.PrestadorDTO;
import br.com.helpper.helpper_api.ENTITY.Prestador;
import br.com.helpper.helpper_api.SERVICES.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestadors")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    // Metodo POST /api/Prestadores - Criar prestador
    @PostMapping
    public ResponseEntity<PrestadorDTO> criar(@RequestBody Prestador prestador) {
        PrestadorDTO dto = prestadorService.criar(prestador);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Metodo GET /api/Prestadors - Listar todos prestadores
    @GetMapping
    public ResponseEntity<List<PrestadorDTO>> listarTodos() {
        List<PrestadorDTO> lista = prestadorService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // GET /api/Prestadors/{id} - Buscar prestador por ID
    @GetMapping("/{id}")
    public ResponseEntity<PrestadorDTO> buscarPorId(@PathVariable Long id) {
        PrestadorDTO dto = prestadorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // Metodo PUT /api/Prestadors/{id} - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<PrestadorDTO> atualizar(
            @PathVariable Long id,
            @RequestBody Prestador prestador
    ) {
        PrestadorDTO dto = prestadorService.atualizar(id, prestador);
        return ResponseEntity.ok(dto);
    }

    // Metodo DELETE /api/Prestadors/{id} - Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        prestadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}