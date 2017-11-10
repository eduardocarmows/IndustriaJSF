package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.hibernate.validator.constraints.NotEmpty;

import br.upf.topicos.industria.entidades.Cidade;
import br.upf.topicos.industria.util.GerirFactory;
import br.upf.topicos.industria.util.TrataException;

@ManagedBean
@SessionScoped
public class CidadeCrud {

	private List<Cidade> lista;
	private Cidade objecto;
	private String[] listaUF = { "AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO" };

	public void inicializarLista() {
		EntityManager em = GerirFactory.getEntityManager();
		lista = em.createQuery("from Cidade").getResultList();
		em.close();
	}

	public String incluir() {
		objecto = new Cidade();
		return "CidadeForm?faces-redirect=true";
	}

	public String gravar() {
		EntityManager em = GerirFactory.getEntityManager();
		em.getTransaction().begin();
		em.merge(objecto);
		em.getTransaction().commit();
		em.close();
		return "CidadeList?faces-redirect=true";
	}

	public String cancelar() {
		return "CidadeList";
	}

	public String alterar(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		objecto = em.find(Cidade.class, id);
		em.close();
		return "CidadeForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		try {
			 em.getTransaction().begin();
			 em.remove(em.find(Cidade.class, id));
			 em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Cidade referenciada em outros lugares!"));
			return "";
		}finally{
			em.close();
		}
		 return "CidadeList?faces-redirect=true";
		}
	
	public CidadeCrud() {
		super();
	}

	public List<Cidade> getLista() {
		return lista;
	}

	public void setLista(List<Cidade> lista) {
		this.lista = lista;
	}

	public Cidade getObjecto() {
		return objecto;
	}

	public void setObjecto(Cidade objecto) {
		this.objecto = objecto;
	}

	public String[] getListaUF() {
		return listaUF;
	}

	public void setListaUF(String[] listaUF) {
		this.listaUF = listaUF;
	}

}
