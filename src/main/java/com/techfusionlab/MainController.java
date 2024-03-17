package com.techfusionlab;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/", "index", "index.html"})
    public String index() {
        return "index";
    }
}
