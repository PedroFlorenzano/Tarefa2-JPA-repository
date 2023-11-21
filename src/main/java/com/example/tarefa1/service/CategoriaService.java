package com.example.tarefa1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tarefa1.dto.CategoriaDTO;
import com.example.tarefa1.model.Categoria;
import com.example.tarefa1.model.Produto;
import com.example.tarefa1.repository.CategoriaRepository;
import com.example.tarefa1.repository.ProdutoRepository;

@Service
public class CategoriaService {

        @Autowired
        private CategoriaRepository categoriaRepository;

        @Autowired
        private ProdutoRepository produtoRepository;

        public List<CategoriaDTO> obterTodos() {
                return categoriaRepository.findAll().stream().map(
                                (Categoria categoria) -> {
                                        List<Long> listaId = categoria.getProdutos().stream()
                                                        .map(produto -> produto.getId()).collect(Collectors.toList());

                                        return CategoriaDTO.builder()
                                                        .id(categoria.getId())
                                                        .nome(categoria.getNome())
                                                        .listProdutoId(listaId)
                                                        .build();
                                }).collect(Collectors.toList());
        }

        public CategoriaDTO obterPorId(Long id) {
                Categoria categoria = categoriaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Categoria não encontrada."));

                List<Long> listaId = categoria.getProdutos().stream().map(produto -> produto.getId())
                                .collect(Collectors.toList());

                CategoriaDTO categoriaDTO = new CategoriaDTO();
                categoriaDTO.setId(categoria.getId());
                categoriaDTO.setNome(categoria.getNome());
                categoriaDTO.setListProdutoId(listaId);

                return categoriaDTO;
        }

        public Categoria inserir(CategoriaDTO categoria) {

                Categoria categoriaBanco = new Categoria();
                categoriaBanco.setNome(categoria.getNome());

                return categoriaRepository.save(categoriaBanco);
        }

        public void editar(Long id, CategoriaDTO categoria) {
                Categoria categoriaBanco = categoriaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Categoria não encontrada."));

                List<Produto> listaProduto = produtoRepository.findAllById(categoria.getListProdutoId());

                if (listaProduto.size() != categoria.getListProdutoId().size()) {
                        throw new RuntimeException("Produto não encontrado.");
                }

                for(int i = 0; i < listaProduto.size(); i ++){
                        Produto produto = listaProduto.get(i);
                        produto.setCategoriaProdutos(null);
                        produtoRepository.save(produto);
                }

                categoriaBanco.setId(id);
                categoriaBanco.setNome(categoria.getNome());
                categoriaBanco.setProdutos(listaProduto);

                categoriaRepository.save(categoriaBanco);
        }

        public void excluir(Long id) {
                Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                                () -> new RuntimeException("Categoria não encontrada."));

                List<Produto> listaProduto = categoria.getProdutos();

                if (!listaProduto.isEmpty()) {
                        for(int i = 0; i < listaProduto.size(); i++){
                                Produto produto = listaProduto.get(i);
                                produto.setCategoriaProdutos(null);
                                produtoRepository.save(produto);
                        }
                }
                categoriaRepository.deleteById(id);
        }
}
