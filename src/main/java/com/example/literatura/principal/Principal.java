package com.example.literatura.principal;

import com.example.literatura.model.Autor;
import com.example.literatura.model.Libro;
import com.example.literatura.model.Lenguaje;
import com.example.literatura.service.LibroService;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosHasta();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el título del libro a buscar:");
        String titulo = teclado.nextLine();
        libroService.buscarLibroPorTitulo(titulo);
    }
    private void listarLibrosRegistrados() {
        System.out.println("Libros registrados: ");
        List<Libro> libros = libroService.mostrarLibros();

        libros.forEach(System.out::println);
    }
    private void listarAutoresRegistrados() {
        System.out.println("Autores registrados: ");
        List<Autor> autores = libroService.mostrarAutores();

        autores.forEach(System.out::println);
    }
    private void listarAutoresVivosHasta() {
        System.out.println("Ingresa un año: ");
        Integer anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autores = libroService.autoresVivosEnAnio(anio);

        autores.forEach(System.out::println);

    }
    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        String idioma = teclado.nextLine();
        Lenguaje lenguaje = Lenguaje.fromString(idioma);
        List<Libro> libros = libroService.librosPorIdioma(lenguaje);
        libros.forEach(System.out::println);
    }
}
