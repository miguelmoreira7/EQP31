package com.eqp3e1.controller.aluno;

import com.eqp3e1.model.Aluno;
import com.eqp3e1.model.Habilidade;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.service.AlunoService;
import com.eqp3e1.service.HabilidadeService;
import com.eqp3e1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private HabilidadeService habilidadeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

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
        UserDetails user = User.withUsername(aluno.getUsername()).password(new BCryptPasswordEncoder().encode("a")).roles("USER").build();
        userService.save(user);

        return "redirect:/aluno/todos";
    }

    @GetMapping("/todos")
    public String listarAlunos(Model model){
        List<Aluno> alunos = alunoService.listarAlunosComCandidaturas();
        model.addAttribute("alunos", alunos);
        return "aluno/listar";
    }

    @GetMapping("/ficha/{id}")
    public String fichaAluno(@PathVariable("id") Long id, Model model) {
        Optional<Aluno> alunoOpt = alunoService.buscarPorId(id);
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            List<OfertaEstagio> candidaturas = aluno.getCandidaturas();
            model.addAttribute("aluno", alunoService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado")));
            model.addAttribute("candidaturas", candidaturas);
            return "aluno/ficha";
        } else {
            throw new RuntimeException("Aluno não encontrado.");
        }
    }
}
