package br.com.primum.modelo.viasoft.U_LITROS_PRODUTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_LITROS_PRODUTO")
public class U_litros_produto  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_LITROS_PRODUTO_ID;

	@Column
	private String IDITEM;

	@Column
	private Integer LITROS;


	public Integer getU_litros_produto_id(){
		return U_LITROS_PRODUTO_ID;
	}


	public void setU_litros_produto_id(Integer U_LITROS_PRODUTO_ID){
		this.U_LITROS_PRODUTO_ID = U_LITROS_PRODUTO_ID;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public Integer getLitros(){
		return LITROS;
	}


	public void setLitros(Integer LITROS){
		this.LITROS = LITROS;
	}

}