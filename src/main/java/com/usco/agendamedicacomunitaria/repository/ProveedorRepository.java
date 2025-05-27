package com.usco.agendamedicacomunitaria.repository;

import com.usco.agendamedicacomunitaria.model.Proveedor;
import com.usco.agendamedicacomunitaria.model.Proveedor.TipoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p FROM Proveedor p JOIN FETCH p.especialidades e WHERE LOWER(e.nombre) LIKE LOWER(concat('%', :especialidadNombre, '%'))")
    List<Proveedor> findByEspecialidadesNombreContainingIgnoreCase(@Param("especialidadNombre") String especialidadNombre);

    List<Proveedor> findByTipoProveedor(TipoProveedor tipoProveedor);

    Optional<Proveedor> findByNumeroRegistroProfesional(String numeroRegistroProfesional);

    @Override
    @Query("SELECT DISTINCT p FROM Proveedor p LEFT JOIN FETCH p.especialidades")
    List<Proveedor> findAll();
    
    @Query("SELECT p FROM Proveedor p LEFT JOIN FETCH p.especialidades WHERE p.activo = true")
    List<Proveedor> findAllActivos();

    @Query("SELECT p FROM Proveedor p LEFT JOIN FETCH p.especialidades WHERE p.id = :id AND p.activo = true")
    Optional<Proveedor> findByIdWithEspecialidades(Long id);

    @Override
    @Query("SELECT p FROM Proveedor p WHERE p.id = :id AND p.activo = true")
    Optional<Proveedor> findById(Long id);
    
    Optional<Proveedor> findByEmail(String email);
}
