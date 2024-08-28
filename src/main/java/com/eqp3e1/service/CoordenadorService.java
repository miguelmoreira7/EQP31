package com.eqp3e1.service;

import com.eqp3e1.model.Coordenador;
import com.eqp3e1.model.Empresa;
import com.eqp3e1.model.Aluno;
import com.eqp3e1.model.OfertaEstagio;
import com.eqp3e1.model.Estagio;
import com.eqp3e1.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordenadorService {

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private OfertaEstagioService ofertaEstagioService;

    @Autowired
    private EstagioService estagioService;

    @Autowired
    private EmpresaService empresaService;

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
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get();

            // Atualizar os campos da empresa com os dados da novaEmpresa
            empresa.setNome(novaEmpresa.getNome());
            empresa.setCnpj(novaEmpresa.getCnpj());
            empresa.setEndereco(novaEmpresa.getEndereco());
            empresa.setTelefone(novaEmpresa.getTelefone());
            empresa.setEmail(novaEmpresa.getEmail());

            // Salvar as alterações no banco de dados
            empresaService.salvar(empresa);
        } else {
            throw new RuntimeException("Empresa não encontrada.");
        }
    }

    public List<OfertaEstagio> acompanharOfertas() {
        return (List<OfertaEstagio>) ofertaEstagioService.listarTodas();
    }

    public List<Estagio> acompanharEstagios() {
        return (List<Estagio>) estagioService.listarTodos();
    }

    public List<Aluno> acompanharCandidatos(Long ofertaId) {
        Optional<OfertaEstagio> ofertaOpt = ofertaEstagioService.buscarPorId(ofertaId);

        if (ofertaOpt.isPresent()) {
            OfertaEstagio oferta = ofertaOpt.get();
            return oferta.getCandidaturas();  // método fictício para retornar candidatos
        } else {
            throw new RuntimeException("Oferta de Estágio não encontrada.");
        }
    }
}
