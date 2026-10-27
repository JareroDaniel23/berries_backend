package cocecha_backend.berrie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cocecha_backend.berrie.model.Trabajador;

import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByQrCodeUuid(String qrCodeUuid);
    Optional<Trabajador> findByUsername(String username);

}
