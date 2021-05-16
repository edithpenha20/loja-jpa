package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.CategoriaId;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
		
//		List<Produto> todos = produtoDao.buscarTodos();
//		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
//		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoPorNome("Xiaomi Redmi");
//		System.out.println("Preço do Produto: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria informatica = new Categoria("INFORMATICA");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal(800), celulares );
		Produto celularA = new Produto("Moto G5", "com conexao 5G", new BigDecimal(950), celulares );
		Produto celularB = new Produto("Samsung S3", "bateria dura 24h", new BigDecimal(1000), celulares );
		Produto notebook = new Produto("MacBook", "M1, muito potente", new BigDecimal(1200), informatica );
		Produto notebookA = new Produto("Dell", "Com core i7", new BigDecimal(1100), informatica );
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(celularA);
		produtoDao.cadastrar(celularB);
		
		categoriaDao.cadastrar(informatica);
		produtoDao.cadastrar(notebook);
		produtoDao.cadastrar(notebookA);
		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		em.close();
	}

}
