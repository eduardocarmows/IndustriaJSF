package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.topicos.industria.entidades.Cidade;
import br.upf.topicos.industria.entidades.PessoaJuridica;
import br.upf.topicos.industria.util.GerirFactory;
import br.upf.topicos.industria.util.TrataException;

@ManagedBean
@SessionScoped
public class PessoaJuridicaCrud {

	private List<PessoaJuridica> lista;
	private PessoaJuridica objecto;
	private String[] listaUF = { "RS", "SC", "PR", "SP" };

	public void inicializarLista() {
		EntityManager em = GerirFactory.getEntityManager();
		lista = em.createQuery("from PessoaJuridica").getResultList();
		em.close();
	}
	
	public List<Cidade> completeCidade(String query) {
		EntityManager em = GerirFactory.getEntityManager();
		 List<Cidade> results = em.createQuery(
		 "from Cidade where upper(nome) like "+
		"'"+query.trim().toUpperCase()+"%' "+
		 "order by nome").getResultList();
		 em.close();
		 return results;
		}


	public String incluir() {
		objecto = new PessoaJuridica();
		return "PessoaJuridicaForm?faces-redirect=true";
	}

	public String gravar() {
		EntityManager em = GerirFactory.getEntityManager();
		em.getTransaction().begin();
		em.merge(objecto);
		em.getTransaction().commit();
		em.close();
		return "PessoaJuridicaList?faces-redirect=true";
	}

	public String cancelar() {
		return "PessoaJuridicaList";
	}

	public String alterar(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		objecto = em.find(PessoaJuridica.class, id);
		em.close();
		return "PessoaJuridicaForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		try {
			 em.getTransaction().begin();
			 em.remove(em.find(PessoaJuridica.class, id));
			 em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(TrataException.getErrorMessage(e)));
			return "";
		}finally{
			em.close();
		}
		 return "PessoaJuridicaList?faces-redirect=true";
		}
	
	
	public PessoaJuridicaCrud() {
		super();
	}

	public List<PessoaJuridica> getLista() {
		return lista;
	}

	public void setLista(List<PessoaJuridica> lista) {
		this.lista = lista;
	}

	public PessoaJuridica getObjecto() {
		return objecto;
	}

	public void setObjecto(PessoaJuridica objecto) {
		this.objecto = objecto;
	}

	public String[] getListaUF() {
		return listaUF;
	}

	public void setListaUF(String[] listaUF) {
		this.listaUF = listaUF;
	}

}
