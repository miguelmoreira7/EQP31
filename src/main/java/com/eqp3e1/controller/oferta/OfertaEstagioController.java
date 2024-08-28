package com.eqp3e1.controller.oferta;

import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.StatusOferta;
import com.eqp3e1.service.EmpresaService;
import com.eqp3e1.service.OfertaEstagioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/oferta-estagio")
public class OfertaEstagioController {

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/ofertas")
    public String listarOfertas(Model model) {
        List<OfertaEstagio> ofertas = ofertaEstagioService.listarTodas();
        model.addAttribute("ofertas", ofertas);
        return "ofertas";
    }

    @GetMapping("/registrar/{empresaId}")
    public String mostrarFormularioDeRegistro(@PathVariable Long empresaId, Model model) {
        OfertaEstagio ofertaEstagio = new OfertaEstagio();
        model.addAttribute("ofertaEstagio", ofertaEstagio);
        model.addAttribute("empresaId", empresaId); // Passando o ID da empresa para o formulário
        return "empresa/registrarOfertaEstagio";
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
            return "redirect:/ofertas";
        }
        return "redirect:/ofertas"; // Redireciona para a página de ofertas após o salvamento
    }
}
