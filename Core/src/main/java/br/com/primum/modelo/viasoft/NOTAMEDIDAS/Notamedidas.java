package br.com.primum.modelo.viasoft.NOTAMEDIDAS;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAMEDIDAS")
public class Notamedidas  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer SEQITEM;

	@Column
	private Integer ALTURA;

	@Column
	private Integer LARGURA;

	@Column
	private Integer COMPRIMENTO;

	@Column
	private Integer ESPESSURA;

	@Column
	private String STATUS;

	@Column
	private String OBSERVACAO;

	@Column
	private Integer IDVENDEDOR;

	@Column
	private Integer QTDE;

	@Column
	private Integer SEQ;

	@Column
	private Integer QTDEDEV;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public Integer getAltura(){
		return ALTURA;
	}


	public void setAltura(Integer ALTURA){
		this.ALTURA = ALTURA;
	}


	public Integer getLargura(){
		return LARGURA;
	}


	public void setLargura(Integer LARGURA){
		this.LARGURA = LARGURA;
	}


	public Integer getComprimento(){
		return COMPRIMENTO;
	}


	public void setComprimento(Integer COMPRIMENTO){
		this.COMPRIMENTO = COMPRIMENTO;
	}


	public Integer getEspessura(){
		return ESPESSURA;
	}


	public void setEspessura(Integer ESPESSURA){
		this.ESPESSURA = ESPESSURA;
	}


	public String getStatus(){
		return STATUS;
	}


	public void setStatus(String STATUS){
		this.STATUS = STATUS;
	}


	public String getObservacao(){
		return OBSERVACAO;
	}


	public void setObservacao(String OBSERVACAO){
		this.OBSERVACAO = OBSERVACAO;
	}


	public Integer getIdvendedor(){
		return IDVENDEDOR;
	}


	public void setIdvendedor(Integer IDVENDEDOR){
		this.IDVENDEDOR = IDVENDEDOR;
	}


	public Integer getQtde(){
		return QTDE;
	}


	public void setQtde(Integer QTDE){
		this.QTDE = QTDE;
	}


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public Integer getQtdedev(){
		return QTDEDEV;
	}


	public void setQtdedev(Integer QTDEDEV){
		this.QTDEDEV = QTDEDEV;
	}

}