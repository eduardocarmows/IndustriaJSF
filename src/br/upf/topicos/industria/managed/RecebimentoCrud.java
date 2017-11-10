package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import org.hibernate.Hibernate;

import br.upf.topicos.industria.entidades.MateriaPrima;
import br.upf.topicos.industria.entidades.PessoaJuridica;
import br.upf.topicos.industria.entidades.Recebimento;
import br.upf.topicos.industria.entidades.RecebimentoItem;
import br.upf.topicos.industria.util.GerirFactory;

@ManagedBean
@SessionScoped
public class RecebimentoCrud {

	private List<Recebimento> lista;
	private Recebimento objecto;

	public void inicializarLista() {
		EntityManager em = GerirFactory.getEntityManager();
		lista = em.createQuery("from Recebimento").getResultList();
		em.close();
	}

	public List<PessoaJuridica> completeFornecedor(String query) {
		EntityManager em = GerirFactory.getEntityManager();
		List<PessoaJuridica> results = em.createQuery("from PessoaJuridica where upper(nome) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by nome").getResultList();
		em.close();
		return results;
	}

	public List<MateriaPrima> completeMateriaPrima(String query) {
		EntityManager em = GerirFactory.getEntityManager();
		List<MateriaPrima> results = em.createQuery("from MateriaPrima where upper(nome) like " + "'"
				+ query.trim().toUpperCase() + "%' " + "order by nome").getResultList();
		em.close();
		return results;
	}

	public String incluir() {
		objecto = new Recebimento();
		return "RecebimentoForm?faces-redirect=true";
	}

	public String gravar() {
		EntityManager em = GerirFactory.getEntityManager();
		em.getTransaction().begin();
		em.merge(objecto);
		em.getTransaction().commit();
		em.close();
		return "RecebimentoList?faces-redirect=true";
	}

	public String cancelar() {
		return "RecebimentoList";
	}

	public String alterar(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		objecto = em.find(Recebimento.class, id);
		Hibernate.initialize(objecto.getItensRecebidos());
		em.close();
		return "RecebimentoForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		objecto = em.find(Recebimento.class, id);
		em.getTransaction().begin();
		em.remove(objecto);
		em.getTransaction().commit();
		em.close();
		return "RecebimentoList?faces-redirect=true";
	}

	public RecebimentoCrud() {
		super();
	}

	public List<Recebimento> getLista() {
		return lista;
	}

	public void setLista(List<Recebimento> lista) {
		this.lista = lista;
	}

	public Recebimento getObjecto() {
		return objecto;
	}

	public void setObjecto(Recebimento objecto) {
		this.objecto = objecto;
	}

	
	
	
	
	// --------------------------------------------------------
	// Para os itens
	// --------------------------------------------------------
	
	
	
	
	private RecebimentoItem item; // item em edição, vinculado ao formulário

	private Integer rowIndex = null; // índice do item selecionado - alterar e
										// excluir

	public void incluirItem() {
		rowIndex = null;
		item = new RecebimentoItem();
	}

	public void alterarItem(Integer rowIndex) {
		this.rowIndex = rowIndex;
		item = objecto.getItensRecebidos().get(rowIndex); // pega item da
															// coleção
	}

	public void excluirItem(Integer rowIndex) {
		objecto.getItensRecebidos().remove(rowIndex.intValue()); // exclui item
		calcularTotais();
	}

	public void gravarItem() {
		if (this.rowIndex == null) {
			item.setRecebimento(objecto);
			objecto.getItensRecebidos().add(item); // adiciona item na coleção
		} else {
			objecto.getItensRecebidos().set(rowIndex, item); // altera na
																// coleção
		}
		calcularTotais();
		rowIndex = null;
		item = null;
	}

	public void cancelarItem() {
		rowIndex = null;
		item = null;
	}

	/**
	 * Método chamado por ajax para o cálculo do total do item ao digitar no
	 * formulário
	 */
	public void calcularTotalItem() {
		if (item.getValorUnitario() == null || item.getQuantidade() == null)
			return;
		item.setTotal(item.getValorUnitario() * item.getQuantidade());
	}

	/**
	 * Método que calcula o total do recebimento após as operações sobre itens
	 */
	public void calcularTotais() {
		Float valorProdutos = 0.0F;
		for (RecebimentoItem it : objecto.getItensRecebidos())
			valorProdutos += it.getTotal();
		objecto.setTotalProdutos(valorProdutos);

		if (objecto.getValorFrete() == null)
			objecto.setValorFrete(0.0F);
		objecto.setTotalCompra(objecto.getTotalProdutos() + 
				               objecto.getValorFrete());
	}

	public RecebimentoItem getItem() {
		return item;
	}

	public void setItem(RecebimentoItem item) {
		this.item = item;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

}
