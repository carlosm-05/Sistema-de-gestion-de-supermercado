package com.example.supermercado.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ThymeleafConfig {

    @ModelAttribute("currentRequest")
    public HttpServletRequest currentRequest(HttpServletRequest request) {
        return request;
    }
}
