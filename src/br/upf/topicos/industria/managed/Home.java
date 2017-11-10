package br.upf.topicos.industria.managed;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import br.upf.topicos.industria.util.GerirFactory;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@RequestScoped
public class Home {

	public void abrirPopup(String url, int largura, int altura, boolean modal) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", modal);
		options.put("width", largura);
		options.put("height", altura);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		options.put("resizable", false);
		options.put("minimizable", true);
		options.put("maximizable", true);
		RequestContext.getCurrentInstance().openDialog(url, options, null);
	}

	public void relCidades() {
		try {
			HashMap parameters = new HashMap();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
			Connection con = GerirFactory.getEntityManagerJDBCConnection();
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(scontext.getRealPath("/WEB-INF/relatorios/cidade/cidade.jasper"), parameters, con);
			byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			res.setContentType("application/pdf");
			res.setHeader("Content-disposition", "inline;filename=arquivo.pdf"); // diretamente
																					// na
																					// página
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");// baixar ou salvar
			res.getOutputStream().write(b);
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public void relCidadesPorUf() {
		try {
			HashMap parameters = new HashMap();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
			Connection con = GerirFactory.getEntityManagerJDBCConnection();
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(scontext.getRealPath("/WEB-INF/relatorios/cidadePorUf/cidadePorUf.jasper"), parameters, con);
			byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			res.setContentType("application/pdf");
			res.setHeader("Content-disposition", "inline;filename=arquivo.pdf"); // diretamente
																					// na
																					// página
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");// baixar ou salvar
			res.getOutputStream().write(b);
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void relGrupos() {
		try {
			HashMap parameters = new HashMap();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
			Connection con = GerirFactory.getEntityManagerJDBCConnection();
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(scontext.getRealPath("/WEB-INF/relatorios/grupo/grupo.jasper"), parameters, con);
			byte[] b = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			res.setContentType("application/pdf");
			res.setHeader("Content-disposition", "inline;filename=arquivo.pdf"); // diretamente
																					// na
																					// página
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");// baixar ou salvar
			res.getOutputStream().write(b);
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
}
