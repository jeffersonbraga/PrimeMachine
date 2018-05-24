package br.com.primum.modelo.viasoft.LOTE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="LOTE")
public class Lote  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private String LOTE;

	@Column
	private Date VALIDADE;

	@Column
	private Integer PRECO;

	@Column
	private String PONTAEST;

	@Column
	private Integer PONTAESTQTD;

	@Column
	private String LOTEFORN;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getLote(){
		return LOTE;
	}


	public void setLote(String LOTE){
		this.LOTE = LOTE;
	}


	public Date getValidade(){
		return VALIDADE;
	}


	public void setValidade(Date VALIDADE){
		this.VALIDADE = VALIDADE;
	}


	public Integer getPreco(){
		return PRECO;
	}


	public void setPreco(Integer PRECO){
		this.PRECO = PRECO;
	}


	public String getPontaest(){
		return PONTAEST;
	}


	public void setPontaest(String PONTAEST){
		this.PONTAEST = PONTAEST;
	}


	public Integer getPontaestqtd(){
		return PONTAESTQTD;
	}


	public void setPontaestqtd(Integer PONTAESTQTD){
		this.PONTAESTQTD = PONTAESTQTD;
	}


	public String getLoteforn(){
		return LOTEFORN;
	}


	public void setLoteforn(String LOTEFORN){
		this.LOTEFORN = LOTEFORN;
	}

}