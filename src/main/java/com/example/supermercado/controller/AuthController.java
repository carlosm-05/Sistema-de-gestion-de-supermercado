package com.example.supermercado.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermercado.dto.LoginRequest;
import com.example.supermercado.dto.LoginResponse;
import com.example.supermercado.model.Empleado;
import com.example.supermercado.repository.EmpleadoRepository;
import com.example.supermercado.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final EmpleadoRepository empleadoRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Empleado empleado = empleadoRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(empleado);
        return ResponseEntity
                .ok(new LoginResponse(token, empleado.getNombre(), empleado.getRol(), empleado.getIdEmpleado()));
    }

    // Endpoint para registrar empleados con contraseña (solo ADMIN)
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Empleado empleado) {
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        if (empleado.getRol() == null)
            empleado.setRol("EMPLEADO");
        empleadoRepo.save(empleado);
        return ResponseEntity.ok("Empleado registrado correctamente");
    }
}
