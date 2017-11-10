package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import br.upf.topicos.industria.entidades.Grupo;
import br.upf.topicos.industria.util.GerirFactory;
import br.upf.topicos.industria.util.TrataException;


@ManagedBean
@SessionScoped 
public class GrupoCrud {

	private List<Grupo> lista;
	private Grupo objeto;

	public String incluir(){
		objeto = new Grupo();
		return "GrupoForm?faces-redirect=true";
	}
	
	public String alterar(Integer id){
		EntityManager em = GerirFactory.getEntityManager();
		objeto = em.find(Grupo.class, id);
		em.close(); 
		return "GrupoForm?faces-redirect=true";
	}
		
	public String excluir(Integer id){
		EntityManager em = GerirFactory.getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.find(Grupo.class, id));
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", 
					new FacesMessage(TrataException.getErrorMessage(e)));
			return "";
		} finally {
			em.close();	
		}
		return "GrupoList?faces-redirect=true";
	}	
	
	public String gravar(){
		Integer idAnt = objeto.getId();
		EntityManager em = GerirFactory.getEntityManager();
		try {
			em.getTransaction().begin();
			objeto = em.merge(objeto);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", 
					new FacesMessage(TrataException.getErrorMessage(e)));
			objeto.setId(idAnt);
			return "";
		}finally {
			em.close();	
		}
		return "GrupoList?faces-redirect=true";
	}
	
	public String cancelar(){
		return "GrupoList?faces-redirect=true";
	}
	
	public void inicializarLista(){
		EntityManager em = GerirFactory.getEntityManager();
		lista = em.createQuery("from Grupo").getResultList();
		em.close();
	}
	
	
	
	public List<Grupo> getLista() {
		return lista;
	}

	public void setLista(List<Grupo> lista) {
		this.lista = lista;
	}



	public Grupo getObjeto() {
		return objeto;
	}



	public void setObjeto(Grupo objeto) {
		this.objeto = objeto;
	}

	
	
	
}
	