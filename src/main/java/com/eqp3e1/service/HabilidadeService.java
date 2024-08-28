package com.eqp3e1.service;

import com.eqp3e1.model.Habilidade;
import com.eqp3e1.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadeService {

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    public Habilidade salvar(Habilidade habilidade) {
        return habilidadeRepository.save(habilidade);
    }

    public Optional<Habilidade> buscarPorId(Long id) {
        return habilidadeRepository.findById(id);
    }

    public List<Habilidade> listarTodas() {
        return (List<Habilidade>) habilidadeRepository.findAll();
    }

    public void deletar(Long id) {
        habilidadeRepository.deleteById(id);
    }
}