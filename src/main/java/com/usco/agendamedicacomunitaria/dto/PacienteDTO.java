package com.usco.agendamedicacomunitaria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.usco.agendamedicacomunitaria.model.Paciente;

import java.time.LocalDateTime;

public class PacienteDTO {
    private Long id;
    private String identificacion;
    private String tipoDocumento;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private Paciente.Genero genero;
    private Integer totalCitas;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ultimaCita;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
    
    public PacienteDTO() {
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }
    
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Paciente.Genero getGenero() {
        return genero;
    }
    
    public void setGenero(Paciente.Genero genero) {
        this.genero = genero;
    }
    
    public Integer getTotalCitas() {
        return totalCitas;
    }
    
    public void setTotalCitas(Integer totalCitas) {
        this.totalCitas = totalCitas;
    }
    
    public LocalDateTime getUltimaCita() {
        return ultimaCita;
    }
    
    public void setUltimaCita(LocalDateTime ultimaCita) {
        this.ultimaCita = ultimaCita;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
