package com.example.literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String titulo;

    @ManyToMany()
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;
    private Double numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.lenguaje = Lenguaje.fromString(datosLibro.lenguajes().stream()
                .limit(1).collect(Collectors.joining()));
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString(){
        return "---------------LIBRO---------------"
                + "\nTítulo: " + titulo
                + "\nAutor: " + autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining(", "))
                + "\nIdioma: " + lenguaje
                + "\nNúmero de descargas: " + numeroDeDescargas
                +"\n-------------------------------\n"
                ;
    }
}
