package cocecha_backend.berrie.controller;

import cocecha_backend.berrie.model.Lote;
import cocecha_backend.berrie.model.RegistroCosecha;
import cocecha_backend.berrie.model.Trabajador;
import cocecha_backend.berrie.repository.LoteRepository;
import cocecha_backend.berrie.repository.RegistroCosechaRepository;
import cocecha_backend.berrie.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CosechaController {

    @Autowired
    private TrabajadorRepository trabajadorRepo;

    @Autowired
    private LoteRepository loteRepo;

    @Autowired
    private RegistroCosechaRepository cosechaRepo;

    @PostMapping("/cosecha/escanear")
    public ResponseEntity<?> registrarEscaneo(@RequestBody Map<String, Object> payload) {
        String qrCosechador = (String) payload.get("qrCosechador");
        Long supervisorId = Long.valueOf(payload.get("supervisorId").toString());
        Long loteId = Long.valueOf(payload.get("loteId").toString());
        Integer cubetas = Integer.valueOf(payload.get("cubetas").toString());

        Trabajador cosechador = trabajadorRepo.findByQrCodeUuid(qrCosechador)
                .orElseThrow(() -> new RuntimeException("QR de cosechador no válido"));
        Trabajador supervisor = trabajadorRepo.findById(supervisorId)
                .orElseThrow(() -> new RuntimeException("Supervisor no encontrado"));
        Lote lote = loteRepo.findById(loteId)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        RegistroCosecha registro = new RegistroCosecha();
        registro.setCosechador(cosechador);
        registro.setSupervisor(supervisor);
        registro.setLote(lote);
        registro.setCantidadCubetas(cubetas);
        registro.setFechaHoraEscaneo(LocalDateTime.now());

        cosechaRepo.save(registro);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Registro exitoso");
        respuesta.put("cosechador", cosechador.getNombre());
        respuesta.put("cubetasAgregadas", cubetas);
        respuesta.put("lote", lote.getCodigoLote());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/cosecha/mi-dia/{qrCosechador}")
    public ResponseEntity<?> obtenerMiDia(@PathVariable String qrCosechador) {
        Trabajador cosechador = trabajadorRepo.findByQrCodeUuid(qrCosechador)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDate.now().atTime(LocalTime.MAX);

        List<RegistroCosecha> registrosHoy = cosechaRepo
                .findByCosechadorQrCodeUuidAndFechaHoraEscaneoBetween(qrCosechador, inicioDia, finDia);

        int totalCubetasHoy = registrosHoy.stream()
                .mapToInt(RegistroCosecha::getCantidadCubetas)
                .sum();

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("nombre", cosechador.getNombre());
        respuesta.put("qrCodeUuid", cosechador.getQrCodeUuid());
        respuesta.put("totalCubetasHoy", totalCubetasHoy);
        respuesta.put("entregasRealizadas", registrosHoy.size());
        respuesta.put("historialHoy", registrosHoy);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/directivos/lote/{codigoLote}/rechazar")
    public ResponseEntity<?> rechazarLote(
            @PathVariable String codigoLote,
            @RequestBody Map<String, String> body) {

        Lote lote = loteRepo.findByCodigoLote(codigoLote)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado"));

        lote.setEstadoCalidad("RECHAZADO");
        lote.setMotivoRechazo(body.getOrDefault("motivo", "Incumplimiento de calidad en empaque"));
        loteRepo.save(lote);

        List<RegistroCosecha> involucrados = cosechaRepo.findByLoteCodigoLote(codigoLote);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("alerta", "Lote marcado como RECHAZADO");
        respuesta.put("lote", lote);
        respuesta.put("registrosInvolucrados", involucrados);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/directivos/datos-base")
    public ResponseEntity<?> obtenerDatosBase() {
        Map<String, Object> datos = new HashMap<>();
        datos.put("lotes", loteRepo.findAll());
        datos.put("trabajadores", trabajadorRepo.findAll());
        datos.put("registros", cosechaRepo.findAll());
        return ResponseEntity.ok(datos);
    }
}