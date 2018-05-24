package br.com.primum.modelo.atividades_manutencao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.atividades.Atividades;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atividades_manutencao")
public class Atividades_manutencao {
	
	@Id
	@Column
	private Integer cd_atividades_manutencao;
	
	@Column
	private Integer cd_manutencao_preventiva;
	
	@Column
	private Integer cd_atividades;
	
	@Column
	private String ds_como;
	
	@Column
	private Integer nr_ordem;

	@Column
	private Integer nr_tempo_estimado;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.atividades.Atividades", campoBuscar="cd_atividades", campoComparar="cd_atividades")
	@Transient
	private Atividades atividade;
	
	
	@Transient
	private Boolean marcada;
	
	

	public Integer getNr_tempo_estimado() {
		return nr_tempo_estimado;
	}

	public void setNr_tempo_estimado(Integer nr_tempo_estimado) {
		this.nr_tempo_estimado = nr_tempo_estimado;
	}

	public Boolean getMarcada() {
		return marcada;
	}

	public void setMarcada(Boolean marcada) {
		this.marcada = marcada;
	}


	public Atividades getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividades atividade) {
		this.atividade = atividade;
	}

	public Integer getCd_atividades_manutencao() {
		return cd_atividades_manutencao;
	}

	public void setCd_atividades_manutencao(Integer cd_atividades_manutencao) {
		this.cd_atividades_manutencao = cd_atividades_manutencao;
	}

	public Integer getCd_manutencao_preventiva() {
		return cd_manutencao_preventiva;
	}

	public void setCd_manutencao_preventiva(Integer cd_manutencao_preventiva) {
		this.cd_manutencao_preventiva = cd_manutencao_preventiva;
	}

	public Integer getCd_atividades() {
		return cd_atividades;
	}

	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}

	public String getDs_como() {
		return ds_como;
	}

	public void setDs_como(String ds_como) {
		this.ds_como = ds_como;
	}

	public Integer getNr_ordem() {
		return nr_ordem;
	}

	public void setNr_ordem(Integer nr_ordem) {
		this.nr_ordem = nr_ordem;
	}
	
}
