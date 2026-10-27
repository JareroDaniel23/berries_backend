package cocecha_backend.berrie.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trabajadores")
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String qrCodeUuid;

    private String rol;

    public Trabajador(Long id, String nombre, String username, String password, String qrCodeUuid, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.qrCodeUuid = qrCodeUuid;
        this.rol = rol;
    }
}