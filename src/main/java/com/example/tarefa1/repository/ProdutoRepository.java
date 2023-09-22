package com.example.tarefa1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tarefa1.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    List<Produto> findByPrecoGreaterThan(Double valor);

    List<Produto> findByPrecoLessThanEqual(Double valor);

    List<Produto> findByNomeStartingWith(String nome);

}
