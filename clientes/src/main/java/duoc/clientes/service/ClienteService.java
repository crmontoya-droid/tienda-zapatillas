package duoc.clientes.service;

import duoc.clientes.dto.ClienteDTO;
import duoc.clientes.exception.EmailDuplicadoException;
import duoc.clientes.exception.ResourceNotFoundException;
import duoc.clientes.model.Cliente;
import duoc.clientes.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarTodos() {
        log.info("Obteniendo lista de todos los clientes");
        return repository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        return repository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> {
                    log.error("Cliente no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Cliente no encontrado");
                });
    }

    @Transactional
    public ClienteDTO guardar(ClienteDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            log.error("Intento de registro con email duplicado: {}", dto.getEmail());
            throw new EmailDuplicadoException("El email ya existe");
        }
        log.info("Guardando nuevo cliente: {}", dto.getNombre());
        Cliente cliente = convertirAEntidad(dto);
        return convertirADTO(repository.save(cliente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Cliente no existe");
        }
        repository.deleteById(id);
        log.info("Cliente con ID {} eliminado", id);
    }

    // Mappers
    private ClienteDTO convertirADTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setApellido(c.getApellido());
        dto.setEmail(c.getEmail());
        dto.setFechaNacimiento(c.getFechaNacimiento());
        return dto;
    }

    private Cliente convertirAEntidad(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setNombre(dto.getNombre());
        c.setApellido(dto.getApellido());
        c.setEmail(dto.getEmail());
        c.setFechaNacimiento(dto.getFechaNacimiento());
        return c;
    }
}