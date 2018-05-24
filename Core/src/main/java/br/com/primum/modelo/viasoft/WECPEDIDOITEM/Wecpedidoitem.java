package br.com.primum.modelo.viasoft.WECPEDIDOITEM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECPEDIDOITEM")
public class Wecpedidoitem  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String IDITEM;

	@Column
	private String DESCRICAO;

	@Column
	private Integer SUBTOTAL;

	@Column
	private Integer QUANTIDADE;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer VALORTOTAL;

	@Column
	private Integer SEQITEM;

	@Column
	private String PEDCODIGO;

	@Column
	private String XMLITEM;


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public Integer getSubtotal(){
		return SUBTOTAL;
	}


	public void setSubtotal(Integer SUBTOTAL){
		this.SUBTOTAL = SUBTOTAL;
	}


	public Integer getQuantidade(){
		return QUANTIDADE;
	}


	public void setQuantidade(Integer QUANTIDADE){
		this.QUANTIDADE = QUANTIDADE;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getValortotal(){
		return VALORTOTAL;
	}


	public void setValortotal(Integer VALORTOTAL){
		this.VALORTOTAL = VALORTOTAL;
	}


	public Integer getSeqitem(){
		return SEQITEM;
	}


	public void setSeqitem(Integer SEQITEM){
		this.SEQITEM = SEQITEM;
	}


	public String getPedcodigo(){
		return PEDCODIGO;
	}


	public void setPedcodigo(String PEDCODIGO){
		this.PEDCODIGO = PEDCODIGO;
	}


	public String getXmlitem(){
		return XMLITEM;
	}


	public void setXmlitem(String XMLITEM){
		this.XMLITEM = XMLITEM;
	}

}