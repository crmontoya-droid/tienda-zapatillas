package duoc.valoraciones.service;

import duoc.valoraciones.model.Valoracion;
import duoc.valoraciones.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    public List<Valoracion> listarTodas() {
        return valoracionRepository.findAll();
    }

    public List<Valoracion> buscarPorProducto(Long productoId) {
        return valoracionRepository.findByProductoId(productoId);
    }

    public Optional<Valoracion> buscarPorId(Long id) {
        return valoracionRepository.findById(id);
    }

    public Valoracion crearValoracion(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }
}