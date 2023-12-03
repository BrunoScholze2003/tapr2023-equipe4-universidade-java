package br.edu.univille.microservcurso.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.univille.microservcurso.entity.Curso;
import br.edu.univille.microservcurso.entity.Matricula;
import br.edu.univille.microservcurso.repository.CursoRepository;
import br.edu.univille.microservcurso.service.CursoService;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@Service
public class CursoServiceImpl implements CursoService{
    private DaprClient client = new DaprClientBuilder().build();
    @Value("${app.component.topic.curso}")
    private String TOPIC_NAME;
    @Value("${app.component.service}")
    private String PUBSUB_NAME;

    @Autowired
    private CursoRepository repository;

    @Override
    public List<Curso> getAll() {
        var iterador = repository.findAll();
        List<Curso> listaCursos = new ArrayList<>();

        iterador.forEach(listaCursos::add);

        return listaCursos;
    }

    @Override
    public Curso getById(String id) {
        var curso = repository.findById(id);
        if(curso.isPresent())
            return curso.get();
        return null;
    }

    @Override
    public Curso saveNew(Curso curso) {
        curso.setId(null);
        curso = repository.save(curso);
        publicarAtualizacao(curso);
        return curso;
    }

    @Override
    public Curso update(String id, Curso curso) {
        var buscaCursoAntigo = repository.findById(id);
        if (buscaCursoAntigo.isPresent()){
            var cursoAntigo = buscaCursoAntigo.get();

            //Atualizar cada atributo do objeto antigo 
            cursoAntigo.setName(curso.getName());
            
        }
        return null;
    }

    @Override
    public Curso delete(String id) {
        var buscaCurso = repository.findById(id);
        if (buscaCurso.isPresent()){
            var curso = buscaCurso.get();

            repository.delete(curso);

            return curso;
        }
        return null;
    }

    @Override
    public Curso update(Curso curso) {
        return repository.save(curso);
        
    }

    private void publicarAtualizacao(Curso curso){
        client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_NAME,
                    curso).block();
    }
}