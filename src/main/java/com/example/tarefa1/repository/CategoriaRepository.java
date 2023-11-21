package com.example.tarefa1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tarefa1.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    List<Categoria> findByNomeStartingWith(String nome);

    @Query("select cc from Categoria cc left join fetch cc.produtos c where cc.id = :id ")
    Categoria findCategoryById(@Param("id") Long id);
}
