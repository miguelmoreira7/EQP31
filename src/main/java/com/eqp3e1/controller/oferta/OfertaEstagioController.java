package com.eqp3e1.controller.oferta;

import com.eqp3e1.model.Aluno;
import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.StatusOferta;
import com.eqp3e1.service.AlunoService;
import com.eqp3e1.service.EmpresaService;
import com.eqp3e1.service.HabilidadeService;
import com.eqp3e1.service.OfertaEstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/oferta")
public class OfertaEstagioController {

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private HabilidadeService habilidadeService;

    @GetMapping("/todas")
    public String listarOfertas(Model model) {
        List<OfertaEstagio> ofertas = ofertaEstagioService.listarTodas();
        List<Aluno> alunos = alunoService.listarTodos();
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("alunos", alunos);
        return "ofertas/ofertas";
    }

    @GetMapping("/registrar/{empresaId}")
    public String mostrarFormularioDeRegistro(@PathVariable Long empresaId, Model model) {
        OfertaEstagio ofertaEstagio = new OfertaEstagio();
        model.addAttribute("ofertaEstagio", ofertaEstagio);
        model.addAttribute("empresaId", empresaId); // Passando o ID da empresa para o formulário
        model.addAttribute("habilidadesDisponiveis", habilidadeService.listarTodas());
        return "ofertas/registrarOfertaEstagio";
    }

    @PostMapping("/salvar")
    public String salvarOfertaEstagio(@ModelAttribute OfertaEstagio ofertaEstagio, @RequestParam Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        System.out.println(empresaId);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            ofertaEstagio.setEmpresa(empresa);
            ofertaEstagio.setStatus(StatusOferta.ABERTA);
            empresaService.registrarOferta(empresaId, ofertaEstagio);
        }
        return "redirect:/oferta/todas"; // Redireciona para a página de ofertas após o salvamento
    }

    @PostMapping("/candidatar")
    public String candidatarAluno(@RequestParam("alunoId") Long alunoId,
                                  @RequestParam("ofertaId") Long ofertaId,
                                  Model model) {
        try {
            alunoService.candidatar(alunoId, ofertaId);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Erro ao candidatar-se: " + e.getMessage());
            return "redirect:/oferta/todas";
        }
        return "redirect:/oferta/todas";
    }

    @PostMapping("/cancelar")
    public String cancelarOferta(@RequestParam Long ofertaId, @RequestParam Long empresaId, RedirectAttributes redirectAttributes) {
        try {
            empresaService.cancelarOferta(ofertaId, empresaId);
            redirectAttributes.addFlashAttribute("successMessage", "Oferta de estágio cancelada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/oferta/todas"; // Redireciona para a página de ofertas após a exclusão
    }

    @GetMapping("/ficha/{id}")
    public String fichaOferta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("oferta", ofertaEstagioService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Oferta de Estágio não encontrada")));
        return "ofertas/ficha";
    }
    
    @GetMapping("/candidatos/{id}")
    public String candidatos(@PathVariable("id") Long id, Model model) {
        model.addAttribute("oferta", ofertaEstagioService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Oferta de Estágio não encontrada")));
        return "ofertas/candidatos";
    }
}
