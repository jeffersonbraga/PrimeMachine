package br.com.primum.modelo.evidencia_fotografica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="evidencia_fotografica")
public class Evidencia_fotografica {
	
	@Id
	@Column
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cd_evidencia_fotografica;
	
	@Column
	private String ds_evidencia_fotografica;
	
	@Column
	private String ds_local_armazenamento;
	
	@Column
	private Integer cd_resposta_execucao_auditoria;

//	@Transient
//	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.resposta_execucao_auditoria.Resposta_execucao_auditoria", campoBuscar="cd_resposta_execucao_auditoria", campoComparar="cd_resposta_execucao_auditoria")
//	private Resposta_execucao_auditoria resposta_execucao_auditoria;
//
//	
//	public Resposta_execucao_auditoria getResposta_execucao_auditoria() {
//		return resposta_execucao_auditoria;
//	}
//
//
//	public void setResposta_execucao_auditoria(
//			Resposta_execucao_auditoria resposta_execucao_auditoria) {
//		this.resposta_execucao_auditoria = resposta_execucao_auditoria;
//	}


	public Integer getCd_evidencia_fotografica() {
		return cd_evidencia_fotografica;
	}


	public void setCd_evidencia_fotografica(Integer cd_evidencia_fotografica) {
		this.cd_evidencia_fotografica = cd_evidencia_fotografica;
	}


	public String getDs_evidencia_fotografica() {
		return ds_evidencia_fotografica;
	}

	
	public void setDs_evidencia_fotografica(String ds_evidencia_fotografica) {
		this.ds_evidencia_fotografica = ds_evidencia_fotografica;
	}


	public String getDs_local_armazenamento() {
		return ds_local_armazenamento;
	}


	public void setDs_local_armazenamento(String ds_local_armazenamento) {
		this.ds_local_armazenamento = ds_local_armazenamento;
	}


	public Integer getCd_resposta_execucao_auditoria() {
		return cd_resposta_execucao_auditoria;
	}


	public void setCd_resposta_execucao_auditoria(
			Integer cd_resposta_execucao_auditoria) {
		this.cd_resposta_execucao_auditoria = cd_resposta_execucao_auditoria;
	}
	
	
	
}
