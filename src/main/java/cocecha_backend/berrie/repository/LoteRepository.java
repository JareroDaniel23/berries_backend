package cocecha_backend.berrie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cocecha_backend.berrie.model.Lote;

import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    
    Optional<Lote> findByCodigoLote(String codigoLote);
}
