package com.example.literatura.repository;

import com.example.literatura.model.Autor;
import com.example.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    //Autores vivos en un determinado año
    @Query("SELECT a FROM Autor a JOIN FETCH a.libros WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento >= :anio OR a.fechaFallecimiento IS NULL)")
    List<Autor> autoresVivosEnAnio(Integer anio);

    // Autores
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    // Autores y libros
    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAllConLibros();
}