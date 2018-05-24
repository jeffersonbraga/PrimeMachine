package br.com.primum.modelo.evento_campanha;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="evento_campanha")
public class Evento_campanha  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer cd_evento_campanha;

	@Column
	private String nm_evento_campanha;

	@Column
	private Date dt_evento_campanha;

	@Column
	private String ds_local_evento_campanha;

	@Column
	private String ds_evento_campanha;

	@Column
	private String ds_participantes;

	@Column
	private String ds_olho_texto;


	public Integer getCd_evento_campanha(){
		return cd_evento_campanha;
	}


	public void setCd_evento_campanha(Integer cd_evento_campanha){
		this.cd_evento_campanha = cd_evento_campanha;
	}


	public String getNm_evento_campanha(){
		return nm_evento_campanha;
	}


	public void setNm_evento_campanha(String nm_evento_campanha){
		this.nm_evento_campanha = nm_evento_campanha;
	}


	public Date getDt_evento_campanha(){
		return dt_evento_campanha;
	}


	public void setDt_evento_campanha(Date dt_evento_campanha){
		this.dt_evento_campanha = dt_evento_campanha;
	}


	public String getDs_local_evento_campanha(){
		return ds_local_evento_campanha;
	}


	public void setDs_local_evento_campanha(String ds_local_evento_campanha){
		this.ds_local_evento_campanha = ds_local_evento_campanha;
	}


	public String getDs_evento_campanha(){
		return ds_evento_campanha;
	}


	public void setDs_evento_campanha(String ds_evento_campanha){
		this.ds_evento_campanha = ds_evento_campanha;
	}


	public String getDs_participantes(){
		return ds_participantes;
	}


	public void setDs_participantes(String ds_participantes){
		this.ds_participantes = ds_participantes;
	}


	public String getDs_olho_texto(){
		return ds_olho_texto;
	}


	public void setDs_olho_texto(String ds_olho_texto){
		this.ds_olho_texto = ds_olho_texto;
	}

}