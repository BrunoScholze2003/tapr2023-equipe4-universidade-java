package br.edu.univille.microservcurso.service;
import java.util.List;

import br.edu.univille.microservcurso.entity.Matricula;

public interface MatriculaService {
    public List<Matricula> getAll();
    public Matricula getById(String id);
    public Matricula saveNew(Matricula matricula);
    public Matricula delete(String id);
    public Matricula update(String id, Matricula matricula);
    public Matricula update(Matricula matricula);
}