package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Empleado;
import com.example.supermercado.repository.EmpleadoRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public List<Empleado> findAll() {
        return repo.findAll();
    }

    public Empleado findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + id));
    }

    public Empleado save(Empleado e) {
        return repo.save(e);
    }

    public Empleado update(Integer id, Empleado datos) {
        Empleado e = findById(id);
        e.setNombre(datos.getNombre());
        e.setPosicion(datos.getPosicion());
        return repo.save(e);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(e -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", e.getIdEmpleado());
            m.put("nombre", e.getNombre());
            m.put("posicion", e.getPosicion() != null ? e.getPosicion() : "—");
            m.put("rol", e.getRol() != null ? e.getRol() : "EMPLEADO");
            return m;
        }).toList();
    }
}
