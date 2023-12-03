
package br.edu.univille.microservcurso.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.univille.microservcurso.entity.Matricula;

@Repository
public interface MatriculaRepository
    extends CrudRepository<Matricula,String>{
    
}