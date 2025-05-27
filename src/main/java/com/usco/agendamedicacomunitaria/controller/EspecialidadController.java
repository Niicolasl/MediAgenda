package com.usco.agendamedicacomunitaria.controller;

import com.usco.agendamedicacomunitaria.model.Especialidad;
import com.usco.agendamedicacomunitaria.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @Autowired
    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping
    public ResponseEntity<Especialidad> crearEspecialidad(@Valid @RequestBody Especialidad especialidad) {
        Especialidad nuevaEspecialidad = especialidadService.crearEspecialidad(especialidad);
        return new ResponseEntity<>(nuevaEspecialidad, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerEspecialidadPorId(@PathVariable Long id) {
        Optional<Especialidad> especialidad = especialidadService.obtenerEspecialidadPorId(id);
        return especialidad.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Especialidad> obtenerEspecialidadPorNombre(@PathVariable String nombre) {
        Optional<Especialidad> especialidad = especialidadService.obtenerEspecialidadPorNombre(nombre);
        return especialidad.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> listarTodasEspecialidades() {
        List<Especialidad> especialidades = especialidadService.listarTodasEspecialidades();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizarEspecialidad(@PathVariable Long id, @Valid @RequestBody Especialidad especialidadActualizada) {
        try {
            Especialidad especialidad = especialidadService.actualizarEspecialidad(id, especialidadActualizada);
            return new ResponseEntity<>(especialidad, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Long id) {
        if (especialidadService.eliminarEspecialidad(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}