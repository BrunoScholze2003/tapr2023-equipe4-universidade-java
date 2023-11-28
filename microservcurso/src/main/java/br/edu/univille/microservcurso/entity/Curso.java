package br.edu.univille.microservcurso.entity;

import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

@Container(containerName = "curso")
public class Curso {
    @Id
    @GeneratedValue
    public String id;
    @PartitionKey
    public String name;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return name;
    }
    public void setNome(String name) {
        this.name = name;
    }
}

    
