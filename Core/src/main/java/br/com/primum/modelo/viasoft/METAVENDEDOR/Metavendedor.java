package br.com.primum.modelo.viasoft.METAVENDEDOR;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="METAVENDEDOR")
public class Metavendedor  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ANO;

	@Column
	private Integer MES;

	@Column
	private Integer SEQMETA;

	@Column
	private Integer IDVENDEDOR;

	@Column
	private Integer VALORVENDA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getAno(){
		return ANO;
	}


	public void setAno(Integer ANO){
		this.ANO = ANO;
	}


	public Integer getMes(){
		return MES;
	}


	public void setMes(Integer MES){
		this.MES = MES;
	}


	public Integer getSeqmeta(){
		return SEQMETA;
	}


	public void setSeqmeta(Integer SEQMETA){
		this.SEQMETA = SEQMETA;
	}


	public Integer getIdvendedor(){
		return IDVENDEDOR;
	}


	public void setIdvendedor(Integer IDVENDEDOR){
		this.IDVENDEDOR = IDVENDEDOR;
	}


	public Integer getValorvenda(){
		return VALORVENDA;
	}


	public void setValorvenda(Integer VALORVENDA){
		this.VALORVENDA = VALORVENDA;
	}

}