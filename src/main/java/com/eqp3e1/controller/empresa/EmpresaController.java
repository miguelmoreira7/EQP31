package com.eqp3e1.controller.empresa;

import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.groups.OnUpdate;
import com.eqp3e1.service.EmpresaService;
import com.eqp3e1.service.HabilidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private HabilidadeService habilidadeService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("empresa", new Empresa());

        model.addAttribute("pageTitle", "Registro de Empresa");
        model.addAttribute("formTitle", "Registrar Empresa");
        model.addAttribute("submitButtonLabel", "Registrar");
        model.addAttribute("formAction", "/empresa/registrar");

        return "empresa/registroEmpresa";

    }

    @PostMapping("/registrar")
    public String registrarEmpresa(@Valid @ModelAttribute("empresa") Empresa empresa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("pageTitle", "Registro de Empresa");
            model.addAttribute("formTitle", "Registrar Empresa");
            model.addAttribute("submitButtonLabel", "Registrar");
            model.addAttribute("formAction", "/empresa/registrar");
            return "empresa/registroEmpresa";
        }
        empresaService.salvar(empresa);
        return "redirect:/empresa/todas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(id);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            model.addAttribute("empresa", empresa);

            // Add attributes for the template
            model.addAttribute("pageTitle", "Editar Empresa");
            model.addAttribute("formTitle", "Editar Empresa");
            model.addAttribute("submitButtonLabel", "Atualizar");
            model.addAttribute("formAction", "/empresa/atualizar");

            return "empresa/registroEmpresa";
        } else {
            model.addAttribute("errorMessage", "Empresa não encontrada.");
            return "error";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/atualizar")
    public String atualizarEmpresa(@Validated(OnUpdate.class) @ModelAttribute("empresa") Empresa empresa,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            // Log validation errors
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Validation error: " + error);
            });
            model.addAttribute("pageTitle", "Editar Empresa");
            model.addAttribute("formTitle", "Editar Empresa");
            model.addAttribute("submitButtonLabel", "Atualizar");
            model.addAttribute("formAction", "/empresa/atualizar");
            return "empresa/registroEmpresa";
        }
        empresaService.salvar(empresa);
        return "redirect:/empresa/todas";
    }


    @GetMapping("/todas")
    public String listarEmpresas(Model model) {
        List<Empresa> empresas = empresaService.listarTodas();
        model.addAttribute("empresas", empresas);
        return "empresa/listarEmpresas";
    }

    @GetMapping("/criar-oferta/{id}")
    public String criarOferta(@PathVariable Long id, Model model) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(id);
        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            OfertaEstagio novaOferta = new OfertaEstagio();
            novaOferta.setEmpresa(empresa);
            model.addAttribute("ofertaEstagio", novaOferta);
            model.addAttribute("habilidadesDisponiveis", habilidadeService.listarTodas());
            return "criar_oferta";
        }
        return "redirect:/empresa/todas";
    }

    @GetMapping("/ficha/{id}")
    public String fichaEmpresa(@PathVariable("id") Long id, Model model) {
        model.addAttribute("empresa", empresaService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Empresa não encontrada")));
        return "empresa/ficha";
    }
}
