package com.example.supermercado.dto;

public class LoginResponse {
    private String token;
    private String nombre;
    private String rol;
    private Integer idEmpleado;

    public LoginResponse(String token, String nombre, String rol, Integer idEmpleado) {
        this.token = token;
        this.nombre = nombre;
        this.rol = rol;
        this.idEmpleado = idEmpleado;
    }

    public String getToken() {
        return token;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }
}