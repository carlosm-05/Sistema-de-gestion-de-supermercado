package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Venta;
import com.example.supermercado.repository.VentaRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository repo;

    public List<Venta> findAll() {
        return repo.findAll();
    }

    public Venta findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }

    public Venta save(Venta v) {
        return repo.save(v);
    }

    public Venta update(Integer id, Venta datos) {
        Venta v = findById(id);
        v.setFecha(datos.getFecha());
        v.setCliente(datos.getCliente());
        v.setEmpleado(datos.getEmpleado());
        return repo.save(v);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(v -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", v.getIdVenta());
            m.put("fecha", v.getFecha().toString());
            m.put("cliente", v.getCliente() != null ? v.getCliente().getNombre() : "—");
            m.put("empleado", v.getEmpleado() != null ? v.getEmpleado().getNombre() : "—");
            return m;
        }).toList();
    }
}
