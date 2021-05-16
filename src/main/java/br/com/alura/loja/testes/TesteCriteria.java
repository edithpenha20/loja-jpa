package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class TesteCriteria {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		produtoDao.buscarPorParametrosComCriteria("PS5", null, LocalDate.now());
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria informatica = new Categoria("INFORMATICA");
		Categoria games = new Categoria("JOGOS");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal(800), celulares );
		Produto notebook = new Produto("MacBook", "M1, muito potente", new BigDecimal(1200), informatica );
		Produto videoGame = new Produto("PS5", "ultimo lancamento", new BigDecimal(15000), games );
		
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(informatica);
		categoriaDao.cadastrar(games);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(notebook);
		produtoDao.cadastrar(videoGame);
		
		em.getTransaction().commit();
		em.close();
	}

}
