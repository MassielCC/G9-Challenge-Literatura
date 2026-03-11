package com.example.literatura.repository;

import com.example.literatura.model.Lenguaje;
import com.example.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Mostrar libro por título
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    // Libros por idioma
    @Query("SELECT l FROM Libro l JOIN FETCH l.autores WHERE l.lenguaje = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") Lenguaje lenguaje);
    //List<Libro> findByLenguaje(Lenguaje lenguaje);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    List<Libro> findAllConAutores();
}