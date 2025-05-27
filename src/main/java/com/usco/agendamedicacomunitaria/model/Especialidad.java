package com.usco.agendamedicacomunitaria.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "especialidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "proveedores")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(length = 100, unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String nombre;

    @ManyToMany(mappedBy = "especialidades", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("especialidades")  // Prevents infinite recursion
    @EqualsAndHashCode.Exclude
    private Set<Proveedor> proveedores = new HashSet<>();
    
    // Método helper para manejar la relación bidireccional
    public void addProveedor(Proveedor proveedor) {
        this.proveedores.add(proveedor);
        proveedor.getEspecialidades().add(this);
    }
    
    public void removeProveedor(Proveedor proveedor) {
        this.proveedores.remove(proveedor);
        proveedor.getEspecialidades().remove(this);
    }
}
