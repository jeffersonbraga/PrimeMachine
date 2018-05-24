package br.com.lsd.service.manutencao;

import java.util.List;

import br.com.primum.modelo.atv_exec_manutencao.Atv_exec_manutencao;
import br.com.primum.modelo.cronograma_manutencao.Cronograma_manutencao;
import br.com.primum.modelo.executar_manutencao.Executar_manutencao;

public class PainelManutencoes {
	
	private PainelManutencoesBO painelBO;
	
	public PainelManutencoesBO getPainelBO() {
		return painelBO;
	}

	public void setPainelBO(PainelManutencoesBO painelBO) {
		this.painelBO = painelBO;
	}
	
	public List<Cronograma_manutencao> buscarManutencoes() {
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarTodasManutencoes();
	}
	
	public List<Executar_manutencao> buscarExecucoes(Integer cd_cronograma_manutencao) {
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarTodasExecucoes(cd_cronograma_manutencao);
	}
	
	public List<Executar_manutencao> buscarExecucoes() {
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarTodasExecucoes();
	}
	
	
	
	public List<Atv_exec_manutencao> buscarlistaAtividadesExecucao(Integer cd_executar_manutencao) {
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarTodasAtividades(cd_executar_manutencao);
	}
	
	public List<Executar_manutencao> buscarManutencoesPorEquipamento(){
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarAllManutencoes();
	}
	
	public List<Executar_manutencao> buscarExecucoesPorEquipamento(Integer cd_equipamento, Integer ano){
		this.setPainelBO(new PainelManutencoesBO());
		return this.getPainelBO().buscarExecucoesPorEquipamento(cd_equipamento, ano);
	}
	
//	public List<Cronograma_manutencao> buscarCronogramas(){
//		this.setPainelBO(new PainelManutencoesBO());
//		return this.getPainelBO().buscarCronogramas();
//	}
	
//	public Boolean mudaNr_Executada_Atividade(List<?> listaAtividades){
//		this.setPainelBO(new PainelManutencoesBO());
//		return this.getPainelBO().mudaNr_Executada_Atividade(listaAtividades);
//	}

}
