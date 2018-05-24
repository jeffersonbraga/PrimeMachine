package br.com.primum.modelo.viasoft.ITEMMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMMCP")
public class Itemmcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String IDITEM;

	@Column
	private String KITALT;

	@Column
	private Integer ESTABCAD;

	@Column
	private String KITVALINFO;

	@Column
	private String TINTA;

	@Column
	private String DESCPIG;

	@Column
	private String VDAINDIVIDUAL;

	@Column
	private String ITEMVDAIND;

	@Column
	private String IMPETIQUETA;

	@Column
	private String MONTA;

	@Column
	private Integer MESGARANTIA;


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


	public String getKitalt(){
		return KITALT;
	}


	public void setKitalt(String KITALT){
		this.KITALT = KITALT;
	}


	public Integer getEstabcad(){
		return ESTABCAD;
	}


	public void setEstabcad(Integer ESTABCAD){
		this.ESTABCAD = ESTABCAD;
	}


	public String getKitvalinfo(){
		return KITVALINFO;
	}


	public void setKitvalinfo(String KITVALINFO){
		this.KITVALINFO = KITVALINFO;
	}


	public String getTinta(){
		return TINTA;
	}


	public void setTinta(String TINTA){
		this.TINTA = TINTA;
	}


	public String getDescpig(){
		return DESCPIG;
	}


	public void setDescpig(String DESCPIG){
		this.DESCPIG = DESCPIG;
	}


	public String getVdaindividual(){
		return VDAINDIVIDUAL;
	}


	public void setVdaindividual(String VDAINDIVIDUAL){
		this.VDAINDIVIDUAL = VDAINDIVIDUAL;
	}


	public String getItemvdaind(){
		return ITEMVDAIND;
	}


	public void setItemvdaind(String ITEMVDAIND){
		this.ITEMVDAIND = ITEMVDAIND;
	}


	public String getImpetiqueta(){
		return IMPETIQUETA;
	}


	public void setImpetiqueta(String IMPETIQUETA){
		this.IMPETIQUETA = IMPETIQUETA;
	}


	public String getMonta(){
		return MONTA;
	}


	public void setMonta(String MONTA){
		this.MONTA = MONTA;
	}


	public Integer getMesgarantia(){
		return MESGARANTIA;
	}


	public void setMesgarantia(Integer MESGARANTIA){
		this.MESGARANTIA = MESGARANTIA;
	}

}