package com.example.supermercado.controller;

import java.util.List;

import org.springframework.http.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.supermercado.dto.EmpleadoDTO;
import com.example.supermercado.model.Empleado;
import com.example.supermercado.service.EmpleadoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public List<EmpleadoDTO> getAll() {
        return service.findAll().stream()
                .map(e -> new EmpleadoDTO(e.getIdEmpleado(), e.getNombre(), e.getPosicion()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> getById(@PathVariable Integer id) {
        Empleado e = service.findById(id);
        return ResponseEntity.ok(new EmpleadoDTO(e.getIdEmpleado(), e.getNombre(), e.getPosicion()));
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> create(@RequestBody Empleado empleado) {
        Empleado saved = service.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new EmpleadoDTO(saved.getIdEmpleado(), saved.getNombre(), saved.getPosicion()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Integer id, @RequestBody Empleado datos) {
        Empleado updated = service.update(id, datos);
        return ResponseEntity.ok(new EmpleadoDTO(updated.getIdEmpleado(), updated.getNombre(), updated.getPosicion()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}