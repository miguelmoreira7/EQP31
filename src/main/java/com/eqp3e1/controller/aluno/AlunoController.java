package com.eqp3e1.controller.aluno;

import com.eqp3e1.model.Aluno;
import com.eqp3e1.service.AlunoService;
import com.eqp3e1.service.HabilidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private HabilidadeService habilidadeService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model){
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("habilidadesDisponiveis", habilidadeService.listarTodas());
        return "aluno/registro";
    }

    @PostMapping("/registrar")
    public String registerAluno(@Valid @ModelAttribute("aluno") Aluno aluno, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("habilidadesDisponiveis", habilidadeService.listarTodas());
            return "aluno/registro";
        }

        alunoService.salvar(aluno);
        return "redirect:/aluno/todos";
    }

    @GetMapping("/todos")
    public String listarAlunos(Model model){
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("alunos", alunos);
        return "aluno/listar";
    }

}
