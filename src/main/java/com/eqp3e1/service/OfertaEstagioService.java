package com.eqp3e1.service;

import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.Estagio;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.Aluno;
import com.eqp3e1.repository.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfertaEstagioService {

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Autowired
    public EstagioService estagioService;

    public OfertaEstagio salvar(OfertaEstagio oferta) {
        return ofertaEstagioRepository.save(oferta);
    }

    public Optional<OfertaEstagio> buscarPorId(Long id) {
        return ofertaEstagioRepository.findById(id);
    }

    public List<OfertaEstagio> listarTodas() {
        return ofertaEstagioRepository.findAll();
    }

    public void deletar(Long id) {
        ofertaEstagioRepository.deleteById(id);
    }
    

    public Estagio converterEmEstagio(Long ofertaId, Aluno aluno, LocalDate dataInicio, LocalDate dataTermino) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioRepository.findById(ofertaId);

        if (ofertaOpt.isPresent()) {
            OfertaEstagio oferta = ofertaOpt.get();

            Estagio estagio = new Estagio();
            estagio.setAluno(aluno);
            estagio.setEmpresa(oferta.getEmpresa());
            estagio.setDataInicio(dataInicio);
            estagio.setDataTermino(dataTermino);
            estagio.setValorBolsa(oferta.getValorBolsa());

            ofertaEstagioRepository.delete(oferta);

            return estagio;
        } else {
            throw new RuntimeException("Oferta de Estágio não encontrada.");
        }
    }

    public void adicionarCandidato(OfertaEstagio oferta, Aluno aluno) {
        oferta.addCandidato(aluno);
        ofertaEstagioRepository.save(oferta);
    }

}
