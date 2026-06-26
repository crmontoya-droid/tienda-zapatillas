package duoc.reclamos.service;

import duoc.reclamos.model.Reclamo;
import duoc.reclamos.repository.ReclamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReclamoService {

    @Autowired
    private ReclamoRepository reclamoRepository;

    public List<Reclamo> listarTodos() {
        return reclamoRepository.findAll();
    }

    public Optional<Reclamo> buscarPorId(Long id) {
        return reclamoRepository.findById(id);
    }

    public List<Reclamo> buscarPorCliente(Long clienteId) {
        return reclamoRepository.findByClienteId(clienteId);
    }

    public Reclamo crearReclamo(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public Optional<Reclamo> cambiarEstado(Long id, String nuevoEstado) {
        return reclamoRepository.findById(id).map(reclamo -> {
            reclamo.setEstado(nuevoEstado.toUpperCase());
            return reclamoRepository.save(reclamo);
        });
    }
}