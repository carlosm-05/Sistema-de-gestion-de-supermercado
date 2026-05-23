package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.dto.FormFieldDTO;
import com.example.supermercado.model.Proveedor;
import com.example.supermercado.service.ProveedorService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorViewController {

    private final ProveedorService service;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "nombre", "label", "Nombre"),
                Map.of("key", "telefono", "label", "Teléfono"),
                Map.of("key", "correo", "label", "Correo"));
        model.addAttribute("pageTitle", "Proveedores");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", service.findAllAsMap());
        model.addAttribute("editUrl", "/proveedores/form");
        model.addAttribute("deleteUrl", "/proveedores/eliminar");
        model.addAttribute("newUrl", "/proveedores/form");
        model.addAttribute("entidad", "Proveedor");
        return "pages/listado";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        boolean esEdicion = id != null;
        Proveedor p = esEdicion ? service.findById(id) : new Proveedor();

        List<FormFieldDTO> fields = List.of(
                new FormFieldDTO("text", "nombre", "Nombre", p.getNombre(), true, null,
                        "maxlength=\"100\""),
                new FormFieldDTO("text", "telefono", "Teléfono", p.getTelefono(), false, null,
                        "maxlength=\"20\""),
                new FormFieldDTO("email", "correoElectronico", "Correo", p.getCorreoElectronico(), false, null,
                        "maxlength=\"100\""));

        model.addAttribute("pageTitle", esEdicion ? "Editar Proveedor" : "Nuevo Proveedor");
        model.addAttribute("fields", fields);
        model.addAttribute("formAction", esEdicion ? "/proveedores/guardar?id=" + id : "/proveedores/guardar");
        model.addAttribute("submitLabel", esEdicion ? "Actualizar" : "Crear");
        model.addAttribute("backUrl", "/proveedores");
        return "pages/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer id,
            @RequestParam String nombre,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String correoElectronico,
            Model model) {
        if (nombre == null || nombre.isBlank()) {
            model.addAttribute("error", "El nombre es obligatorio");
            return form(id, model);
        }
        if (nombre.length() > 100) {
            model.addAttribute("error", "El nombre no puede tener más de 100 caracteres");
            return form(id, model);
        }
        Proveedor p = id != null ? service.findById(id) : new Proveedor();
        p.setNombre(nombre.trim());
        p.setTelefono(telefono != null ? telefono.trim() : null);
        p.setCorreoElectronico(correoElectronico != null ? correoElectronico.trim() : null);
        service.save(p);
        return "redirect:/proveedores";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/proveedores";
    }
}
