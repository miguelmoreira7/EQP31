package com.eqp3e1.service;

import com.eqp3e1.model.*;
import com.eqp3e1.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EstagioService estagioService;

    public Empresa salvar(Empresa empresa) {
        if (empresa.getId() !=null ){
            Empresa existingEmpresa = empresaRepository.findById(empresa.getId())
            .orElseThrow(() ->  new RuntimeException("Empresa nao encontrada"));
            existingEmpresa.setCnpj(empresa.getCnpj());
            existingEmpresa.setNome(empresa.getNome());
            existingEmpresa.setTelefone(empresa.getTelefone());
            existingEmpresa.setEndereco(empresa.getEndereco());
            existingEmpresa.setEmail(empresa.getEmail());

            return empresaRepository.save(existingEmpresa);
        } else
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

    public Optional<Empresa> findByEmail(String email) {
        return empresaRepository.findByEmail(email);
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

    public void cancelarOferta(Long ofertaId, Long empresaId) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioService.buscarPorId(ofertaId);
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);

        if (ofertaOpt.isPresent() && empresaOpt.isPresent()) {
            ofertaEstagioService.deletar(ofertaId);
            empresaOpt.get().getOfertas().remove(ofertaOpt.get());
        } else {
            throw new RuntimeException("Oferta de Estágio não encontrada.");
        }
    }
}
