package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Categoria;
import com.example.supermercado.model.Producto;
import com.example.supermercado.repository.CategoriaRepository;
import com.example.supermercado.repository.ProductoRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository categoriaRepo;

    public List<Producto> findAll() {
        return repo.findAll();
    }

    public Producto findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public Producto save(Producto p) {
        return repo.save(p);
    }

    public Producto update(Integer id, Producto datos) {
        Producto p = findById(id);
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setExistencias(datos.getExistencias());
        p.setCategoria(datos.getCategoria());
        return repo.save(p);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Producto saveFromForm(String nombre, String precio, String existencias, Integer categoriaId) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(new java.math.BigDecimal(precio));
        p.setExistencias(Integer.parseInt(existencias));
        if (categoriaId != null) {
            categoriaRepo.findById(categoriaId).ifPresent(p::setCategoria);
        }
        return repo.save(p);
    }

    public Producto updateFromForm(Integer id, String nombre, String precio, String existencias, Integer categoriaId) {
        Producto p = findById(id);
        p.setNombre(nombre);
        p.setPrecio(new java.math.BigDecimal(precio));
        p.setExistencias(Integer.parseInt(existencias));
        Categoria cat = categoriaId != null ? categoriaRepo.findById(categoriaId).orElse(null) : null;
        p.setCategoria(cat);
        return repo.save(p);
    }

    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(p -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", p.getIdProducto());
            m.put("nombre", p.getNombre());
            m.put("precio", "$" + p.getPrecio());
            m.put("existencias", p.getExistencias());
            m.put("categoria", p.getCategoria() != null ? p.getCategoria().getNombre() : "—");
            return m;
        }).toList();
    }
}
