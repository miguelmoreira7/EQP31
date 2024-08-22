package com.eqp3e1.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.eqp3e1.repository.AlunoRepository;
import com.eqp3e1.repository.OfertaEstagioRepository;

public class AlunoService {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private OfertaEstagioService ofertaestagioservice;

  @Autowired
  private OfertaEstagioRepository ofertaEstagioRepository;

  public Aluno salvar(Aluno aluno){
    return alunoRepository.save(aluno);
  }
  public Optional<Aluno> buscarPorId(Long id) {
    return alunoRepository.findById(id);
}

public List<Aluno> listarTodos() {
    return alunoRepository.findAll();
}

public void deletar(Long id) {
    alunoRepository.deleteById(id);
}

public void candidatar(Long alunoId, Long ofertaId) {

    Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
    Optional<OfertaEstagio> ofertaOpt = ofertaEstagioRepository.findById(ofertaId);

    if (alunoOpt.isPresent() && ofertaOpt.isPresent()) {
        Aluno aluno = alunoOpt.get();
        OfertaEstagio oferta = ofertaOpt.get();

        // Chama o serviço responsável pela lógica de candidatura
        ofertaEstagioService.adicionarCandidato(oferta, aluno);
        
    } else {
        throw new RuntimeException("Aluno ou Oferta de Estágio não encontrados.");
    }
}
public void adicionarEstagio(Long alunoId, Long estagioId) {}

public List<OfertaEstagio> consultarCandidaturas(Long alunoId) {
    Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
    if (alunoOpt.isPresent()) {
        return alunoOpt.get().getCandidaturas();
    } else {
        throw new RuntimeException("Aluno não encontrado.");
    }
}
}
