package com.example.tarefa1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tbl_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prod_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "prod_prec", length = 200, nullable = false)
    private Double preco;

    @ManyToOne
    @JoinColumn(name =  "categoriaProdutos_id")
    private Categoria categoriaProdutos;

    public Produto(String nome, Double preco){
        this.nome = nome;
        this.preco = preco;
    }
}
