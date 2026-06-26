package duoc.reclamos.controller;

import duoc.reclamos.dto.ReclamoDTO;
import duoc.reclamos.model.Reclamo;
import duoc.reclamos.service.ReclamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public List<Reclamo> getAll() {
        return reclamoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> getById(@PathVariable Long id) {
        return reclamoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Reclamo> getByClienteId(@PathVariable Long clienteId) {
        return reclamoService.buscarPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Reclamo> create(@Valid @RequestBody ReclamoDTO dto) {
        Reclamo reclamo = new Reclamo();
        reclamo.setClienteId(dto.getClienteId());
        reclamo.setVentaId(dto.getVentaId()); // <-- Aquí estaba setPedidoId
        reclamo.setMotivo(dto.getMotivo());
        reclamo.setDescripcion(dto.getDescripcion());

        Reclamo nuevoReclamo = reclamoService.crearReclamo(reclamo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReclamo);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Reclamo> updateEstado(@PathVariable Long id, @RequestParam String estado) {
        return reclamoService.cambiarEstado(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}