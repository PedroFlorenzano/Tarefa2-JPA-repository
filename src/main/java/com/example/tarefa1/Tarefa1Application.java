package com.example.tarefa1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.tarefa1.model.Categoria;
import com.example.tarefa1.model.Produto;
import com.example.tarefa1.repository.CategoriaRepository;
import com.example.tarefa1.repository.ProdutoRepository;

@SpringBootApplication
public class Tarefa1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tarefa1Application.class, args);
	}

	@Bean
	public CommandLineRunner initRunner(@Autowired ProdutoRepository produtoRepository, @Autowired CategoriaRepository categoriaRepository){
		return args -> {

			// Instanciando os Produtos
			Produto produtoTeclado = new Produto( "Teclado Gamer", 20.0);
			Produto produtoCelular = new Produto( "Celular", 60.0);
			Produto produtoNotebook = new Produto( "Notebook", 100.0);
			Produto produtoMesa = new Produto( "Mesa", 40.0);
			Produto produtoTecladoEscritorio = new Produto("Teclado Escritório", 10.0);
			
			// Salvando produtos dentro do banco de dados
			produtoRepository.save(produtoTeclado);
			produtoRepository.save(produtoCelular);
			produtoRepository.save(produtoNotebook);
			produtoRepository.save(produtoMesa);
			produtoRepository.save(produtoTecladoEscritorio);
			
			// Instanciando categorias
			Categoria categoriaJogo = new Categoria( "Categoria de Jogos");
			Categoria categoriaEletronico = new Categoria( "Categoria Eletrônicos");
			Categoria categoriaUtilitarios = new Categoria( "Categoria de Utilitários");
			Categoria categoriaCozinha = new Categoria( "Cozinha");

			
			// Salvando categorias dentro do banco de dados
			categoriaRepository.save(categoriaJogo);
			categoriaRepository.save(categoriaEletronico);
			categoriaRepository.save(categoriaUtilitarios);
			categoriaRepository.save(categoriaCozinha);

			// Vinculando Produtos dentro de Categorias
			Produto produtoTecladoEncontrado = produtoRepository.findById(1L).get();
			produtoTecladoEncontrado.setCategoriaProdutos(categoriaEletronico);
			produtoRepository.save(produtoTecladoEncontrado);

			Produto produtoCelularEncontrado = produtoRepository.findById(2L).get();
			produtoCelularEncontrado.setCategoriaProdutos(categoriaEletronico);
			produtoRepository.save(produtoCelularEncontrado);
			
			Produto produtoNotebookEncontrado = produtoRepository.findById(3L).get();
			produtoNotebookEncontrado.setCategoriaProdutos(categoriaEletronico);
			produtoRepository.save(produtoNotebookEncontrado);

			System.out.println("Deve retornar todos os produtos com um valor maior do que parâmetro");
			List<Produto> produtosMetodo1 = produtoRepository.findByPrecoGreaterThan(50.0);
			for (Produto produto : produtosMetodo1) {
				System.out.println("Nome do produto: " + produto.getNome() + " - " + "Preço do Produto: " + produto.getPreco());
			}

			System.out.println("Deve retornar todos os produtos com um valor menor ou igual do que parâmetro");
			List<Produto> produtosMetodo2 = produtoRepository.findByPrecoLessThanEqual(40.0);
			for (Produto produto : produtosMetodo2) {
				System.out.println("Nome do produto: " + produto.getNome() + " - " + "Preço do Produto: " + produto.getPreco());
			}

			System.out.println("Deve retornar todos os produtos que comecem com o nome do parâmetro");
			List<Produto> produtosMetodo3 = produtoRepository.findByNomeStartingWith("Teclado");
			for (Produto produto : produtosMetodo3) {
				System.out.println("Nome do produto: " + produto.getNome());
			}

			System.out.println("Deve retornar todas as categorias que comecem com o nome do parâmetro");
			List<Categoria> categoriasMetodo1 = categoriaRepository.findByNomeStartingWith("Categoria");
			for (Categoria categoria : categoriasMetodo1) {
				System.out.println("Nome da categoria: " + categoria.getNome());
			}

			System.out.println("Deve retornar uma categoria pelo parâmetro e todos os produtos relacionados a essa categoria");
			Categoria categoriaMetodo2 = categoriaRepository.findCategoryById(2L);
			
			for (Produto produto : categoriaMetodo2.getProdutos()) {
				System.out.println("Produtos da categoria encontrado: " + produto.getNome() + " - " + "Preço do Produto: " + produto.getPreco());
			}
		};
	}
}
