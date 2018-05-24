package br.com.primum.modelo.viasoft.BMVDASHFILTROS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BMVDASHFILTROS")
public class Bmvdashfiltros  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String DASHBOARD;

	@Column
	private Integer ID;

	@Column
	private String DESCRICAO;

	@Column
	private Date DTINC;

	@Column
	private String USUARIOINC;

	@Column
	private Date DTALT;

	@Column
	private String USUARIOALT;

	@Column
	private String PADRAO;

	@Column
	private Integer IDUSUARIO;


	public String getDashboard(){
		return DASHBOARD;
	}


	public void setDashboard(String DASHBOARD){
		this.DASHBOARD = DASHBOARD;
	}


	public Integer getId(){
		return ID;
	}


	public void setId(Integer ID){
		this.ID = ID;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Date getDtinc(){
		return DTINC;
	}


	public void setDtinc(Date DTINC){
		this.DTINC = DTINC;
	}


	public String getUsuarioinc(){
		return USUARIOINC;
	}


	public void setUsuarioinc(String USUARIOINC){
		this.USUARIOINC = USUARIOINC;
	}


	public Date getDtalt(){
		return DTALT;
	}


	public void setDtalt(Date DTALT){
		this.DTALT = DTALT;
	}


	public String getUsuarioalt(){
		return USUARIOALT;
	}


	public void setUsuarioalt(String USUARIOALT){
		this.USUARIOALT = USUARIOALT;
	}


	public String getPadrao(){
		return PADRAO;
	}


	public void setPadrao(String PADRAO){
		this.PADRAO = PADRAO;
	}


	public Integer getIdusuario(){
		return IDUSUARIO;
	}


	public void setIdusuario(Integer IDUSUARIO){
		this.IDUSUARIO = IDUSUARIO;
	}

}