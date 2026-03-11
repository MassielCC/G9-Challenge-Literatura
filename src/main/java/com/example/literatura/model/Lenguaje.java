package com.example.literatura.model;

public enum Lenguaje {
    ES("es"),
    EN("en"),
    FR("fr"),
    PT("pt");

    private String codigo;

    Lenguaje(String codigo){
        this.codigo = codigo;
    }

    public String getCodigo(){
        return codigo;
    }
    public static Lenguaje fromString(String text){
        for(Lenguaje lenguaje : Lenguaje.values()){
            if(lenguaje.codigo.equalsIgnoreCase(text)){
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Lenguaje no encontrado: " + text);
    }
}