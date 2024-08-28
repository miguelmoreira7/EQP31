package com.eqp3e1.controller.empresa;

import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/registroEmpresa";
    }

    @PostMapping("/registrar")
    public String registrarEmpresa(@Valid @ModelAttribute("empresa") Empresa empresa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
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