package br.com.primum.modelo.viasoft.ITEMESTAB;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMESTAB")
public class Itemestab  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private String USAMIX;

	@Column
	private Integer PMZ;

	@Column
	private Integer IDTRIBICMS;

	@Column
	private Integer IDTRIBIPI;

	@Column
	private Integer IDTRIBPIS;

	@Column
	private Integer IDTRIBCOFINS;

	@Column
	private Integer IDESTOQUELOCAL;

	@Column
	private Integer IDGRUPOCTB;

	@Column
	private Integer IDDESCITEM;

	@Column
	private String ESTNEGATIVO;

	@Column
	private String EMBVDAOBG;

	@Column
	private Integer ID_OLD;

	@Column
	private Integer IDTRIBISS;

	@Column
	private String LOCALIZACAO;

	@Column
	private String OBSVDA;

	@Column
	private Integer IDTRIBFUNRURAL;

	@Column
	private String USAECOMMERCE;

	@Column
	private String USALOTE;

	@Column
	private Date DTULTCOMPRA;

	@Column
	private Date DTULTVENDA;

	@Column
	private String IGEMBPADIMP;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getEstabitem(){
		return ESTABITEM;
	}


	public void setEstabitem(Integer ESTABITEM){
		this.ESTABITEM = ESTABITEM;
	}


	public String getIditem(){
		return IDITEM;
	}


	public void setIditem(String IDITEM){
		this.IDITEM = IDITEM;
	}


	public String getUsamix(){
		return USAMIX;
	}


	public void setUsamix(String USAMIX){
		this.USAMIX = USAMIX;
	}


	public Integer getPmz(){
		return PMZ;
	}


	public void setPmz(Integer PMZ){
		this.PMZ = PMZ;
	}


	public Integer getIdtribicms(){
		return IDTRIBICMS;
	}


	public void setIdtribicms(Integer IDTRIBICMS){
		this.IDTRIBICMS = IDTRIBICMS;
	}


	public Integer getIdtribipi(){
		return IDTRIBIPI;
	}


	public void setIdtribipi(Integer IDTRIBIPI){
		this.IDTRIBIPI = IDTRIBIPI;
	}


	public Integer getIdtribpis(){
		return IDTRIBPIS;
	}


	public void setIdtribpis(Integer IDTRIBPIS){
		this.IDTRIBPIS = IDTRIBPIS;
	}


	public Integer getIdtribcofins(){
		return IDTRIBCOFINS;
	}


	public void setIdtribcofins(Integer IDTRIBCOFINS){
		this.IDTRIBCOFINS = IDTRIBCOFINS;
	}


	public Integer getIdestoquelocal(){
		return IDESTOQUELOCAL;
	}


	public void setIdestoquelocal(Integer IDESTOQUELOCAL){
		this.IDESTOQUELOCAL = IDESTOQUELOCAL;
	}


	public Integer getIdgrupoctb(){
		return IDGRUPOCTB;
	}


	public void setIdgrupoctb(Integer IDGRUPOCTB){
		this.IDGRUPOCTB = IDGRUPOCTB;
	}


	public Integer getIddescitem(){
		return IDDESCITEM;
	}


	public void setIddescitem(Integer IDDESCITEM){
		this.IDDESCITEM = IDDESCITEM;
	}


	public String getEstnegativo(){
		return ESTNEGATIVO;
	}


	public void setEstnegativo(String ESTNEGATIVO){
		this.ESTNEGATIVO = ESTNEGATIVO;
	}


	public String getEmbvdaobg(){
		return EMBVDAOBG;
	}


	public void setEmbvdaobg(String EMBVDAOBG){
		this.EMBVDAOBG = EMBVDAOBG;
	}


	public Integer getId_old(){
		return ID_OLD;
	}


	public void setId_old(Integer ID_OLD){
		this.ID_OLD = ID_OLD;
	}


	public Integer getIdtribiss(){
		return IDTRIBISS;
	}


	public void setIdtribiss(Integer IDTRIBISS){
		this.IDTRIBISS = IDTRIBISS;
	}


	public String getLocalizacao(){
		return LOCALIZACAO;
	}


	public void setLocalizacao(String LOCALIZACAO){
		this.LOCALIZACAO = LOCALIZACAO;
	}


	public String getObsvda(){
		return OBSVDA;
	}


	public void setObsvda(String OBSVDA){
		this.OBSVDA = OBSVDA;
	}


	public Integer getIdtribfunrural(){
		return IDTRIBFUNRURAL;
	}


	public void setIdtribfunrural(Integer IDTRIBFUNRURAL){
		this.IDTRIBFUNRURAL = IDTRIBFUNRURAL;
	}


	public String getUsaecommerce(){
		return USAECOMMERCE;
	}


	public void setUsaecommerce(String USAECOMMERCE){
		this.USAECOMMERCE = USAECOMMERCE;
	}


	public String getUsalote(){
		return USALOTE;
	}


	public void setUsalote(String USALOTE){
		this.USALOTE = USALOTE;
	}


	public Date getDtultcompra(){
		return DTULTCOMPRA;
	}


	public void setDtultcompra(Date DTULTCOMPRA){
		this.DTULTCOMPRA = DTULTCOMPRA;
	}


	public Date getDtultvenda(){
		return DTULTVENDA;
	}


	public void setDtultvenda(Date DTULTVENDA){
		this.DTULTVENDA = DTULTVENDA;
	}


	public String getIgembpadimp(){
		return IGEMBPADIMP;
	}


	public void setIgembpadimp(String IGEMBPADIMP){
		this.IGEMBPADIMP = IGEMBPADIMP;
	}

}