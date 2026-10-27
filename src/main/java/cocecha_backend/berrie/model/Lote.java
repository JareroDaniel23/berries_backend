package cocecha_backend.berrie.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lotes")
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoLote;

    private String zonaInvernadero;
    private String estadoCalidad; 
    private String motivoRechazo;
    private LocalDate fechaCreacion;

    public Lote(Long id, String codigoLote, String zonaInvernadero, String estadoCalidad, String motivoRechazo, LocalDate fechaCreacion) {
        this.id = id;
        this.codigoLote = codigoLote;
        this.zonaInvernadero = zonaInvernadero;
        this.estadoCalidad = estadoCalidad;
        this.motivoRechazo = motivoRechazo;
        this.fechaCreacion = fechaCreacion;
    }
}