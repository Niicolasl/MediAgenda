package com.usco.agendamedicacomunitaria.controller;

import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Especialidad;
import com.usco.agendamedicacomunitaria.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;
import com.usco.agendamedicacomunitaria.model.Usuario;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // Endpoints para la gestión de horarios (bajo /api/proveedores/{proveedorId}/horarios)
    @CrossOrigin(origins = "http://localhost:3000", 
               methods = {RequestMethod.POST, RequestMethod.OPTIONS},
               allowedHeaders = {"Content-Type", "Accept"},
               allowCredentials = "true")
    @PostMapping(
        value = "/{proveedorId}/horarios",
        consumes = {"application/json", "application/json;charset=UTF-8"},
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Horario> crearHorario(
            @PathVariable Long proveedorId, 
            @Valid @RequestBody Horario horario) {
        
        // Log the incoming request with more details
        System.out.println("\n=== INCOMING REQUEST ===");
        System.out.println("Endpoint: POST /api/proveedores/" + proveedorId + "/horarios");
        System.out.println("Proveedor ID: " + proveedorId);
        System.out.println("Horario recibido: " + horario);
        System.out.println("Fecha/Hora Inicio: " + horario.getFechaHoraInicio());
        System.out.println("Fecha/Hora Fin: " + horario.getFechaHoraFin());
        System.out.println("Disponible: " + horario.isDisponible());
        
        Horario nuevoHorario = proveedorService.crearHorario(horario, proveedorId);
        
        // Log the response
        System.out.println("=== SENDING RESPONSE ===");
        System.out.println("Horario creado con ID: " + nuevoHorario.getId());
        
        return new ResponseEntity<>(           nuevoHorario,
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{proveedorId}/horarios")
    public ResponseEntity<List<Horario>> listarHorarios(@PathVariable Long proveedorId) {
        List<Horario> horarios = proveedorService.listarHorariosPorProveedor(proveedorId);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }

    @GetMapping("/{proveedorId}/horarios/{horarioId}")
    public ResponseEntity<Horario> obtenerHorario(@PathVariable Long proveedorId, @PathVariable Long horarioId) {
        return proveedorService.obtenerHorarioPorId(horarioId)
                .map(horario -> new ResponseEntity<>(horario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{proveedorId}/horarios/{horarioId}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long proveedorId, @PathVariable Long horarioId) {
        if (proveedorService.eliminarHorario(horarioId, proveedorId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{proveedorId}/horarios/{horarioId}")
    public ResponseEntity<Horario> actualizarHorario(@PathVariable Long proveedorId, @PathVariable Long horarioId, @Valid @RequestBody Horario horario) {
        try {
            Horario horarioActualizado = proveedorService.actualizarHorario(horarioId, horario, proveedorId);
            return new ResponseEntity<>(horarioActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtiene todos los pacientes que tienen citas con un proveedor específico
     * @param proveedorId ID del proveedor
     * @return Lista de pacientes únicos que tienen citas con el proveedor
     */
    @GetMapping("/{proveedorId}/pacientes")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Usuario>> obtenerPacientesPorProveedor(@PathVariable Long proveedorId) {
        try {
            List<Usuario> pacientes = proveedorService.obtenerPacientesPorProveedor(proveedorId);
            return new ResponseEntity<>(pacientes, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{proveedorId}/disponibilidad")
    public ResponseEntity<List<Horario>> obtenerDisponibilidadProveedor(
            @PathVariable Long proveedorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        try {
            if (fecha != null) {
                // Si se proporciona una fecha específica, usar el nuevo servicio de franjas horarias
                List<FranjaHorariaDisponible> franjas = proveedorService.obtenerFranjasHorariasDisponiblesPorProveedorYFecha(
                    proveedorId, 
                    fecha.atStartOfDay()
                );
                // Convertir FranjaHorariaDisponible a Horario para mantener compatibilidad
                List<Horario> horarios = franjas.stream()
                    .map(f -> {
                        Horario h = new Horario();
                        h.setId(f.getId());
                        h.setFechaHoraInicio(f.getHoraInicio());
                        h.setFechaHoraFin(f.getHoraFin());
                        return h;
                    })
                    .collect(Collectors.toList());
                return new ResponseEntity<>(horarios, HttpStatus.OK);
            } else if (fechaInicio != null && fechaFin != null) {
                // Comportamiento original para rangos de fechas
                List<Horario> horariosDisponibles = proveedorService.obtenerHorariosDisponiblesPorProveedor(proveedorId, fechaInicio, fechaFin);
                return new ResponseEntity<>(horariosDisponibles, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoints para la gestión de especialidades del proveedor
    @PostMapping("/{proveedorId}/especialidades/{especialidadId}")
    public ResponseEntity<Proveedor> agregarEspecialidad(@PathVariable Long proveedorId, @PathVariable Long especialidadId) {
        try {
            Proveedor proveedorActualizado = proveedorService.agregarEspecialidadProveedor(proveedorId, especialidadId);
            return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{proveedorId}/especialidades/{especialidadId}")
    public ResponseEntity<Proveedor> eliminarEspecialidad(@PathVariable Long proveedorId, @PathVariable Long especialidadId) {
        try {
            Proveedor proveedorActualizado = proveedorService.eliminarEspecialidadProveedor(proveedorId, especialidadId);
            return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{proveedorId}/especialidades")
    public ResponseEntity<Set<Especialidad>> listarEspecialidadesProveedor(@PathVariable Long proveedorId) {
        try {
            Set<Especialidad> especialidades = proveedorService.listarEspecialidadesProveedor(proveedorId);
            return new ResponseEntity<>(especialidades, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoints para la gestión básica del proveedor (bajo /api/proveedores)
    @GetMapping("/{proveedorId}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable Long proveedorId) {
        Optional<Proveedor> proveedor = proveedorService.obtenerProveedorPorId(proveedorId);
        return proveedor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.listarTodosProveedores();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @PutMapping("/{proveedorId}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long proveedorId, @Valid @RequestBody Proveedor proveedorActualizado) {
        try {
            Proveedor proveedorActualizadoResult = proveedorService.actualizarProveedor(proveedorId, proveedorActualizado);
            return new ResponseEntity<>(proveedorActualizadoResult, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{proveedorId}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long proveedorId) {
        if (proveedorService.eliminarProveedor(proveedorId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/especialidad/{especialidadNombre}")
    public ResponseEntity<List<Proveedor>> buscarProveedoresPorEspecialidad(@PathVariable String especialidadNombre) {
        try {
            List<Proveedor> proveedores = proveedorService.buscarProveedoresPorEspecialidad(especialidadNombre);
            if (proveedores == null || proveedores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}