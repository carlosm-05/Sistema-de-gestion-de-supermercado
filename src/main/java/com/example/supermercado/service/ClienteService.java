package com.example.supermercado.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.supermercado.model.Cliente;
import com.example.supermercado.repository.ClienteRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repo;

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
    }

    public Cliente save(Cliente c) {
        return repo.save(c);
    }

    public Cliente update(Integer id, Cliente datos) {
        Cliente c = findById(id);
        c.setNombre(datos.getNombre());
        c.setTelefono(datos.getTelefono());
        c.setCorreoElectronico(datos.getCorreoElectronico());
        return repo.save(c);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> findAllAsMap() {
        return repo.findAll().stream().map(c -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", c.getIdCliente());
            m.put("nombre", c.getNombre());
            m.put("telefono", c.getTelefono() != null ? c.getTelefono() : "—");
            m.put("correo", c.getCorreoElectronico() != null ? c.getCorreoElectronico() : "—");
            return m;
        }).toList();
    }
}
