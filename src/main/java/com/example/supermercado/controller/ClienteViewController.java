package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.dto.FormFieldDTO;
import com.example.supermercado.model.Cliente;
import com.example.supermercado.service.ClienteService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteViewController {

    private final ClienteService service;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "nombre", "label", "Nombre"),
                Map.of("key", "telefono", "label", "Teléfono"),
                Map.of("key", "correo", "label", "Correo"));
        model.addAttribute("pageTitle", "Clientes");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", service.findAllAsMap());
        model.addAttribute("editUrl", "/clientes/form");
        model.addAttribute("deleteUrl", "/clientes/eliminar");
        model.addAttribute("newUrl", "/clientes/form");
        model.addAttribute("entidad", "Cliente");
        return "pages/listado";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        boolean esEdicion = id != null;
        Cliente c = esEdicion ? service.findById(id) : new Cliente();

        List<FormFieldDTO> fields = List.of(
                new FormFieldDTO("text", "nombre", "Nombre", c.getNombre(), true, null,
                        "maxlength=\"100\""),
                new FormFieldDTO("text", "telefono", "Teléfono", c.getTelefono(), false, null,
                        "maxlength=\"20\""),
                new FormFieldDTO("email", "correoElectronico", "Correo", c.getCorreoElectronico(), false, null,
                        "maxlength=\"100\""));

        model.addAttribute("pageTitle", esEdicion ? "Editar Cliente" : "Nuevo Cliente");
        model.addAttribute("fields", fields);
        model.addAttribute("formAction", esEdicion ? "/clientes/guardar?id=" + id : "/clientes/guardar");
        model.addAttribute("submitLabel", esEdicion ? "Actualizar" : "Crear");
        model.addAttribute("backUrl", "/clientes");
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
        Cliente c = id != null ? service.findById(id) : new Cliente();
        c.setNombre(nombre.trim());
        c.setTelefono(telefono != null ? telefono.trim() : null);
        c.setCorreoElectronico(correoElectronico != null ? correoElectronico.trim() : null);
        service.save(c);
        return "redirect:/clientes";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/clientes";
    }
}
