package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import com.usco.agendamedicacomunitaria.repository.PacienteRepository;
import com.usco.agendamedicacomunitaria.repository.ProveedorRepository;
import com.usco.agendamedicacomunitaria.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final ProveedorRepository proveedorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UsuarioRepository usuarioRepository, PacienteRepository pacienteRepository, ProveedorRepository proveedorRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.proveedorRepository = proveedorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrarPaciente(Paciente paciente) {
        if (usuarioRepository.existsByEmail(paciente.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        paciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        paciente.setRol(Usuario.Rol.PACIENTE);
        paciente.setFechaRegistro(LocalDateTime.now());
        return pacienteRepository.save(paciente);
    }

    @Override
    public Usuario registrarProveedor(Proveedor proveedor) {
        if (usuarioRepository.existsByEmail(proveedor.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        if (proveedorRepository.findByNumeroRegistroProfesional(proveedor.getNumeroRegistroProfesional()).isPresent()) {
            throw new RuntimeException("El número de registro profesional ya está registrado");
        }
        proveedor.setPassword(passwordEncoder.encode(proveedor.getPassword()));
        proveedor.setRol(Usuario.Rol.PROVEEDOR);
        proveedor.setFechaRegistro(LocalDateTime.now());
        return proveedorRepository.save(proveedor);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario authenticatePaciente(String email, String password) {
        Usuario usuario = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));
        
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        return usuario;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario authenticateProveedor(String email, String password) {
        Usuario usuario = proveedorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Proveedor no encontrado con el email: " + email));
        
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        return usuario;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email);
    }
}
