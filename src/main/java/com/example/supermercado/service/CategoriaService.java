package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Categoria;
import com.example.supermercado.repository.CategoriaRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repo;

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Categoria findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }

    public Categoria save(Categoria c) {
        return repo.save(c);
    }

    public Categoria update(Integer id, Categoria datos) {
        Categoria c = findById(id);
        c.setNombre(datos.getNombre());
        return repo.save(c);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    /** Para la tabla dinámica del tutorial */
    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(c -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", c.getIdCategoria());
            m.put("nombre", c.getNombre());
            return m;
        }).toList();
    }
}
