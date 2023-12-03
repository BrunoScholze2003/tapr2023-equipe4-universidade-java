package br.edu.univille.microservcurso.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.edu.univille.microservcurso.entity.Curso;
import br.edu.univille.microservcurso.entity.Matricula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.univille.microservcurso.repository.MatriculaRepository;
import br.edu.univille.microservcurso.service.MatriculaService;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@Service
public class MatriculaServiceImpl implements MatriculaService {
    private DaprClient client = new DaprClientBuilder().build();
    @Value("${app.component.topic.matricula}")
    private String TOPIC_NAME;
    @Value("${app.component.service}")
	private String PUBSUB_NAME;


    @Autowired
    private MatriculaRepository repository;

    @Override
    public List<Matricula> getAll() {
        var iterador = repository.findAll();
        List<Matricula> listaMatriculas = new ArrayList<>();

        iterador.forEach(listaMatriculas::add);

        return listaMatriculas;
    }

    @Override
    public Matricula getById(String id) {
        var matricula = repository.findById(id);
        if(matricula.isPresent())
            return matricula.get();
        return null;
    }

    @Override
    public Matricula saveNew(Matricula matricula) {
        matricula.setId(null);
        matricula = repository.save(matricula);
        publicarAtualizacao(matricula);
        return matricula;
    }

    @Override
    public Matricula update(String id, Matricula matricula) {
        var buscaMatriculaAntigo = repository.findById(id);
        if (buscaMatriculaAntigo.isPresent()){
            var matriculaAntigo = buscaMatriculaAntigo.get();

            //Atualizar cada atributo do objeto antigo 
            matriculaAntigo.setNumero(matricula.getNumero());
            matriculaAntigo = repository.save(matriculaAntigo);
            publicarAtualizacao(matriculaAntigo);
            return matriculaAntigo;
        }
        return null;
    }


    @Override
    public Matricula delete(String id) {
        var buscaMatricula = repository.findById(id);
        if (buscaMatricula.isPresent()){
            var matricula = buscaMatricula.get();

            repository.delete(matricula);

            return matricula;
        }
        return null;
    }

    @Override
    public Matricula update(Matricula matricula) {
        return repository.save(matricula);
        
    }

    private void publicarAtualizacao(Matricula matricula){
        client.publishEvent(
					PUBSUB_NAME,
					TOPIC_NAME,
					matricula).block();
    }

}