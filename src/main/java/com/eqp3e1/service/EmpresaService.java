package com.eqp3e1.service;

import com.eqp3e1.model.*;
import com.eqp3e1.repository.AlunoRepository;
import com.eqp3e1.repository.EmpresaRepository;
import com.eqp3e1.repository.EstagioRepository;
import com.eqp3e1.repository.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public void deletar(Long id) {
        empresaRepository.deleteById(id);
    }

    public void registrarOferta(Long empresaId, OfertaEstagio oferta) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.setOfertas(oferta);
            oferta.setEmpresa(empresa);
            ofertaEstagioService.salvar(oferta);
        } else {
            throw new RuntimeException("Empresa não encontrada.");
        }
    }

    public void cancelarOferta(Long ofertaId) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioService.buscarPorId(ofertaId);

        if (ofertaOpt.isPresent()) {

            ofertaEstagioService.deletar(ofertaId);
        } else {
            throw new RuntimeException("Oferta de Estágio não encontrada.");
        }
    }

}