package br.com.primum.modelo.viasoft.U_ANALITICA_LNFIXA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_ANALITICA_LNFIXA")
public class U_analitica_lnfixa  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_ANALITICA_LNFIXA_ID;

	@Column
	private Integer U_LINHA_VARIAVEL_ID;

	@Column
	private Integer PLANO;

	@Column
	private Integer REDUZIDA;

	@Column
	private String TIPOBUSCA;


	public Integer getU_analitica_lnfixa_id(){
		return U_ANALITICA_LNFIXA_ID;
	}


	public void setU_analitica_lnfixa_id(Integer U_ANALITICA_LNFIXA_ID){
		this.U_ANALITICA_LNFIXA_ID = U_ANALITICA_LNFIXA_ID;
	}


	public Integer getU_linha_variavel_id(){
		return U_LINHA_VARIAVEL_ID;
	}


	public void setU_linha_variavel_id(Integer U_LINHA_VARIAVEL_ID){
		this.U_LINHA_VARIAVEL_ID = U_LINHA_VARIAVEL_ID;
	}


	public Integer getPlano(){
		return PLANO;
	}


	public void setPlano(Integer PLANO){
		this.PLANO = PLANO;
	}


	public Integer getReduzida(){
		return REDUZIDA;
	}


	public void setReduzida(Integer REDUZIDA){
		this.REDUZIDA = REDUZIDA;
	}


	public String getTipobusca(){
		return TIPOBUSCA;
	}


	public void setTipobusca(String TIPOBUSCA){
		this.TIPOBUSCA = TIPOBUSCA;
	}

}