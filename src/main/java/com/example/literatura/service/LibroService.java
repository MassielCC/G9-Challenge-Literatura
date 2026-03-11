package com.example.literatura.service;

import com.example.literatura.model.*;
import com.example.literatura.repository.AutorRepository;
import com.example.literatura.repository.LibroRepository;
import com.example.literatura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvierteDatos conversor;

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    // Buscar libro en API y guardarlo
    public void buscarLibroPorTitulo(String titulo) {
        String json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "%20"));

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }
        DatosLibro datosLibro = respuesta.resultados().get(0);

        Optional<Libro> libroExistente = libroRepository
                .findByTituloContainsIgnoreCase(datosLibro.titulo());

        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return;
        }
        Libro libro = new Libro(datosLibro);
        List<Autor> autores = new ArrayList<>();

        for (DatosAutor datosAutor : datosLibro.autores()) {
            Optional<Autor> autorExistente =
                    autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

            Autor autor;

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor(datosAutor);
                autorRepository.save(autor);
            }

            autores.add(autor);
        }
        libro.setAutores(autores);

        libroRepository.save(libro);

        System.out.println("Libro guardado correctamente.");
        System.out.println(libro);
    }
    // Mostrar todos los libros
    public List<Libro> mostrarLibros() {
        return libroRepository.findAllConAutores();
    }
    // Libros por idioma
    public List<Libro> librosPorIdioma(Lenguaje lenguaje) {
        return libroRepository.buscarLibrosPorIdioma(lenguaje);
    }
    // Autores registrados
    public List<Autor> mostrarAutores() {
        return autorRepository.findAllConLibros();
    }
    // Autores vivos en determinado año
    public List<Autor> autoresVivosEnAnio(Integer anio) {
        return autorRepository.autoresVivosEnAnio(anio);
    }

}
