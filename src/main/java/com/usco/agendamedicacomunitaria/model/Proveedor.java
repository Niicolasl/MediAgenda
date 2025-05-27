package com.usco.agendamedicacomunitaria.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "proveedores")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(exclude = {"especialidades", "horarios"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Proveedor extends Usuario {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proveedor", length = 50)
    private TipoProveedor tipoProveedor;

    @Size(max = 50)
    @Column(name = "numero_registro_profesional", length = 50, unique = true)
    private String numeroRegistroProfesional;

    @Size(max = 20)
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;

    @Size(max = 200)
    @Column(name = "direccion_consultorio", length = 200)
    private String direccionConsultorio;

    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean activo = true;

    @Size(max = 100)
    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "proveedor_especialidades",
            joinColumns = @JoinColumn(name = "proveedor_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    @JsonIgnoreProperties("proveedores")  // Prevents infinite recursion
    private Set<Especialidad> especialidades = new HashSet<>();

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("proveedor-horarios")
    private List<Horario> horarios = new ArrayList<>();



    public enum TipoProveedor {
        MEDICO_INDEPENDIENTE,
        ENFERMERO,
        CENTRO_SALUD_COMUNITARIO
    }
}
