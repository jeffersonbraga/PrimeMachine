package br.com.primum.modelo.viasoft.ITEMCATEGORIA;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMCATEGORIA")
public class Itemcategoria  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private Integer IDSECAO;

	@Column
	private Integer IDGRUPOITEM;

	@Column
	private Integer IDSUBGRUPO;

	@Column
	private Integer IDMARCA;

	@Column
	private Integer IDDEPTO;

	@Column
	private Integer IDGRUPOCTB;

	@Column
	private Integer IDSIMILAR;

	@Column
	private String AGREGADOS;

	@Column
	private Integer IDSETOR;

	@Column
	private Integer IDFAMILIA;

	@Column
	private Integer IDSUBFAMILIA;


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


	public Integer getIdsecao(){
		return IDSECAO;
	}


	public void setIdsecao(Integer IDSECAO){
		this.IDSECAO = IDSECAO;
	}


	public Integer getIdgrupoitem(){
		return IDGRUPOITEM;
	}


	public void setIdgrupoitem(Integer IDGRUPOITEM){
		this.IDGRUPOITEM = IDGRUPOITEM;
	}


	public Integer getIdsubgrupo(){
		return IDSUBGRUPO;
	}


	public void setIdsubgrupo(Integer IDSUBGRUPO){
		this.IDSUBGRUPO = IDSUBGRUPO;
	}


	public Integer getIdmarca(){
		return IDMARCA;
	}


	public void setIdmarca(Integer IDMARCA){
		this.IDMARCA = IDMARCA;
	}


	public Integer getIddepto(){
		return IDDEPTO;
	}


	public void setIddepto(Integer IDDEPTO){
		this.IDDEPTO = IDDEPTO;
	}


	public Integer getIdgrupoctb(){
		return IDGRUPOCTB;
	}


	public void setIdgrupoctb(Integer IDGRUPOCTB){
		this.IDGRUPOCTB = IDGRUPOCTB;
	}


	public Integer getIdsimilar(){
		return IDSIMILAR;
	}


	public void setIdsimilar(Integer IDSIMILAR){
		this.IDSIMILAR = IDSIMILAR;
	}


	public String getAgregados(){
		return AGREGADOS;
	}


	public void setAgregados(String AGREGADOS){
		this.AGREGADOS = AGREGADOS;
	}


	public Integer getIdsetor(){
		return IDSETOR;
	}


	public void setIdsetor(Integer IDSETOR){
		this.IDSETOR = IDSETOR;
	}


	public Integer getIdfamilia(){
		return IDFAMILIA;
	}


	public void setIdfamilia(Integer IDFAMILIA){
		this.IDFAMILIA = IDFAMILIA;
	}


	public Integer getIdsubfamilia(){
		return IDSUBFAMILIA;
	}


	public void setIdsubfamilia(Integer IDSUBFAMILIA){
		this.IDSUBFAMILIA = IDSUBFAMILIA;
	}

}