package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Cita;
import com.usco.agendamedicacomunitaria.model.FranjaHorariaDisponible;
import com.usco.agendamedicacomunitaria.model.Horario;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import com.usco.agendamedicacomunitaria.model.Especialidad;
import com.usco.agendamedicacomunitaria.repository.CitaRepository;
import com.usco.agendamedicacomunitaria.repository.FranjaHorariaDisponibleRepository;
import com.usco.agendamedicacomunitaria.repository.HorarioRepository;
import com.usco.agendamedicacomunitaria.repository.ProveedorRepository;
import com.usco.agendamedicacomunitaria.repository.UsuarioRepository;
import com.usco.agendamedicacomunitaria.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final HorarioRepository horarioRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EspecialidadRepository especialidadRepository;
    private final FranjaHorariaDisponibleRepository franjaHorariaDisponibleRepository;
    private final CitaRepository citaRepository;

    @Autowired
    public ProveedorServiceImpl(HorarioRepository horarioRepository, 
                             ProveedorRepository proveedorRepository, 
                             UsuarioRepository usuarioRepository, 
                             PasswordEncoder passwordEncoder, 
                             EspecialidadRepository especialidadRepository, 
                             FranjaHorariaDisponibleRepository franjaHorariaDisponibleRepository,
                             CitaRepository citaRepository) {
        this.horarioRepository = horarioRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.especialidadRepository = especialidadRepository;
        this.franjaHorariaDisponibleRepository = franjaHorariaDisponibleRepository;
        this.citaRepository = citaRepository;
    }

    @Transactional
    @Override
    public Usuario registrarProveedor(Proveedor proveedor) {
        if (usuarioRepository.findByEmail(proveedor.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya registrado");
        }
        if (proveedorRepository.findByNumeroRegistroProfesional(proveedor.getNumeroRegistroProfesional()).isPresent()) {
            throw new RuntimeException("Número de registro profesional ya registrado");
        }
        proveedor.setPassword(passwordEncoder.encode(proveedor.getPassword()));
        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setRol(Usuario.Rol.PROVEEDOR);
        return proveedorRepository.save(proveedor);
    }

    private void validarHorarioPerteneceProveedor(Long horarioId, Long proveedorId) {
        Optional<Horario> horarioOptional = horarioRepository.findById(horarioId);
        if (horarioOptional.isEmpty() || !horarioOptional.get().getProveedor().getId().equals(proveedorId)) {
            throw new RuntimeException("Horario no encontrado con ID: " + horarioId + " para el Proveedor con ID: " + proveedorId);
        }
    }

    @Transactional
    @Override
    public Horario crearHorario(Horario horario, Long proveedorId) {
        return proveedorRepository.findById(proveedorId)
                .map(proveedor -> {
                    horario.setProveedor(proveedor);
                    Horario horarioGuardado = horarioRepository.save(horario);
                    generarFranjasHorarias(horarioGuardado, proveedor, 45, 15); // Duración cita 45 min, descanso 15 min
                    return horarioGuardado;
                })
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    private void generarFranjasHorarias(Horario horario, Proveedor proveedor, int duracionCitaMinutos, int descansoMinutos) {
        LocalDateTime inicio = horario.getFechaHoraInicio();
        LocalDateTime fin = horario.getFechaHoraFin();
        LocalDateTime horaActual = inicio;

        while (horaActual.isBefore(fin)) {
            LocalDateTime finCita = horaActual.plusMinutes(duracionCitaMinutos);
            if (finCita.isAfter(fin)) {
                break; // No generar una franja que exceda el horario de fin
            }
            franjaHorariaDisponibleRepository.save(new FranjaHorariaDisponible(null, proveedor, horaActual.toLocalDate().atStartOfDay(), horaActual, finCita, true));
            horaActual = finCita.plusMinutes(descansoMinutos);
        }
    }

    @Override
    public List<Horario> listarHorariosPorProveedor(Long proveedorId) {
        return proveedorRepository.findById(proveedorId)
                .map(horarioRepository::findByProveedor)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    @Override
    public Optional<Horario> obtenerHorarioPorId(Long id) {
        return horarioRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean eliminarHorario(Long id, Long proveedorId) {
        validarHorarioPerteneceProveedor(id, proveedorId);
        // Considerar eliminar también las franjas horarias asociadas a este horario
        horarioRepository.deleteById(id);
        // Implementa la lógica para eliminar las franjas horarias si es necesario
        return true;
    }

    @Transactional
    @Override
    public Horario actualizarHorario(Long id, Horario horarioActualizado, Long proveedorId) {
        validarHorarioPerteneceProveedor(id, proveedorId);
        return horarioRepository.findById(id)
                .map(horarioExistente -> {
                    horarioExistente.setFechaHoraInicio(horarioActualizado.getFechaHoraInicio());
                    horarioExistente.setFechaHoraFin(horarioActualizado.getFechaHoraFin());
                    horarioExistente.setDisponible(horarioActualizado.isDisponible());
                    // Considerar regenerar las franjas horarias si el horario cambia significativamente
                    return horarioRepository.save(horarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
    }

    @Override
    public List<Horario> obtenerHorariosDisponiblesPorProveedor(Long proveedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return proveedorRepository.findById(proveedorId)
                .map(proveedor -> horarioRepository.findByProveedorAndFechaHoraInicioGreaterThanEqualAndFechaHoraFinLessThanEqualAndDisponibleTrue(
                        proveedor, fechaInicio, fechaFin
                ))
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    @Override
    public Optional<Proveedor> obtenerProveedorPorId(Long proveedorId) {
        return proveedorRepository.findById(proveedorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> listarTodosProveedores() {
        return proveedorRepository.findAllActivos();
    }

    @Transactional
    @Override
    public Proveedor actualizarProveedor(Long proveedorId, Proveedor proveedorActualizado) {
        Optional<Proveedor> proveedorExistenteOptional = proveedorRepository.findById(proveedorId);
        if (proveedorExistenteOptional.isPresent()) {
            Proveedor proveedorExistente = proveedorExistenteOptional.get();
            proveedorExistente.setTipoProveedor(proveedorActualizado.getTipoProveedor());
            proveedorExistente.setNumeroRegistroProfesional(proveedorActualizado.getNumeroRegistroProfesional());
            proveedorExistente.setTelefonoContacto(proveedorActualizado.getTelefonoContacto());
            proveedorExistente.setDireccionConsultorio(proveedorActualizado.getDireccionConsultorio());
            proveedorExistente.setDescripcion(proveedorActualizado.getDescripcion());
            proveedorExistente.setNombre(proveedorActualizado.getNombre());
            proveedorExistente.setApellido(proveedorActualizado.getApellido());
            proveedorExistente.setEmail(proveedorActualizado.getEmail());
            proveedorExistente.setCiudad(proveedorActualizado.getCiudad());
            return proveedorRepository.save(proveedorExistente);
        } else {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorId);
        }
    }

    @Transactional
    @Override
    public boolean eliminarProveedor(Long proveedorId) {
        if (proveedorRepository.existsById(proveedorId)) {
            proveedorRepository.deleteById(proveedorId);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Proveedor agregarEspecialidadProveedor(Long proveedorId, Long especialidadId) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
        Especialidad especialidad = especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + especialidadId));
        proveedor.getEspecialidades().add(especialidad);
        proveedorRepository.save(proveedor);
        return proveedorRepository.findByIdWithEspecialidades(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
    }

    @Transactional
    @Override
    public Proveedor eliminarEspecialidadProveedor(Long proveedorId, Long especialidadId) {
        Proveedor proveedor = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId));
        Especialidad especialidad = especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + especialidadId));
        proveedor.getEspecialidades().remove(especialidad);
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Set<Especialidad> listarEspecialidadesProveedor(Long proveedorId) {
        return proveedorRepository.findByIdWithEspecialidades(proveedorId)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + proveedorId))
                .getEspecialidades();
    }

    @Override
    public List<Proveedor> buscarProveedoresPorEspecialidad(String especialidadNombre) {
        return proveedorRepository.findByEspecialidadesNombreContainingIgnoreCase(especialidadNombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHorariaDisponible> obtenerFranjasHorariasDisponiblesPorProveedorYFecha(Long proveedorId, LocalDateTime fecha) {
        Optional<Proveedor> proveedorOptional = proveedorRepository.findById(proveedorId);
        if (proveedorOptional.isEmpty()) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorId);
        }
        
        // Usar la nueva consulta que excluye automáticamente las franjas con citas existentes
        return franjaHorariaDisponibleRepository.findDisponiblesByProveedorYFecha(
            proveedorOptional.get(), 
            fecha.toLocalDate().atStartOfDay()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerPacientesPorProveedor(Long proveedorId) {
        // Verificar si el proveedor existe
        if (!proveedorRepository.existsById(proveedorId)) {
            throw new NoSuchElementException("No se encontró el proveedor con ID: " + proveedorId);
        }
        
        // Obtener todas las citas del proveedor
        List<Cita> citas = citaRepository.findByProveedorId(proveedorId);
        
        // Extraer pacientes únicos
        return citas.stream()
            .map(Cita::getPaciente)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }
}
