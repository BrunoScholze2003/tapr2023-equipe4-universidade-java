package br.edu.univille.microservcurso.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.univille.microservcurso.entity.Curso;

@Repository
public interface CursoRepository 
    extends CrudRepository<Curso,String>{
    
}