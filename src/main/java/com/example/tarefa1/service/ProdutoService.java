package com.example.tarefa1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tarefa1.dto.ProdutoDTO;
import com.example.tarefa1.model.Categoria;
import com.example.tarefa1.model.Produto;
import com.example.tarefa1.repository.CategoriaRepository;
import com.example.tarefa1.repository.ProdutoRepository;

@Service
public class ProdutoService {

        @Autowired
        private ProdutoRepository produtoRepository;

        @Autowired
        private CategoriaRepository categoriaRepository;

        public List<ProdutoDTO> obterTodos() {
                return produtoRepository.findAll().stream().map(
                                (Produto produto) -> {
                                        return ProdutoDTO.builder()
                                                        .id(produto.getId())
                                                        .nome(produto.getNome())
                                                        .preco(produto.getPreco())
                                                        .categoriaId(produto.getCategoriaProdutos() == null
                                                                        ? 0L
                                                                        :  produto.getCategoriaProdutos().getId())
                                                        .build();
                                }).collect(Collectors.toList());
        }

        public ProdutoDTO obterPorId(Long id) {
                Produto produto = produtoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Produto não encontrado."));

                ProdutoDTO produtoDTO = new ProdutoDTO();
                produtoDTO.setId(produto.getId());
                produtoDTO.setNome(produto.getNome());
                produtoDTO.setPreco(produto.getPreco());
                produtoDTO.setCategoriaId(produto.getCategoriaProdutos().getId());

                return produtoDTO;
        }

        public ProdutoDTO inserir(ProdutoDTO produto) {
                Categoria categoria = categoriaRepository.findById(produto.getCategoriaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Categoria não encontrada."));

                Produto produtoBanco = new Produto();
                produtoBanco.setNome(produto.getNome());
                produtoBanco.setPreco(produto.getPreco());
                produtoBanco.setCategoriaProdutos(categoria);
                produtoRepository.save(produtoBanco);

                ProdutoDTO produtoDTO = new ProdutoDTO();
                produtoDTO.setId(produtoBanco.getId());
                produtoDTO.setNome(produtoBanco.getNome());
                produtoDTO.setPreco(produtoBanco.getPreco());
                produtoDTO.setCategoriaId(produtoBanco.getCategoriaProdutos().getId());

                return produtoDTO;
        }

        public void editar(Long id, ProdutoDTO produto) {
                Produto produtoBanco = produtoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Produto não encontrado"));

                Categoria categoria = categoriaRepository.findById(Long.valueOf(produto.getCategoriaId()))
                                .orElseThrow(() -> new RuntimeException(
                                                "Categoria não encontrada."));

                produtoBanco.setNome(produto.getNome());
                produtoBanco.setPreco(produto.getPreco());
                produtoBanco.setCategoriaProdutos(categoria);

                produtoRepository.save(produtoBanco);
        }

        public void excluir(Long id) {
                produtoRepository.deleteById(id);
        }
}
