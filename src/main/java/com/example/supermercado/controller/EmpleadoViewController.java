package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.service.EmpleadoService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoViewController {

    private final EmpleadoService service;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "nombre", "label", "Nombre"),
                Map.of("key", "posicion", "label", "Posición"),
                Map.of("key", "rol", "label", "Rol"));
        model.addAttribute("pageTitle", "Empleados");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", service.findAllAsMap());
        model.addAttribute("editUrl", null);
        model.addAttribute("deleteUrl", null);
        model.addAttribute("newUrl", null);
        model.addAttribute("entidad", "Empleado");
        return "pages/listado";
    }
}
