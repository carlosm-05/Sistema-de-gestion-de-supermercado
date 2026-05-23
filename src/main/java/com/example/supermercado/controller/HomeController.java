package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.supermercado.repository.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final VentaRepository ventaRepo;
    private final EmpleadoRepository empleadoRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Inicio");
        model.addAttribute("totalProductos", productoRepo.count());
        model.addAttribute("totalClientes", clienteRepo.count());
        model.addAttribute("totalVentas", ventaRepo.count());
        model.addAttribute("totalEmpleados", empleadoRepo.count());
        return "pages/inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }
}
