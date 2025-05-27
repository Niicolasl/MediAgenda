package com.usco.agendamedicacomunitaria.controller;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;
import com.usco.agendamedicacomunitaria.service.AuthService;
import com.usco.agendamedicacomunitaria.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final PacienteService pacienteService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, 
                         PacienteService pacienteService,
                         AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.pacienteService = pacienteService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/paciente/registro")
    public ResponseEntity<?> registrarPaciente(@Valid @RequestBody Paciente paciente) {
        try {
            Usuario nuevoPaciente = pacienteService.registrarPaciente(paciente);
            return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/proveedor/registro")
    public ResponseEntity<?> registrarProveedor(@Valid @RequestBody Proveedor proveedor) {
        try {
            Usuario nuevoProveedor = authService.registrarProveedor(proveedor);
            return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/paciente/login")
    public ResponseEntity<?> authenticatePaciente(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario usando Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Obtener el usuario autenticado
            Usuario usuario = authService.authenticatePaciente(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            );
            
            // La sesión se manejará automáticamente por Spring Security
            return ResponseEntity.ok(usuario);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error de autenticación: " + e.getMessage());
        }
    }
    
    @PostMapping("/proveedor/login")
    public ResponseEntity<?> authenticateProveedor(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario usando Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Obtener el usuario autenticado
            Usuario usuario = authService.authenticateProveedor(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            );
            
            // La sesión se manejará automáticamente por Spring Security
            return ResponseEntity.ok(usuario);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error de autenticación: " + e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Invalidar la sesión actual
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Usuario currentUser = authService.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
            return ResponseEntity.ok(currentUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error al obtener el usuario actual: " + e.getMessage());
        }
    }
    
    // Clase interna para manejar las solicitudes de inicio de sesión
    public static class LoginRequest {
        private String email;
        private String password;
        
        // Getters y Setters
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
