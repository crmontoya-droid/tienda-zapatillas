package duoc.valoraciones.controller;

import duoc.valoraciones.dto.ValoracionDTO;
import duoc.valoraciones.model.Valoracion;
import duoc.valoraciones.service.ValoracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @GetMapping
    public List<Valoracion> getAll() {
        return valoracionService.listarTodas();
    }

    @GetMapping("/producto/{productoId}")
    public List<Valoracion> getByProductoId(@PathVariable Long productoId) {
        return valoracionService.buscarPorProducto(productoId);
    }

    @PostMapping
    public ResponseEntity<Valoracion> create(@Valid @RequestBody ValoracionDTO dto) {
        Valoracion valoracion = new Valoracion();
        valoracion.setClienteId(dto.getClienteId());
        valoracion.setProductoId(dto.getProductoId());
        valoracion.setPuntuacion(dto.getPuntuacion());
        valoracion.setComentario(dto.getComentario());

        Valoracion nuevaValoracion = valoracionService.crearValoracion(valoracion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaValoracion);
    }
}