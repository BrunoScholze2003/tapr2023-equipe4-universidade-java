package br.edu.univille.microservcurso.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.univille.microservcurso.entity.Curso;
import br.edu.univille.microservcurso.repository.CursoRepository;
import br.edu.univille.microservcurso.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService{

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
        return repository.save(curso);
    }

    @Override
    public Curso update(String id, Curso curso) {
        var buscaCursoAntigo = repository.findById(id);
        if (buscaCursoAntigo.isPresent()){
            var cursoAntigo = buscaCursoAntigo.get();

            //Atualizar cada atributo do objeto antigo 
            cursoAntigo.setNome(curso.getNome());
            
            return repository.save(cursoAntigo);
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
}