package br.com.primum.modelo.proposta_candidato;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="proposta_candidato")
public class Proposta_candidato  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer cd_proposta;

	@Column
	private Integer cd_candidato;

	@Column
	private String ds_porposta;


	public Integer getCd_proposta(){
		return cd_proposta;
	}


	public void setCd_proposta(Integer cd_proposta){
		this.cd_proposta = cd_proposta;
	}


	public Integer getCd_candidato(){
		return cd_candidato;
	}


	public void setCd_candidato(Integer cd_candidato){
		this.cd_candidato = cd_candidato;
	}


	public String getDs_porposta(){
		return ds_porposta;
	}


	public void setDs_porposta(String ds_porposta){
		this.ds_porposta = ds_porposta;
	}

}