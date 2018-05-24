package br.com.primum.modelo.viasoft.INVENTARIO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="INVENTARIO")
public class Inventario  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDINVENTARIO;

	@Column
	private String DESCRICAO;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private String BLOQUEADO;

	@Column
	private String PARCIAL;

	@Column
	private Integer IDNOTAE;

	@Column
	private Integer IDNOTAS;

	@Column
	private String TIPOCUSTO;

	@Column
	private String USERID;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdinventario(){
		return IDINVENTARIO;
	}


	public void setIdinventario(Integer IDINVENTARIO){
		this.IDINVENTARIO = IDINVENTARIO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public String getBloqueado(){
		return BLOQUEADO;
	}


	public void setBloqueado(String BLOQUEADO){
		this.BLOQUEADO = BLOQUEADO;
	}


	public String getParcial(){
		return PARCIAL;
	}


	public void setParcial(String PARCIAL){
		this.PARCIAL = PARCIAL;
	}


	public Integer getIdnotae(){
		return IDNOTAE;
	}


	public void setIdnotae(Integer IDNOTAE){
		this.IDNOTAE = IDNOTAE;
	}


	public Integer getIdnotas(){
		return IDNOTAS;
	}


	public void setIdnotas(Integer IDNOTAS){
		this.IDNOTAS = IDNOTAS;
	}


	public String getTipocusto(){
		return TIPOCUSTO;
	}


	public void setTipocusto(String TIPOCUSTO){
		this.TIPOCUSTO = TIPOCUSTO;
	}


	public String getUserid(){
		return USERID;
	}


	public void setUserid(String USERID){
		this.USERID = USERID;
	}

}