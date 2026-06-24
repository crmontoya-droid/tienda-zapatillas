package duoc.productos.service;

import duoc.productos.client.ClienteClient;
import duoc.productos.dto.ApiResponse; // Importar tu ApiResponse
import duoc.productos.dto.ProductoDTO;
import duoc.productos.exception.ResourceNotFoundException;
import duoc.productos.model.Producto;
import duoc.productos.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ClienteClient clienteClient;

    @Transactional(readOnly = true)
    public Object verificarClienteExterno(Long id) {
        log.info("Comunicación remota: Consultando cliente ID {} en microservicio externo", id);
        try {
            ApiResponse<Object> respuesta = clienteClient.obtenerClientePorId(id);

            if (respuesta != null && respuesta.getData() != null) {
                log.info("Cliente verificado exitosamente: {}", respuesta.getData());
                return respuesta.getData(); // Retornamos los datos del cliente
            }
            throw new ResourceNotFoundException("El cliente existe pero no se recibió información válida.");

        } catch (Exception e) {
            log.error("Error de integración: No se pudo encontrar al cliente con ID {}", id);
            throw new ResourceNotFoundException("Error de integración: El cliente no existe en el sistema de Clientes.");
        }
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodos() {
        log.info("Accediendo al catálogo completo de productos");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoDTO buscarPorId(Long id) {
        log.info("Buscando producto con ID: {}", id);
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.error("Error: Producto con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Producto no existente en el catálogo");
                });
    }

    @Transactional
    public ProductoDTO guardar(ProductoDTO dto) {
        log.info("Iniciando guardado de producto: {}", dto.getNombre());
        Producto p = convertirAEntidad(dto);
        return convertirADTO(repository.save(p));
    }

    @Transactional
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        log.info("Actualizando producto con ID: {}", id);
        Producto existente = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al actualizar: ID {} no encontrado", id);
                    return new ResourceNotFoundException("No se puede actualizar: Producto no encontrado");
                });

        existente.setNombre(dto.getNombre());
        existente.setMarca(dto.getMarca());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());

        return convertirADTO(repository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar producto con ID: {}", id);
        if (!repository.existsById(id)) {
            log.error("Fallo al eliminar: ID {} no existe", id);
            throw new ResourceNotFoundException("No se puede eliminar: Producto no existente");
        }
        repository.deleteById(id);
        log.info("Producto con ID {} eliminado exitosamente", id);
    }

    private ProductoDTO convertirADTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setMarca(p.getMarca());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        return dto;
    }

    private Producto convertirAEntidad(ProductoDTO dto) {
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setMarca(dto.getMarca());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        return p;
    }
}