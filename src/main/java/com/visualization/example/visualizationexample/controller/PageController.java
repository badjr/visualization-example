package com.visualization.example.visualizationexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/visualization")
    public String getVisualizationPage() {
        return "index.html";
    }
}
