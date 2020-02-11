package com.example.u2quispeejercicio1tema1.Ejercicio1.Tarea2;

public class ClienteR {
    private String Cod_persona;
    private String Nombre;
    private String Apellidos;

    public ClienteR(String Cod_persona, String Nombre, String Apellidos) {
        this.Cod_persona = Cod_persona;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
    }

    public String getcodigo() {
        return Cod_persona;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {

        return Apellidos;
    }
}