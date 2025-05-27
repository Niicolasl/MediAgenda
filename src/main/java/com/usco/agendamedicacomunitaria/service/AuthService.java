package com.usco.agendamedicacomunitaria.service;

import com.usco.agendamedicacomunitaria.model.Paciente;
import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Usuario;

import java.util.Optional;

public interface AuthService {
    Usuario registrarPaciente(Paciente paciente);
    Usuario registrarProveedor(Proveedor proveedor);
    
    // Métodos para autenticación
    Usuario authenticatePaciente(String email, String password);
    Usuario authenticateProveedor(String email, String password);
    Optional<Usuario> getCurrentUser();
}
