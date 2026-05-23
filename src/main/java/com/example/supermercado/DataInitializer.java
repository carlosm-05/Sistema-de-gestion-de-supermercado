package com.example.supermercado;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.supermercado.model.*;
import com.example.supermercado.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepo;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;
    private final ProveedorRepository proveedorRepo;
    private final VentaRepository ventaRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        // Categorías
        Categoria cat1 = categoriaRepo.save(new Categoria(null, "Electrodomésticos"));
        Categoria cat2 = categoriaRepo.save(new Categoria(null, "Ropa"));
        Categoria cat3 = categoriaRepo.save(new Categoria(null, "Alimentos"));

        // Productos
        productoRepo.save(new Producto(null, "Nevera 300L", new BigDecimal("1500000"), 5, cat1));
        productoRepo.save(new Producto(null, "Televisor 43 pulgadas", new BigDecimal("900000"), 8, cat1));
        productoRepo.save(new Producto(null, "Camiseta Básica", new BigDecimal("35000"), 50, cat2));
        productoRepo.save(new Producto(null, "Jean Clásico", new BigDecimal("80000"), 30, cat2));
        productoRepo.save(new Producto(null, "Arroz 5kg", new BigDecimal("18000"), 100, cat3));
        productoRepo.save(new Producto(null, "Aceite de cocina 1L", new BigDecimal("12000"), 80, cat3));

        // Clientes
        Cliente cli1 = clienteRepo.save(new Cliente(null, "Ana Torres", "3101234567", "ana@gmail.com"));
        Cliente cli2 = clienteRepo.save(new Cliente(null, "Luis Martínez", "3209876543", "luis@gmail.com"));
        Cliente cli3 = clienteRepo.save(new Cliente(null, "Sofia Ruiz", "3154567890", "sofia@gmail.com"));

        // Empleados
        if (empleadoRepo.count() == 0) {
            Empleado admin = new Empleado();
            admin.setNombre("Administrador");
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRol("ADMIN");
            admin.setPosicion("Gerente");
            empleadoRepo.save(admin);

            Empleado emp = new Empleado();
            emp.setNombre("Pedro Gómez");
            emp.setUsername("pedro");
            emp.setPassword(encoder.encode("pedro123"));
            emp.setRol("EMPLEADO");
            emp.setPosicion("Vendedor");
            empleadoRepo.save(emp);
        }

        // Proveedores
        proveedorRepo.save(new Proveedor(null, "Importadora Nacional", "6011234567", "ventas@importadora.co"));
        proveedorRepo.save(new Proveedor(null, "Distribuidora Centro", "3001122334", "info@distribuidora.co"));

        // Ventas
        Empleado emp = empleadoRepo.findAll().get(0);
        ventaRepo.save(new Venta(null, LocalDate.now(), cli1, emp, new java.util.ArrayList<>()));
        ventaRepo.save(new Venta(null, LocalDate.now().minusDays(1), cli2, emp, new java.util.ArrayList<>()));
        ventaRepo.save(new Venta(null, LocalDate.now().minusDays(2), cli3, emp, new java.util.ArrayList<>()));

        System.out.println("✅ Datos cargados. Abre: http://localhost:8080");
        System.out.println("   Usuario: admin / admin123");
    }
}
