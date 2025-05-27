package com.usco.agendamedicacomunitaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.sql.Date;

@Entity
@Table(name = "pacientes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Usuario {

    @Size(max = 50)
    @Column(name = "tipo_documento", length = 50)
    private String tipoDocumento;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero_documento", length = 20, unique = true)
    private String numeroDocumento;

    @Size(max = 20)
    @Column(length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Genero genero;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    public enum Genero {
        MASCULINO,
        FEMENINO,
        OTRO,
        PREFIERO_NO_DECIRLO
    }
}

