package com.eqp3e1.controller.oferta;

import com.eqp3e1.model.*;
import com.eqp3e1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/oferta")
public class OfertaEstagioController {

    @Autowired
    private EstagioService estagioService;

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
        model.addAttribute("ofertas", ofertas);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String
                        && authentication.getPrincipal().equals("anonymousUser"))) {
            String username = authentication.getName();

            Optional<Aluno> alunoOpt = alunoService.findByUsername(username);
            if (alunoOpt.isPresent()) {
                Aluno aluno = alunoOpt.get();
                model.addAttribute("alunoId", aluno.getId());
            }
        }

        return "ofertas/ofertas";
    }

    @GetMapping("/registrar")
    public String mostrarFormularioDeRegistro(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String
                        && authentication.getPrincipal().equals("anonymousUser"))) {
            String username = authentication.getName();

            Optional<Empresa> empresaOpt = empresaService.findByEmail(username);
            if (empresaOpt.isPresent()) {
                Empresa empresa = empresaOpt.get();
                OfertaEstagio ofertaEstagio = new OfertaEstagio();

                model.addAttribute("ofertaEstagio", ofertaEstagio);
                model.addAttribute("empresaId", empresa.getId()); // Passando o ID da empresa para o formulário
                model.addAttribute("habilidadesDisponiveis", habilidadeService.listarTodas());
            }
        }
        return "ofertas/registrarOfertaEstagio";
    }

    @PostMapping("/salvar")
    public String salvarOfertaEstagio(@ModelAttribute OfertaEstagio ofertaEstagio, @RequestParam Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            ofertaEstagio.setEmpresa(empresa);
            ofertaEstagio.setStatus(StatusOferta.ABERTA);
            empresaService.registrarOferta(empresaId, ofertaEstagio);
        }
        return "redirect:/oferta/todas"; // Redireciona para a página de ofertas após o salvamento
    }

    @PostMapping("/candidatar")
    @PreAuthorize("isAuthenticated()")
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
    public String cancelarOferta(@RequestParam Long ofertaId, @RequestParam Long empresaId,
            RedirectAttributes redirectAttributes) {
        try {
            empresaService.cancelarOferta(ofertaId, empresaId);
            redirectAttributes.addFlashAttribute("successMessage", "Oferta de estágio cancelada com sucesso.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/oferta/todas"; // Redireciona para a página de ofertas após a exclusão
    }

    @GetMapping("/{ofertaId}/candidatos")
    public String fichaOferta(@PathVariable Long ofertaId, Model model) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioService.buscarPorId(ofertaId);
        if (ofertaOpt.isPresent()) {
            OfertaEstagio oferta = ofertaOpt.get();

            // Retrieve the list of candidate students for this offer
            List<Aluno> candidatos = oferta.getCandidaturas();

            // Add the offer and candidates to the model
            model.addAttribute("oferta", oferta);
            model.addAttribute("candidatos", candidatos);

        }
        return "ofertas/listarCandidatos";
    }

    @GetMapping("/candidatos/{id}")
    public String candidatos(@PathVariable("id") Long id, Model model) {
        model.addAttribute("oferta", ofertaEstagioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Oferta de Estágio não encontrada")));
        return "ofertas/candidatos";
    }

    @GetMapping("/minhas-ofertas")
    public String minhasOfertas(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String
                        && authentication.getPrincipal().equals("anonymousUser"))) {
            String username = authentication.getName();

            Optional<Empresa> empresaOpt = empresaService.findByEmail(username);
            if (empresaOpt.isPresent()) {
                Empresa empresa = empresaOpt.get();
                List<OfertaEstagio> candidaturas = empresa.getOfertas();
                model.addAttribute("empresa", empresa);
                model.addAttribute("ofertas", candidaturas);
                return "ofertas/minhasOfertas";
            } else {
                throw new RuntimeException("Aluno não encontrado.");
            }
        }
        return "ofertas/minhasOfertas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{ofertaId}/converterEmEstagio")
    public String selecionarCandidato(
            @PathVariable Long ofertaId,
            @RequestParam("alunoId") Long alunoId,
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataTermino") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataTermino) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioService.buscarPorId(ofertaId);

        if (ofertaOpt.isPresent()) {
            OfertaEstagio oferta = ofertaOpt.get();
            Optional<Aluno> alunoOpt = alunoService.buscarPorId(alunoId);
            if (alunoOpt.isPresent()) {
                Aluno aluno = alunoOpt.get();

                Estagio estagio = ofertaEstagioService.converterEmEstagio(ofertaId, aluno, dataInicio, dataTermino);

                estagioService.salvar(estagio);

                Empresa empresa = oferta.getEmpresa();
                empresa.getEstagios().add(estagio);
                empresaService.salvar(empresa);

            } else {
                throw new RuntimeException("Oferta de Estágio ou Aluno não encontrados.");
            }
        }
        ofertaEstagioService.deletar(ofertaId);

        return "redirect:/estagio/todos";
    }

}
