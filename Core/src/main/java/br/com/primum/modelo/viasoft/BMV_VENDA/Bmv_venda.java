package br.com.primum.modelo.viasoft.BMV_VENDA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="BMV_VENDA")
public class Bmv_venda  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Date DATA;

	@Column
	private Integer IDVENDEDOR;

	@Column
	private Integer IDGRUPO;

	@Column
	private String IDITEM;

	@Column
	private String VENDEDOR;

	@Column
	private String GRUPO;

	@Column
	private Integer FATURAMENTO;

	@Column
	private Integer DESCONTO;

	@Column
	private Integer DEVOLUCAO;

	@Column
	private Integer CMV;

	@Column
	private Integer QTDVENDA;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Date getData(){
		return DATA;
	}


	public void setData(Date DATA){
		this.DATA = DATA;
	}


	public Integer getIdvendedor(){
		return IDVENDEDOR;
	}


	public void setIdvendedor(Integer IDVENDEDOR){
		this.IDVENDEDOR = IDVENDEDOR;
	}


	public Integer getIdgrupo(){
		return IDGRUPO;
	}


	public void setIdgrupo(Integer IDGRUPO){
		this.IDGRUPO = IDGRUPO;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getVendedor(){
		return VENDEDOR;
	}


	public void setVendedor(String VENDEDOR){
		this.VENDEDOR = VENDEDOR;
	}


	public String getGrupo(){
		return GRUPO;
	}


	public void setGrupo(String GRUPO){
		this.GRUPO = GRUPO;
	}


	public Integer getFaturamento(){
		return FATURAMENTO;
	}


	public void setFaturamento(Integer FATURAMENTO){
		this.FATURAMENTO = FATURAMENTO;
	}


	public Integer getDesconto(){
		return DESCONTO;
	}


	public void setDesconto(Integer DESCONTO){
		this.DESCONTO = DESCONTO;
	}


	public Integer getDevolucao(){
		return DEVOLUCAO;
	}


	public void setDevolucao(Integer DEVOLUCAO){
		this.DEVOLUCAO = DEVOLUCAO;
	}


	public Integer getCmv(){
		return CMV;
	}


	public void setCmv(Integer CMV){
		this.CMV = CMV;
	}


	public Integer getQtdvenda(){
		return QTDVENDA;
	}


	public void setQtdvenda(Integer QTDVENDA){
		this.QTDVENDA = QTDVENDA;
	}

}