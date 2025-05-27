package com.usco.agendamedicacomunitaria.controller;

import com.usco.agendamedicacomunitaria.model.Cita;
import com.usco.agendamedicacomunitaria.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestParam Long pacienteId,
                                          @RequestParam Long proveedorId,
                                          @Valid @RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.crearCita(cita, pacienteId, proveedorId);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // O un código de error más específico
        }
    }

    @GetMapping("/{citaId}")
    public ResponseEntity<Cita> obtenerCita(@PathVariable Long citaId) {
        Optional<Cita> cita = citaService.findById(citaId);
        return cita.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> listarCitasPorPaciente(@PathVariable Long pacienteId) {
        List<Cita> citas = citaService.findByPacienteId(pacienteId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<Cita>> listarCitasPorProveedor(@PathVariable Long proveedorId) {
        List<Cita> citas = citaService.findByProveedorId(proveedorId);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
    
    @GetMapping("/proveedor/{proveedorId}/pendientes")
    public ResponseEntity<List<Cita>> listarCitasPendientesPorProveedor(@PathVariable Long proveedorId) {
        try {
            List<Cita> citasPendientes = citaService.findPendientesByProveedorId(proveedorId);
            return new ResponseEntity<>(citasPendientes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{citaId}")
    public ResponseEntity<Void> cancelarCita(@PathVariable Long citaId) {
        citaService.cancelarCita(citaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{citaId}/estado")
    public ResponseEntity<Cita> actualizarEstadoCita(@PathVariable Long citaId, @RequestParam Cita.EstadoCita nuevoEstado) {
        try {
            Cita citaActualizada = citaService.actualizarEstadoCita(citaId, nuevoEstado);
            return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
