package com.example.supermercado.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.supermercado.model.Empleado;
import com.example.supermercado.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final EmpleadoRepository empleadoRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registrar")
    public String form() {
        return "pages/registro";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombre,
                            @RequestParam String posicion,
                            @RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        if (nombre.isBlank() || posicion.isBlank() || username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Todos los campos son obligatorios");
            return "pages/registro";
        }
        if (username.length() < 3) {
            model.addAttribute("error", "El usuario debe tener al menos 3 caracteres");
            return "pages/registro";
        }
        if (password.length() < 4) {
            model.addAttribute("error", "La contraseña debe tener al menos 4 caracteres");
            return "pages/registro";
        }
        if (empleadoRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "El usuario ya existe");
            return "pages/registro";
        }

        Empleado emp = new Empleado();
        emp.setNombre(nombre.trim());
        emp.setPosicion(posicion.trim());
        emp.setUsername(username.trim());
        emp.setPassword(passwordEncoder.encode(password));
        emp.setRol("EMPLEADO");
        empleadoRepo.save(emp);

        model.addAttribute("success", "Cuenta creada correctamente. Ahora puedes iniciar sesión.");
        return "pages/registro";
    }
}
