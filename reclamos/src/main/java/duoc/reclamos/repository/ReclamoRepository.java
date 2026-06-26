package duoc.reclamos.repository;

import duoc.reclamos.model.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
    // Permite buscar todos los reclamos de un mismo cliente si lo necesitas
    List<Reclamo> findByClienteId(Long clienteId);
}