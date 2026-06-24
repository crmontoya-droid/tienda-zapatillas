package duoc.envios.service;

import duoc.envios.client.VentaClient;
import duoc.envios.dto.EnvioDTO;
import duoc.envios.exception.ResourceNotFoundException;
import duoc.envios.model.Envio;
import duoc.envios.repository.EnvioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnvioService {

    @Autowired
    private EnvioRepository repository;

    @Autowired
    private VentaClient ventaClient; // Solo necesitamos validar la venta

    @Transactional(readOnly = true)
    public List<EnvioDTO> listarTodos() {
        log.info("Consultando historial de envíos");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnvioDTO buscarPorId(Long id) {
        log.info("Buscando envío ID: {}", id);
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new ResourceNotFoundException("Envío no encontrado"));
    }

    @Transactional
    public EnvioDTO programarEnvio(EnvioDTO dto) {
        log.info("Programando envío para Venta ID: {}", dto.getVentaId());

        // 1. Validar que la venta existe
        validarVentaExistente(dto.getVentaId());

        // 2. Lógica de negocio: Generar número de seguimiento único
        String tracking = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        dto.setNumeroSeguimiento(tracking);
        dto.setEstado("PREPARACION");

        // 3. Guardar Envío
        Envio envio = convertirAEntidad(dto);
        return convertirADTO(repository.save(envio));
    }

    private void validarVentaExistente(Long id) {
        try {
            ventaClient.obtenerVentaPorId(id);
        } catch (Exception e) {
            log.error("Validación fallida: Venta {} no existe", id);
            throw new ResourceNotFoundException("No se puede crear el envío: La venta especificada no existe.");
        }
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Envío inexistente");
        }
        repository.deleteById(id);
    }

    private EnvioDTO convertirADTO(Envio e) {
        EnvioDTO dto = new EnvioDTO();
        dto.setId(e.getId());
        dto.setVentaId(e.getVentaId());
        dto.setDireccion(e.getDireccion());
        dto.setEstado(e.getEstado());
        dto.setNumeroSeguimiento(e.getNumeroSeguimiento());
        dto.setFechaEnvio(e.getFechaEnvio());
        return dto;
    }

    private Envio convertirAEntidad(EnvioDTO dto) {
        Envio e = new Envio();
        e.setVentaId(dto.getVentaId());
        e.setDireccion(dto.getDireccion());
        e.setEstado(dto.getEstado());
        e.setNumeroSeguimiento(dto.getNumeroSeguimiento());
        return e;
    }
}