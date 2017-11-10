package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.topicos.industria.entidades.MateriaPrima;
import br.upf.topicos.industria.util.GerirFactory;

@ManagedBean
@SessionScoped
public class MateriaPrimaCrud {

	private List<MateriaPrima> lista;
	private MateriaPrima objeto;

	public MateriaPrimaCrud() {
		super();
	}
	
	public void inicializarLista() {
		EntityManager em = GerirFactory.getEntityManager();
		lista = em.createQuery("from MateriaPrima").getResultList();
		em.close();
	}

	public String incluir() {
		objeto = new MateriaPrima();
		return "MateriaPrimaForm?faces-redirect=true";
	}

	public String gravar() {
		EntityManager em = GerirFactory.getEntityManager();
		em.getTransaction().begin();
		em.merge(objeto);
		em.getTransaction().commit();
		em.close();
		return "MateriaPrimaList?faces-redirect=true";
	}

	public String alterar(Integer id) {
		EntityManager em = GerirFactory.getEntityManager();
		objeto = em.find(MateriaPrima.class, id);
		em.close();
		return "MateriaPrimaForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		try {
			EntityManager em = br.upf.topicos.industria.util.GerirFactory.getEntityManager();
			objeto = em.find(MateriaPrima.class, id);
			em.getTransaction().begin();
			em.remove(objeto);
			em.getTransaction().commit();
			em.close();
			return "MateriaPrimaList?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Objeto não pode ser excluído!", "");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
		}
	}

	public String cancelar() {
		return "MateriaPrimaList";
	}
	
	public List<MateriaPrima> getLista() {
		return lista;
	}

	public void setLista(List<MateriaPrima> lista) {
		this.lista = lista;
	}

	public MateriaPrima getObjeto() {
		return objeto;
	}

	public void setObjeto(MateriaPrima objeto) {
		this.objeto = objeto;
	}

}
