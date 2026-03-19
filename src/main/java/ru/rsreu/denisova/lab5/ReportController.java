package ru.rsreu.denisova.lab5;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    @Autowired
    private Birt birt;

    @GetMapping("/report")
    public String page() {
        return "report";
    }

    @PostMapping("/report")
    public void generate(@RequestParam(required = false) String id,
                         HttpServletResponse response,
                         HttpServletRequest request) {

        birt.generatePDF(id, response, request);
    }
}
