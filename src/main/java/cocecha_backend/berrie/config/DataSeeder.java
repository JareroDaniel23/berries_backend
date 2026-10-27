package cocecha_backend.berrie.config;

import cocecha_backend.berrie.model.Lote;
import cocecha_backend.berrie.model.Trabajador;
import cocecha_backend.berrie.repository.LoteRepository;
import cocecha_backend.berrie.repository.TrabajadorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            TrabajadorRepository trabajadorRepo,
            LoteRepository loteRepo,
            PasswordEncoder encoder) {
        return args -> {
            if (trabajadorRepo.count() == 0) {
                trabajadorRepo.save(new Trabajador(null, "Juan Pérez", "juan01", encoder.encode("1234"), "QR-COS-001", "COSECHADOR"));
                trabajadorRepo.save(new Trabajador(null, "María Gómez", "maria02", encoder.encode("1234"), "QR-COS-002", "COSECHADOR"));

                trabajadorRepo.save(new Trabajador(null, "Ing. Fernando (Supervisor)", "sup_fer", encoder.encode("admin123"), "QR-SUP-001", "SUPERVISOR"));
                trabajadorRepo.save(new Trabajador(null, "Dirección General", "admin", encoder.encode("admin123"), "QR-DIR-001", "DIRECTIVO"));

                loteRepo.save(new Lote(null, "LOTE-TUNEL1-A", "Sector Norte - Túnel 1", "APROBADO", null, LocalDate.now()));
                loteRepo.save(new Lote(null, "LOTE-TUNEL4-B", "Sector Sur - Túnel 4", "APROBADO", null, LocalDate.now()));

                System.out.println("✅ DATOS DE PRUEBA CARGADOS EN NEON EXITOSAMENTE");
            }
        };
    }
}