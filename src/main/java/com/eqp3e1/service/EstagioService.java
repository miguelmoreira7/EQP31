package com.eqp3e1.service;

import com.eqp3e1.model.Estagio;
import com.eqp3e1.repository.EstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstagioService {

    @Autowired
    private EstagioRepository estagioRepository;

    public Estagio salvar(Estagio estagio) {
        return estagioRepository.save(estagio);
    }

    public Optional<Estagio> buscarPorId(Long id) {
        return estagioRepository.findById(id);
    }

    public List<Estagio> listarTodos() {
        return (List<Estagio>) estagioRepository.findAll();
    }

    public void deletar(Long id) {
        estagioRepository.deleteById(id);
    }

    public void baixarTermoEstagio(Long estagioId) {
        Optional<Estagio> estagioOpt = estagioRepository.findById(estagioId);

        if (estagioOpt.isPresent()) {
            Estagio estagio = estagioOpt.get();
            // lógica para baixar termo de estágio (gerar PDF, por exemplo)
        } else {
            throw new RuntimeException("Estágio não encontrado.");
        }
    }
}
