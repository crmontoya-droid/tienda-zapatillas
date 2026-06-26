package duoc.pagos.service;

import duoc.pagos.model.Pago;
import duoc.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago procesarPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Optional<Pago> actualizarEstado(Long id, String nuevoEstado) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setEstado(nuevoEstado.toUpperCase());
            return pagoRepository.save(pago);
        });
    }
}