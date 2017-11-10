package br.upf.topicos.industria.managed;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import br.upf.topicos.industria.entidades.Grupo;


@ManagedBean
@SessionScoped 
public class GrupoList {

	private List<Grupo> lista;

	public void inicializarLista(){
		EntityManager em = Persistence.createEntityManagerFactory(
				   "IndustriaJPA").createEntityManager();
		lista = em.createQuery("from Grupo").getResultList();
		em.close();
	}
	
	
	
	public List<Grupo> getLista() {
		return lista;
	}

	public void setLista(List<Grupo> lista) {
		this.lista = lista;
	}


}
	