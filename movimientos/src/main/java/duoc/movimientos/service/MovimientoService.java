package duoc.movimientos.service;

import duoc.movimientos.client.ProductoClient;
import duoc.movimientos.dto.MovimientoDTO;
import duoc.movimientos.exception.ResourceNotFoundException;
import duoc.movimientos.model.Movimiento;
import duoc.movimientos.repository.MovimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovimientoService {

    @Autowired
    private MovimientoRepository repository;

    @Autowired
    private ProductoClient productoClient;

    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimientoDTO registrar(MovimientoDTO dto) {
        log.info("Validando existencia del producto ID: {}", dto.getProductoId());
        try {
            productoClient.obtenerProductoPorId(dto.getProductoId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error: El producto ID " + dto.getProductoId() + " no existe.");
        }

        Movimiento movimiento = new Movimiento();
        BeanUtils.copyProperties(dto, movimiento);
        return convertirADTO(repository.save(movimiento));
    }

    private MovimientoDTO convertirADTO(Movimiento m) {
        MovimientoDTO dto = new MovimientoDTO();
        BeanUtils.copyProperties(m, dto);
        return dto;
    }
}