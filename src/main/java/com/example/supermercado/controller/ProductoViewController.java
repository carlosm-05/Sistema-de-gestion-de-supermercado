package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.dto.FormFieldDTO;
import com.example.supermercado.dto.OptionDTO;
import com.example.supermercado.model.Producto;
import com.example.supermercado.service.CategoriaService;
import com.example.supermercado.service.ProductoService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoViewController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping
    public String index(Model model) {
        List<Map<String, Object>> columnas = List.of(
                Map.of("key", "id", "label", "ID"),
                Map.of("key", "nombre", "label", "Nombre"),
                Map.of("key", "precio", "label", "Precio"),
                Map.of("key", "existencias", "label", "Existencias"),
                Map.of("key", "categoria", "label", "Categoría"));
        model.addAttribute("pageTitle", "Productos");
        model.addAttribute("columnas", columnas);
        model.addAttribute("filas", productoService.findAllAsMap());
        model.addAttribute("editUrl", "/productos/form");
        model.addAttribute("deleteUrl", "/productos/eliminar");
        model.addAttribute("newUrl", "/productos/form");
        model.addAttribute("entidad", "Producto");
        return "pages/listado";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Integer id, Model model) {
        boolean esEdicion = id != null;
        Producto p = esEdicion ? productoService.findById(id) : new Producto();

        List<OptionDTO> catOpts = categoriaService.findAll().stream()
                .map(c -> new OptionDTO(String.valueOf(c.getIdCategoria()), c.getNombre()))
                .toList();

        List<FormFieldDTO> fields = List.of(
                new FormFieldDTO("text", "nombre", "Nombre", p.getNombre(), true, null,
                        "maxlength=\"100\""),
                new FormFieldDTO("number", "precio", "Precio",
                        p.getPrecio() != null ? p.getPrecio().toPlainString() : "", true, null,
                        "min=\"0\" step=\"0.01\""),
                new FormFieldDTO("number", "existencias", "Existencias",
                        p.getExistencias() != null ? p.getExistencias().toString() : "0", true, null,
                        "min=\"0\" step=\"1\""),
                new FormFieldDTO("select", "categoriaId", "Categoría",
                        p.getCategoria() != null ? String.valueOf(p.getCategoria().getIdCategoria()) : "",
                        false, catOpts, null));

        model.addAttribute("pageTitle", esEdicion ? "Editar Producto" : "Nuevo Producto");
        model.addAttribute("fields", fields);
        model.addAttribute("formAction", esEdicion ? "/productos/guardar?id=" + id : "/productos/guardar");
        model.addAttribute("submitLabel", esEdicion ? "Actualizar" : "Crear");
        model.addAttribute("backUrl", "/productos");
        return "pages/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer id,
            @RequestParam String nombre,
            @RequestParam String precio,
            @RequestParam String existencias,
            @RequestParam(required = false) Integer categoriaId,
            Model model) {
        if (nombre == null || nombre.isBlank()) {
            model.addAttribute("error", "El nombre es obligatorio");
            return form(id, model);
        }
        if (nombre.length() > 100) {
            model.addAttribute("error", "El nombre no puede tener más de 100 caracteres");
            return form(id, model);
        }
        if (precio == null || precio.isBlank()) {
            model.addAttribute("error", "El precio es obligatorio");
            return form(id, model);
        }
        try {
            new java.math.BigDecimal(precio);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El precio debe ser un número válido");
            return form(id, model);
        }
        if (existencias == null || existencias.isBlank()) {
            model.addAttribute("error", "Las existencias son obligatorias");
            return form(id, model);
        }
        try {
            int ex = Integer.parseInt(existencias);
            if (ex < 0) {
                model.addAttribute("error", "Las existencias no pueden ser negativas");
                return form(id, model);
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Las existencias deben ser un número entero");
            return form(id, model);
        }
        if (id != null) {
            productoService.updateFromForm(id, nombre.trim(), precio, existencias, categoriaId);
        } else {
            productoService.saveFromForm(nombre.trim(), precio, existencias, categoriaId);
        }
        return "redirect:/productos";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer id) {
        productoService.delete(id);
        return "redirect:/productos";
    }
}
