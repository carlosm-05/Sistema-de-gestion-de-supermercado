package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.service.VentaService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaViewController {

    private final VentaService service;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "fecha", "label", "Fecha"),
                Map.of("key", "cliente", "label", "Cliente"),
                Map.of("key", "empleado", "label", "Empleado"));
        model.addAttribute("pageTitle", "Ventas");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", service.findAllAsMap());
        model.addAttribute("editUrl", null);
        model.addAttribute("deleteUrl", null);
        model.addAttribute("newUrl", null);
        model.addAttribute("entidad", "Venta");
        return "pages/listado";
    }
}
