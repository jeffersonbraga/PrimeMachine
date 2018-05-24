package br.com.primum.modelo.viasoft.PESSOACONCEITO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="PESSOACONCEITO")
public class Pessoaconceito  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer EMPRESA;

	@Column
	private Integer IDCONCEITO;

	@Column
	private String DESCRICAO;

	@Column
	private String VENDABLOQ;

	@Column
	private String VENDAPERIG;

	@Column
	private String VERIFLIMCRED;

	@Column
	private String VERIFDUPVENC;

	@Column
	private String PEDAPROV;

	@Column
	private String DOCVALIDA;

	@Column
	private String FORMACART;

	@Column
	private String FORMADUP;

	@Column
	private String FORMACHE;

	@Column
	private String FORMAOUT;

	@Column
	private String FORMACP;

	@Column
	private String MSGVDAPERIG;

	@Column
	private String OBGAVALISTA;

	@Column
	private String PERMITEPROMOCAO;


	public Integer getEmpresa(){
		return EMPRESA;
	}


	public void setEmpresa(Integer EMPRESA){
		this.EMPRESA = EMPRESA;
	}


	public Integer getIdconceito(){
		return IDCONCEITO;
	}


	public void setIdconceito(Integer IDCONCEITO){
		this.IDCONCEITO = IDCONCEITO;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getVendabloq(){
		return VENDABLOQ;
	}


	public void setVendabloq(String VENDABLOQ){
		this.VENDABLOQ = VENDABLOQ;
	}


	public String getVendaperig(){
		return VENDAPERIG;
	}


	public void setVendaperig(String VENDAPERIG){
		this.VENDAPERIG = VENDAPERIG;
	}


	public String getVeriflimcred(){
		return VERIFLIMCRED;
	}


	public void setVeriflimcred(String VERIFLIMCRED){
		this.VERIFLIMCRED = VERIFLIMCRED;
	}


	public String getVerifdupvenc(){
		return VERIFDUPVENC;
	}


	public void setVerifdupvenc(String VERIFDUPVENC){
		this.VERIFDUPVENC = VERIFDUPVENC;
	}


	public String getPedaprov(){
		return PEDAPROV;
	}


	public void setPedaprov(String PEDAPROV){
		this.PEDAPROV = PEDAPROV;
	}


	public String getDocvalida(){
		return DOCVALIDA;
	}


	public void setDocvalida(String DOCVALIDA){
		this.DOCVALIDA = DOCVALIDA;
	}


	public String getFormacart(){
		return FORMACART;
	}


	public void setFormacart(String FORMACART){
		this.FORMACART = FORMACART;
	}


	public String getFormadup(){
		return FORMADUP;
	}


	public void setFormadup(String FORMADUP){
		this.FORMADUP = FORMADUP;
	}


	public String getFormache(){
		return FORMACHE;
	}


	public void setFormache(String FORMACHE){
		this.FORMACHE = FORMACHE;
	}


	public String getFormaout(){
		return FORMAOUT;
	}


	public void setFormaout(String FORMAOUT){
		this.FORMAOUT = FORMAOUT;
	}


	public String getFormacp(){
		return FORMACP;
	}


	public void setFormacp(String FORMACP){
		this.FORMACP = FORMACP;
	}


	public String getMsgvdaperig(){
		return MSGVDAPERIG;
	}


	public void setMsgvdaperig(String MSGVDAPERIG){
		this.MSGVDAPERIG = MSGVDAPERIG;
	}


	public String getObgavalista(){
		return OBGAVALISTA;
	}


	public void setObgavalista(String OBGAVALISTA){
		this.OBGAVALISTA = OBGAVALISTA;
	}


	public String getPermitepromocao(){
		return PERMITEPROMOCAO;
	}


	public void setPermitepromocao(String PERMITEPROMOCAO){
		this.PERMITEPROMOCAO = PERMITEPROMOCAO;
	}

}