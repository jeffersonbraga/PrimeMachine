package br.com.lsd.service.formulario;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.primum.bo.escalonamento_formulario.Escalonamento_formularioBO;
import br.com.lsd.bo.formulario.FormularioBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.usuario.Usuario;

@RestController
@RequestMapping("app")
public class FormularioService {

	private FormularioBO form;

	public FormularioBO getForm() {
		return form;
	}

	public void setForm(FormularioBO form) {
		this.form = form;
	}
	
	
//	public Boolean buscaRevisaoeSetaStNova_Revisao(Integer cd_revisao) {
//		this.setForm(new FormularioBO());
//		return this.getForm().buscaRevisaoeSetaStNova_Revisao(cd_revisao);
//	}
	
	public Boolean verificaRevisaoAprovada(Integer cd_revisao) {
		this.setForm(new FormularioBO());
		return this.getForm().verificaRevisaoAprovada(cd_revisao);
	}
	public Boolean buscaRevisaoeArquiva(Integer cd_revisao) {
		this.setForm(new FormularioBO());
		return this.getForm().buscaRevisaoeArquiva(cd_revisao);
	}
	
	public List<?> buscarDadosFiltroSubFormulario(Integer cd_tabela) {
		if (cd_tabela != null) {
			this.setForm(new FormularioBO());
			return this.getForm().buscarDadosFiltroSubFormulario(cd_tabela);
		}  else {
			return null;
		}
	}

	public List<?> buscarObjetosPaleta() {
		this.setForm( new FormularioBO());
		return this.getForm().buscarObjetosPaleta();
	}

	public List<?> buscarFormulariosExecutados() {
		this.setForm( new FormularioBO());
		return this.getForm().buscarFormulariosExecutados();
	}
	
	public List<?> pesquisar(Integer cd_programa){
		this.setForm( new FormularioBO());
		return this.getForm().pesquisar(cd_programa);
	}
	
	public List<Formulario> buscarFormulariosPorRevisao(Integer cd_revisao_programa_qualidade){
		this.setForm( new FormularioBO());
		return this.getForm().buscarFormulariosPorRevisao(cd_revisao_programa_qualidade);
	}
	
	public List<List<?>> buscarProgramasQualidade_Formularios(){
		this.setForm( new FormularioBO());
		return this.getForm().buscarProgramasQualidade_Formularios();
	}
	
	public List<?> renovarFormulariosEFilhos(List<Formulario> listaFormularios, Integer novoCd_revisao){
		this.setForm(new FormularioBO());
		return this.getForm().renovarFormulariosEFilhos(listaFormularios, novoCd_revisao);
	}
	
	
	public Formulario salvarFormulario(Formulario f){
		this.setForm(new FormularioBO());
		return this.getForm().salvarFormulario( f );
	}

	public List<Formulario> buscaListaFormulariosPorProgramaQualidade(Integer cd_programa_qualidade){
		this.setForm(new FormularioBO());
		return this.getForm().buscaListaFormulariosPorProgramaQualidade( cd_programa_qualidade );
	}

	public List<?> buscarDadosFormulariosExecutados(Integer cd_formulario) {
		this.setForm( new FormularioBO());
		return this.getForm().buscarDadosFormulariosExecutados(cd_formulario);
	}

	public List<Object> buscarFormulariosMenuPrograma(Integer cd_usuario) {
		this.setForm( new FormularioBO());
		return this.getForm().buscarFormulariosMenuPrograma(cd_usuario);
	}		

	public List<Object> buscarCronogramaAtividadesMobile(Usuario u) {
		Escalonamento_formularioBO esc = new Escalonamento_formularioBO();
		return esc.buscarCronogramaAtividadesMobile(u.getCd_usuario(), 0);
	}		

	public List<Object> listaExecucoesFormulario(Formulario f) {
		
		GenericDAO dao = new GenericDAO();
		List<Object> lista = dao.getSession().createCriteria(Execucao_formulario.class).add(Restrictions.eq("cd_formulario", f.getCd_formulario())).list();
		for (Object o : lista) {
			if (o instanceof Execucao_formulario) {
				PesquisaPadraoBO p = new PesquisaPadraoBO();
				p.buscarObjetoCompleto(o);
			}
		}

		return lista;
	}		

    @CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/formularios")
	public List<Object> getAllFormularios() {

		GenericDAO dao = new GenericDAO();
		List<Object> lista = dao.getSession().createCriteria(Formulario.class).list();

		return lista;
	}
}