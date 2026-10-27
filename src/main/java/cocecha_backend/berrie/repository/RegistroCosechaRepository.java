package cocecha_backend.berrie.repository;

import cocecha_backend.berrie.model.RegistroCosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RegistroCosechaRepository extends JpaRepository<RegistroCosecha, Long> {
    List<RegistroCosecha> findByCosechadorQrCodeUuidAndFechaHoraEscaneoBetween(
        String qrCodeUuid, LocalDateTime inicioDia, LocalDateTime finDia
    );
    
    List<RegistroCosecha> findByLoteCodigoLote(String codigoLote);
}