package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.dto.FormFieldDTO;
import com.example.supermercado.model.Categoria;
import com.example.supermercado.service.CategoriaService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaViewController {

    private final CategoriaService service;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "nombre", "label", "Nombre"));
        model.addAttribute("pageTitle", "Categorías");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", service.findAllAsMap());
        model.addAttribute("editUrl", "/categorias/form");
        model.addAttribute("deleteUrl", "/categorias/eliminar");
        model.addAttribute("newUrl", "/categorias/form");
        model.addAttribute("entidad", "Categoría");
        return "pages/listado";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        boolean esEdicion = id != null;
        Categoria cat = esEdicion ? service.findById(id) : new Categoria();

        List<FormFieldDTO> fields = List.of(
                new FormFieldDTO("text", "nombre", "Nombre", cat.getNombre(), true, null,
                        "maxlength=\"100\""));

        model.addAttribute("pageTitle", esEdicion ? "Editar Categoría" : "Nueva Categoría");
        model.addAttribute("fields", fields);
        model.addAttribute("formAction", esEdicion ? "/categorias/guardar?id=" + id : "/categorias/guardar");
        model.addAttribute("submitLabel", esEdicion ? "Actualizar" : "Crear");
        model.addAttribute("backUrl", "/categorias");
        return "pages/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer id,
            @RequestParam String nombre, Model model) {
        if (nombre == null || nombre.isBlank()) {
            model.addAttribute("error", "El nombre es obligatorio");
            return form(id, model);
        }
        if (nombre.length() > 100) {
            model.addAttribute("error", "El nombre no puede tener más de 100 caracteres");
            return form(id, model);
        }
        if (id != null) {
            Categoria c = service.findById(id);
            c.setNombre(nombre.trim());
            service.save(c);
        } else {
            service.save(new Categoria(null, nombre.trim()));
        }
        return "redirect:/categorias";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id) {
        service.delete(id);
        return "redirect:/categorias";
    }
}
