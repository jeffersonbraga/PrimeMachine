package br.com.primum.modelo.viasoft.PESSOADOCTINTA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOADOCTINTA")
public class Pessoadoctinta  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer SEQ;

	@Column
	private Integer IDPESS;

	@Column
	private Integer IDBANDEIRA;

	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer IDFORMULA;

	@Column
	private String CODTINTA;

	@Column
	private String APLICACAO;

	@Column
	private Date DATAVENDA;

	@Column
	private Integer IDEMBTINTA;

	@Column
	private Integer IDCOLECAO;


	public Integer getSeq(){
		return SEQ;
	}


	public void setSeq(Integer SEQ){
		this.SEQ = SEQ;
	}


	public Integer getIdpess(){
		return IDPESS;
	}


	public void setIdpess(Integer IDPESS){
		this.IDPESS = IDPESS;
	}


	public Integer getIdbandeira(){
		return IDBANDEIRA;
	}


	public void setIdbandeira(Integer IDBANDEIRA){
		this.IDBANDEIRA = IDBANDEIRA;
	}


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


	public Integer getIdformula(){
		return IDFORMULA;
	}


	public void setIdformula(Integer IDFORMULA){
		this.IDFORMULA = IDFORMULA;
	}


	public String getCodtinta(){
		return CODTINTA;
	}


	public void setCodtinta(String CODTINTA){
		this.CODTINTA = CODTINTA;
	}


	public String getAplicacao(){
		return APLICACAO;
	}


	public void setAplicacao(String APLICACAO){
		this.APLICACAO = APLICACAO;
	}


	public Date getDatavenda(){
		return DATAVENDA;
	}


	public void setDatavenda(Date DATAVENDA){
		this.DATAVENDA = DATAVENDA;
	}


	public Integer getIdembtinta(){
		return IDEMBTINTA;
	}


	public void setIdembtinta(Integer IDEMBTINTA){
		this.IDEMBTINTA = IDEMBTINTA;
	}


	public Integer getIdcolecao(){
		return IDCOLECAO;
	}


	public void setIdcolecao(Integer IDCOLECAO){
		this.IDCOLECAO = IDCOLECAO;
	}

}