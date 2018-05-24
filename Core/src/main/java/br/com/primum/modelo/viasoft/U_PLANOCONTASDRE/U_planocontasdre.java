package br.com.primum.modelo.viasoft.U_PLANOCONTASDRE;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="U_PLANOCONTASDRE")
public class U_planocontasdre  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer U_PLANOCONTASDRE_ID;

	@Column
	private String CONTABIL;

	@Column
	private String CONTABILPAI;

	@Column
	private String TIPOENTSAI;

	@Column
	private String ORDEM;

	@Column
	private String AGRUPARCOM;

	@Column
	private String ANALITICA;

	@Column
	private String EXIBIR;

	@Column
	private String TIPOBUSCA;

	@Column
	private String DESCRICAO;

	@Column
	private String GRUPO;


	public Integer getU_planocontasdre_id(){
		return U_PLANOCONTASDRE_ID;
	}


	public void setU_planocontasdre_id(Integer U_PLANOCONTASDRE_ID){
		this.U_PLANOCONTASDRE_ID = U_PLANOCONTASDRE_ID;
	}


	public String getContabil(){
		return CONTABIL;
	}


	public void setContabil(String CONTABIL){
		this.CONTABIL = CONTABIL;
	}


	public String getContabilpai(){
		return CONTABILPAI;
	}


	public void setContabilpai(String CONTABILPAI){
		this.CONTABILPAI = CONTABILPAI;
	}


	public String getTipoentsai(){
		return TIPOENTSAI;
	}


	public void setTipoentsai(String TIPOENTSAI){
		this.TIPOENTSAI = TIPOENTSAI;
	}


	public String getOrdem(){
		return ORDEM;
	}


	public void setOrdem(String ORDEM){
		this.ORDEM = ORDEM;
	}


	public String getAgruparcom(){
		return AGRUPARCOM;
	}


	public void setAgruparcom(String AGRUPARCOM){
		this.AGRUPARCOM = AGRUPARCOM;
	}


	public String getAnalitica(){
		return ANALITICA;
	}


	public void setAnalitica(String ANALITICA){
		this.ANALITICA = ANALITICA;
	}


	public String getExibir(){
		return EXIBIR;
	}


	public void setExibir(String EXIBIR){
		this.EXIBIR = EXIBIR;
	}


	public String getTipobusca(){
		return TIPOBUSCA;
	}


	public void setTipobusca(String TIPOBUSCA){
		this.TIPOBUSCA = TIPOBUSCA;
	}


	public String getDescricao(){
		return DESCRICAO;
	}


	public void setDescricao(String DESCRICAO){
		this.DESCRICAO = DESCRICAO;
	}


	public String getGrupo(){
		return GRUPO;
	}


	public void setGrupo(String GRUPO){
		this.GRUPO = GRUPO;
	}

}