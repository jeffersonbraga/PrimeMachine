package br.com.primum.service.treinamento;

import java.util.List;

import br.com.primum.bo.treinamento.TreinamentoBO;
import br.com.primum.modelo.formulario.Formulario;

public class TreinamentoService {
	
	private TreinamentoBO treinamentoBO;
	
	public TreinamentoBO getTreinamentoBO() {
		return treinamentoBO;
	}
	
	public void setTreinamentoBO(TreinamentoBO treinamentoBO) {
		this.treinamentoBO = treinamentoBO;
	}
	
	public Boolean gravaArquivo(List<byte[]> lista, List<String> listaNomes, Integer codigo, String pasta){
		
		this.setTreinamentoBO(new TreinamentoBO());
		return getTreinamentoBO().salvarArquivo(lista, listaNomes, codigo, pasta);
	}
	
	public List<?> buscarListaArquivos(Integer cd_chaveprimaria, String pasta) {

		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().buscarListaArquivos(cd_chaveprimaria, pasta);
	}
	
	public List<byte[]> buscarListaArquivosData(Integer cd_chaveprimaria, String pasta) {

		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().buscarListaArquivosData(cd_chaveprimaria, pasta);
	}
	
	public List<?> ListaTreinamentosPorPessoa(Integer cd_pessoa) throws Exception{
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().getListaTreinamentosPorPessoa(cd_pessoa);
	}
	
	
	public List<Formulario> ListaAtividadesPorFuncao(Integer cd_profissao) throws Exception{
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().getListaAtividades(cd_profissao);
	}
	
	public Boolean excluiArquivo(String nomeArquivo, Integer cd_chaveprimaria, String pasta){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().excluirArquivo(nomeArquivo, cd_chaveprimaria, pasta);
	}
	
	public Boolean excluiPasta(Integer cd_chaveprimaria, String pasta){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().excluirPasta(cd_chaveprimaria, pasta);
	}
	
	
	
	public Boolean gravaCopiaControlada(byte[] dados, String nomeArquivo, String pasta){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().gravaCopiaControlada(dados, nomeArquivo, pasta);
	}
	public Boolean gravaCopiaNaoControlada(byte[] dados, String nomeArquivo, String pasta){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().gravaCopiaNaoControlada(dados, nomeArquivo, pasta);
	}
	public Boolean excluiCopiaControlada(String nomeArquivo){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().excluiCopiaControlada(nomeArquivo);
	}
	public Boolean excluiCopiaNaoControlada(String nomeArquivo){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().excluiCopiaNaoControlada(nomeArquivo);
	}
	
	public byte[] returnByteArray(String fileName){
		this.setTreinamentoBO(new TreinamentoBO());
		try {
			return this.getTreinamentoBO().returnByteArray(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Boolean gravaArquivo(byte[] dados, String nomeArquivo, String pasta){
		this.setTreinamentoBO(new TreinamentoBO());
		return this.getTreinamentoBO().gravaArquivo(dados, nomeArquivo, pasta);
	}
	
}
