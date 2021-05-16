package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PerformanceConsulta {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		
		PedidoDao pedidoDao = new PedidoDao(em);
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);
		
		em.close();
		System.out.println(pedido.getCliente().getNome());
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria informatica = new Categoria("INFORMATICA");
		Categoria games = new Categoria("JOGOS");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal(800), celulares );
		Produto notebook = new Produto("MacBook", "M1, muito potente", new BigDecimal(1200), informatica );
		Produto videoGame = new Produto("PS5", "ultimo lancamento", new BigDecimal(15000), games );
		
		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, videoGame));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, pedido2, notebook));
		
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(informatica);
		categoriaDao.cadastrar(games);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(notebook);
		produtoDao.cadastrar(videoGame);
		
		clienteDao.cadastrar(cliente);
		
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		em.close();
		
//		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
//		System.out.println("VALOR TOTAL: " + totalVendido);
//		
//		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
//		relatorio.forEach(System.out::println);
	}

}
