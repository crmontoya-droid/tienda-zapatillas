package duoc.ventas.service;

import duoc.ventas.client.ProductoClient;
import duoc.ventas.client.ClienteClient;
import duoc.ventas.dto.ApiResponse;
import duoc.ventas.dto.VentaDTO;
import duoc.ventas.dto.ProductoDTO;
import duoc.ventas.exception.ResourceNotFoundException;
import duoc.ventas.model.Venta;
import duoc.ventas.repository.VentaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private ClienteClient clienteClient;

    @Transactional(readOnly = true)
    public List<VentaDTO> listarTodas() {
        log.info("Consultando historial completo de ventas");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VentaDTO buscarPorId(Long id) {
        log.info("Buscando registro de venta ID: {}", id);
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));
    }

    @Transactional
    public VentaDTO realizarVenta(VentaDTO dto) {
        log.info("Iniciando transaccion: Cliente {} -> Producto {}", dto.getClienteId(), dto.getProductoId());

        validarClienteExistente(dto.getClienteId());

        ApiResponse<ProductoDTO> respProducto = productoClient.obtenerProductoPorId(dto.getProductoId());
        if (respProducto == null || respProducto.getData() == null) {
            throw new ResourceNotFoundException("Error: El producto no existe en el catalogo");
        }

        Double precioUnitario = respProducto.getData().getPrecio();
        dto.setTotal(precioUnitario * dto.getCantidad());

        Venta venta = convertirAEntidad(dto);
        return convertirADTO(repository.save(venta));
    }

    private void validarClienteExistente(Long id) {
        try {
            clienteClient.obtenerClientePorId(id);
        } catch (Exception e) {
            log.error("Validacion fallida: Cliente {} no existe", id);
            throw new ResourceNotFoundException("No se puede vender: El cliente no existe.");
        }
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Registro inexistente");
        }
        repository.deleteById(id);
    }

    private VentaDTO convertirADTO(Venta v) {
        VentaDTO dto = new VentaDTO();
        dto.setId(v.getId());
        dto.setClienteId(v.getClienteId());
        dto.setProductoId(v.getProductoId());
        dto.setCantidad(v.getCantidad());
        dto.setTotal(v.getTotal());
        dto.setFechaVenta(v.getFechaVenta());
        return dto;
    }

    private Venta convertirAEntidad(VentaDTO dto) {
        Venta v = new Venta();
        v.setClienteId(dto.getClienteId());
        v.setProductoId(dto.getProductoId());
        v.setCantidad(dto.getCantidad());
        v.setTotal(dto.getTotal());
        return v;
    }
}