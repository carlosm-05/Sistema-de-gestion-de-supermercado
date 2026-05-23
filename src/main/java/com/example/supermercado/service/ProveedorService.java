package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Proveedor;
import com.example.supermercado.repository.ProveedorRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository repo;

    public List<Proveedor> findAll() {
        return repo.findAll();
    }

    public Proveedor findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado: " + id));
    }

    public Proveedor save(Proveedor p) {
        return repo.save(p);
    }

    public Proveedor update(Integer id, Proveedor datos) {
        Proveedor p = findById(id);
        p.setNombre(datos.getNombre());
        p.setTelefono(datos.getTelefono());
        p.setCorreoElectronico(datos.getCorreoElectronico());
        return repo.save(p);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(p -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", p.getIdProveedor());
            m.put("nombre", p.getNombre());
            m.put("telefono", p.getTelefono() != null ? p.getTelefono() : "—");
            m.put("correo", p.getCorreoElectronico() != null ? p.getCorreoElectronico() : "—");
            return m;
        }).toList();
    }
}
