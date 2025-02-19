package io.github.octcarp.sustech.cs309.assignment.a1backend.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloTest {
    @RequestMapping(value = {"/", "/hello"})
    public String hello() {
        return "hello";
    }
}