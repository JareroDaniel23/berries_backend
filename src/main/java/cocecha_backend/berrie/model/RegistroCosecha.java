package cocecha_backend.berrie.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "registros_cosecha")
public class RegistroCosecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "cosechador_id", nullable = false)
    private Trabajador cosechador;

    @ManyToOne 
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Trabajador supervisor;

    @ManyToOne 
    @JoinColumn(name = "lote_id", nullable = false)
    private Lote lote;

    private Integer cantidadCubetas;
    private LocalDateTime fechaHoraEscaneo;
    
}
