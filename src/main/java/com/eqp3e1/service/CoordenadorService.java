package com.eqp3e1.service;

import com.eqp3e1.model.Coordenador;
import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.Aluno;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.Estagio;
import com.eqp3e1.repository.CoordenadorRepository;
import com.eqp3e1.repository.EmpresaRepository;
import com.eqp3e1.repository.EstagioRepository;
import com.eqp3e1.repository.OfertaEstagioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OfertaEstagioRepository ofertaEstagioRepository;

    @Autowired
    private EstagioRepository estagioRepository;

    public Coordenador salvar(Coordenador coordenador) {
        return coordenadorRepository.save(coordenador);
    }

    public Optional<Coordenador> buscarPorId(Long id) {
        return coordenadorRepository.findById(id);
    }

    public List<Coordenador> listarTodos() {
        return (List<Coordenador>) coordenadorRepository.findAll();
    }

    public void deletar(Long id) {
        coordenadorRepository.deleteById(id);
    }

    public void alterarCadastroEmpresa(Long empresaId, Empresa novaEmpresa) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(empresaId);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();
            empresa.atualizarDados(novaEmpresa);  // método não oficial de alteração de dados
            empresaRepository.save(empresa);
        } else {
            throw new RuntimeException("Empresa não encontrada.");
        }
    }

    public List<OfertaEstagio> acompanharOfertas() {
        return (List<OfertaEstagio>) ofertaEstagioRepository.findAll();
    }

    public List<Estagio> acompanharEstagios() {
        return (List<Estagio>) estagioRepository.findAll();
    }

    public List<Aluno> acompanharCandidatos(Long ofertaId) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioRepository.findById(ofertaId);

        if (ofertaOpt.isPresent()) {
            OfertaEstagio oferta = ofertaOpt.get();
            return oferta.getCandidaturas();  // método fictício para retornar candidatos
        } else {
            throw new RuntimeException("Oferta de Estágio não encontrada.");
        }
    }
}