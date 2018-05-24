package br.com.lsd.service.verificacao_documental;

import java.util.Date;
import java.util.List;

import br.com.lsd.bo.verificacao_documental.Verificacao_documentalBO;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;

public class Verificacao_documentalService {

	private Verificacao_documentalBO verif;

	public Verificacao_documentalBO getVerif() {
		return verif;
	}

	public void setVerif(Verificacao_documentalBO verif) {
		this.verif = verif;
	}

	public List<Programa_qualidade> buscarProgramasQualidade() {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarProgramasQualidade();
	}

	public List<Execucao_formulario> buscarFormularios(Formulario formulario, Date dataInicial, Date dataFinal) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarFormularios(formulario, dataInicial, dataFinal);
	}
	
	public List<Execucao_formulario> buscarTodosFormularios(Formulario formulario, Date dataInicial, Date dataFinal) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarTodosFormularios(formulario, dataInicial, dataFinal);
	}
	
	public List<Execucao_formulario> buscarTodosFormulariosChecklist(Formulario formulario, Date dataInicial, Date dataFinal, Integer cd_usuario) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarTodosFormulariosChecklist(formulario, dataInicial, dataFinal, cd_usuario);
	}
	
	
	public boolean salvarVerificacao(Execucao_formulario execuca_verificadora, Execucao_formulario execuca_verificada) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().salvarVerificacao(execuca_verificadora, execuca_verificada);
	}
	
	public boolean salvarVerificacaoMobile(Execucao_formulario execuca_verificadora) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().salvarVerificacaoMobile(execuca_verificadora);
	}
	
	public Execucao_formulario buscarVerificacaoExecutada(Integer tipo, Execucao_formulario execucao_verificada) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarVerificacaoExecutada(tipo, execucao_verificada);
	}
	
	public Formulario buscarFormularioMonitoramento( Integer cd_formulario ) {
		this.setVerif(new Verificacao_documentalBO());
		return this.getVerif().buscarFormularioMonitoramento( cd_formulario );
	}
}
