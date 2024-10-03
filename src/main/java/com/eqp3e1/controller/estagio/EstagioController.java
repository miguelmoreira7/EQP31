package com.eqp3e1.controller.estagio;

import com.eqp3e1.model.Estagio;
import com.eqp3e1.service.EstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/estagio")
public class EstagioController {

    @Autowired
    private EstagioService estagioService;

    @GetMapping("/todos")
    public String listarEstagios(Model model) {
        List<Estagio> estagios = estagioService.listarTodos();

        model.addAttribute("estagios", estagios);

        return "estagio/estagios";
    }


}
