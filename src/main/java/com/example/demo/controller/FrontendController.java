package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping("/{path:[^\\.]*}")
    public String forwardToFrontend() {
        return "forward:/build/index.html";
    }
}

