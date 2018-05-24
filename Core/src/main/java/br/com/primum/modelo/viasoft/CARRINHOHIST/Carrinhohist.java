package br.com.primum.modelo.viasoft.CARRINHOHIST;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="CARRINHOHIST")
public class Carrinhohist  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer IDCARRINHO;

	@Column
	private Integer SEQHIST;

	@Column
	private String USERIDALT;

	@Column
	private String OLDVALUE;

	@Column
	private String NEWVALUE;

	@Column
	private Integer IDMOTIVORET;


	public Integer getIdcarrinho(){
		return IDCARRINHO;
	}


	public void setIdcarrinho(Integer IDCARRINHO){
		this.IDCARRINHO = IDCARRINHO;
	}


	public Integer getSeqhist(){
		return SEQHIST;
	}


	public void setSeqhist(Integer SEQHIST){
		this.SEQHIST = SEQHIST;
	}


	public String getUseridalt(){
		return USERIDALT;
	}


	public void setUseridalt(String USERIDALT){
		this.USERIDALT = USERIDALT;
	}


	public String getOldvalue(){
		return OLDVALUE;
	}


	public void setOldvalue(String OLDVALUE){
		this.OLDVALUE = OLDVALUE;
	}


	public String getNewvalue(){
		return NEWVALUE;
	}


	public void setNewvalue(String NEWVALUE){
		this.NEWVALUE = NEWVALUE;
	}


	public Integer getIdmotivoret(){
		return IDMOTIVORET;
	}


	public void setIdmotivoret(Integer IDMOTIVORET){
		this.IDMOTIVORET = IDMOTIVORET;
	}

}