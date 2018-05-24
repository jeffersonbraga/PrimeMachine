package br.com.primum.service.escalonamento_formulario;

import java.util.Date;
import java.util.List;

import br.com.primum.bo.escalonamento_formulario.Escalonamento_formularioBO;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;

public class Escalonamento_formularioService {

	private Escalonamento_formularioBO escalonamentoBO;

	public Escalonamento_formularioBO getEscalonamentoBO() {
		return escalonamentoBO;
	}

	public void setEscalonamentoBO(Escalonamento_formularioBO escalonamentoBO) {
		this.escalonamentoBO = escalonamentoBO;
	}

	public List<Escalonamento_usuario> buscarCronogramaAtividades(Integer cd_usuario, Integer tp_formulario, Integer cd_unidade) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().buscarCronogramaAtividades(cd_usuario, tp_formulario, cd_unidade);
	}

	public List<?> montarPainelProximoDia(Integer qtdeDias, Integer cd_unidade) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().montarPainelProximoDia(qtdeDias, cd_unidade);
	}

	public boolean validarEscalonamentoUsuario(Escalonamento_formulario escalonamento_formulario, Escalonamento_usuario escalonamento_usuario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().validarEscalonamentoUsuario(escalonamento_formulario, escalonamento_usuario);
	}

	public List<?> montarPainelAcompanhamento(Integer cd_unidade) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().montarPainelAcompanhamento( cd_unidade );
	}

	public List<Escalonamento_usuario> listarFormularioEscalonadosPorSituacao(Integer cd_usuario, Integer cd_unidade) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().listarFormularioEscalonadosPorSituacao(cd_usuario, cd_unidade);
	}

	public List<List<?>> getExecucoesPorSituacao(Date data, Integer cd_unidade) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().getExecucoesPorSituacao( data, cd_unidade );
	}

	public List<Escalonamento_formulario> getEscalonamentoFormularioPorUsuario(Integer cd_usuario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().getEscalonamentoFormularioPorUsuario( cd_usuario );
	}

	public List<Escalonamento_usuario> getEscalonamentoUsuarioPorUsuario(Integer cd_usuario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().getEscalonamentoUsuarioPorUsuario( cd_usuario );
	}

	public Boolean getEscalonamentoFormulario( List<Integer> lista, Date dt_inicio, Date dt_termino, Integer cd_usuario_de, Integer cd_usuario_para ) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().getEscalonamentoFormulario( lista, dt_inicio, dt_termino, cd_usuario_de, cd_usuario_para );
	}

	public Boolean setEscalonamentoFormularioInativo( List<Escalonamento_usuario> listaEscUsu ) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().setEscalonamentoFormularioInativo( listaEscUsu );
	}
	
	public List<Formulario> buscarAtividadesCheckList(Integer cd_usuario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().buscarAtividadesCheckList( cd_usuario );
	}
	
	public List<Execucao_formulario> buscarAtividadesIniciadas(Integer cd_usuario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().buscarAtividadesIniciadas( cd_usuario );
	}
	
	public List<Formulario> buscarTodosChecklist() {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().buscarTodosChecklist();
	}	
	
	public List<Object> buscarCronogramaAtividadesMobile(Integer cd_usuario, Integer tp_formulario) {
		this.setEscalonamentoBO(new Escalonamento_formularioBO());
		return this.getEscalonamentoBO().buscarCronogramaAtividadesMobile(cd_usuario, tp_formulario);
	}
}