package com.usco.agendamedicacomunitaria.controller;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/{pacienteId}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable Long pacienteId) {
        Optional<Paciente> paciente = pacienteService.obtenerPacientePorId(pacienteId);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarTodosPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @PutMapping("/{pacienteId}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long pacienteId, @Valid @RequestBody Paciente pacienteActualizado) {
        try {
            Paciente pacienteActualizadoResult = pacienteService.actualizarPaciente(pacienteId, pacienteActualizado);
            return new ResponseEntity<>(pacienteActualizadoResult, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{pacienteId}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long pacienteId) {
        if (pacienteService.eliminarPaciente(pacienteId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Obtiene todos los proveedores con los que un paciente tiene citas programadas
     * @param pacienteId ID del paciente
     * @return Lista de proveedores únicos con los que el paciente tiene citas
     */
    @GetMapping("/{pacienteId}/proveedores")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Proveedor>> obtenerProveedoresPorPaciente(@PathVariable Long pacienteId) {
        try {
            List<Proveedor> proveedores = pacienteService.obtenerProveedoresPorPaciente(pacienteId);
            return new ResponseEntity<>(proveedores, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
