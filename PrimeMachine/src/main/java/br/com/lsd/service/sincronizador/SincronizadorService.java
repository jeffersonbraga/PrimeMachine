package br.com.lsd.service.sincronizador;

import java.io.File;
import java.util.List;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.lsd.bo.sincronizar.SincronizadorBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.usuario.Usuario;

public class SincronizadorService {
	
	private SincronizadorBO sinc;

	public SincronizadorBO getSinc() {
		return sinc;
	}

	public void setSinc(SincronizadorBO sinc) {
		this.sinc = sinc;
	}
	
	public boolean sincronizar(List<Execucao_formulario> listaSincronizar) {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().sincronizar(listaSincronizar);
	}

	public List<Object> sincronizarEscalonamento(Usuario usuario) throws Exception {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().sincronizarEscalonamento(usuario);
	}
	
	public List<Object> sincronizarDispositivo() throws Exception {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().sincronizarDispositivo();
	}
	
	public List<Object> sincronizarDicionario() throws Exception {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().sincronizarDicionario();
	}

	@SuppressWarnings("unchecked")
	public List<Object> gerarExecucao_formulario(Integer mes) throws Exception {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().gerarExecucao_formulario(mes);		 
	}
	
	public void copiarFormulario(Integer cd_formulario) {
		this.setSinc(new SincronizadorBO());
		this.getSinc().copiarFormulario(cd_formulario);		 
	}

	public byte[] criarBaseSQLite() throws Exception {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().criarEstrutura(this.getSinc().criarBaseSQLite());
	}

	public boolean checarConexao() throws Exception {
		return true;
	}
	
	public void criarArquivoSQLite() {
		this.setSinc(new SincronizadorBO());
		this.getSinc().criarArquivoSQLite();
	}
	
	public List<List<?>> getListasDicionario(String caminho_banco) {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().getListasDicionario(caminho_banco);
	}

	public boolean salvarBaseAtual(List<Modulo> listaModulo, List<Tabela> listaTabela, List<Programa> listaPrograma) {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().salvarBaseAtual(listaModulo, listaTabela, listaPrograma);
	}

	public boolean salvarBaseExterna(List<Modulo> listaModulo, List<Tabela> listaTabela, List<Programa> listaPrograma) {
		this.setSinc(new SincronizadorBO());
		return this.getSinc().salvarBaseExterna(listaModulo, listaTabela, listaPrograma);
	}
}