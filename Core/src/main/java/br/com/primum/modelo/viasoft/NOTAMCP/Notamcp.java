package br.com.primum.modelo.viasoft.NOTAMCP;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="NOTAMCP")
public class Notamcp  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer IDAUTORIZADO;

	@Column
	private Integer IDCAIXACANC;

	@Column
	private String EXPEDICAOLIBERADA;

	@Column
	private String PASSOUCAIXA;

	@Column
	private Integer IDMOTIVODEV;

	@Column
	private String MOTIVODEV;

	@Column
	private Integer IDENDENTREGA;

	@Column
	private String OBSEXPEDICAO;

	@Column
	private String IMPORTADA;

	@Column
	private Date DTCOMIS;

	@Column
	private String VALAUTORIZADO;

	@Column
	private String ENTIMEDIATALIBERADA;

	@Column
	private Integer DESCCAIXA;

	@Column
	private Integer IDPESSAVALISTA;

	@Column
	private String BXDUPDEV;

	@Column
	private String VISUFORMPRECOVDA;

	@Column
	private Integer GRUPOROTEIRO;

	@Column
	private String QTDVOLUMEEXP;

	@Column
	private String DANFEIMPRESSA;

	@Column
	private String RETORNOCX;

	@Column
	private String USERFINALIZOU;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getIdautorizado(){
		return IDAUTORIZADO;
	}


	public void setIdautorizado(Integer IDAUTORIZADO){
		this.IDAUTORIZADO = IDAUTORIZADO;
	}


	public Integer getIdcaixacanc(){
		return IDCAIXACANC;
	}


	public void setIdcaixacanc(Integer IDCAIXACANC){
		this.IDCAIXACANC = IDCAIXACANC;
	}


	public String getExpedicaoliberada(){
		return EXPEDICAOLIBERADA;
	}


	public void setExpedicaoliberada(String EXPEDICAOLIBERADA){
		this.EXPEDICAOLIBERADA = EXPEDICAOLIBERADA;
	}


	public String getPassoucaixa(){
		return PASSOUCAIXA;
	}


	public void setPassoucaixa(String PASSOUCAIXA){
		this.PASSOUCAIXA = PASSOUCAIXA;
	}


	public Integer getIdmotivodev(){
		return IDMOTIVODEV;
	}


	public void setIdmotivodev(Integer IDMOTIVODEV){
		this.IDMOTIVODEV = IDMOTIVODEV;
	}


	public String getMotivodev(){
		return MOTIVODEV;
	}


	public void setMotivodev(String MOTIVODEV){
		this.MOTIVODEV = MOTIVODEV;
	}


	public Integer getIdendentrega(){
		return IDENDENTREGA;
	}


	public void setIdendentrega(Integer IDENDENTREGA){
		this.IDENDENTREGA = IDENDENTREGA;
	}


	public String getObsexpedicao(){
		return OBSEXPEDICAO;
	}


	public void setObsexpedicao(String OBSEXPEDICAO){
		this.OBSEXPEDICAO = OBSEXPEDICAO;
	}


	public String getImportada(){
		return IMPORTADA;
	}


	public void setImportada(String IMPORTADA){
		this.IMPORTADA = IMPORTADA;
	}


	public Date getDtcomis(){
		return DTCOMIS;
	}


	public void setDtcomis(Date DTCOMIS){
		this.DTCOMIS = DTCOMIS;
	}


	public String getValautorizado(){
		return VALAUTORIZADO;
	}


	public void setValautorizado(String VALAUTORIZADO){
		this.VALAUTORIZADO = VALAUTORIZADO;
	}


	public String getEntimediataliberada(){
		return ENTIMEDIATALIBERADA;
	}


	public void setEntimediataliberada(String ENTIMEDIATALIBERADA){
		this.ENTIMEDIATALIBERADA = ENTIMEDIATALIBERADA;
	}


	public Integer getDesccaixa(){
		return DESCCAIXA;
	}


	public void setDesccaixa(Integer DESCCAIXA){
		this.DESCCAIXA = DESCCAIXA;
	}


	public Integer getIdpessavalista(){
		return IDPESSAVALISTA;
	}


	public void setIdpessavalista(Integer IDPESSAVALISTA){
		this.IDPESSAVALISTA = IDPESSAVALISTA;
	}


	public String getBxdupdev(){
		return BXDUPDEV;
	}


	public void setBxdupdev(String BXDUPDEV){
		this.BXDUPDEV = BXDUPDEV;
	}


	public String getVisuformprecovda(){
		return VISUFORMPRECOVDA;
	}


	public void setVisuformprecovda(String VISUFORMPRECOVDA){
		this.VISUFORMPRECOVDA = VISUFORMPRECOVDA;
	}


	public Integer getGruporoteiro(){
		return GRUPOROTEIRO;
	}


	public void setGruporoteiro(Integer GRUPOROTEIRO){
		this.GRUPOROTEIRO = GRUPOROTEIRO;
	}


	public String getQtdvolumeexp(){
		return QTDVOLUMEEXP;
	}


	public void setQtdvolumeexp(String QTDVOLUMEEXP){
		this.QTDVOLUMEEXP = QTDVOLUMEEXP;
	}


	public String getDanfeimpressa(){
		return DANFEIMPRESSA;
	}


	public void setDanfeimpressa(String DANFEIMPRESSA){
		this.DANFEIMPRESSA = DANFEIMPRESSA;
	}


	public String getRetornocx(){
		return RETORNOCX;
	}


	public void setRetornocx(String RETORNOCX){
		this.RETORNOCX = RETORNOCX;
	}


	public String getUserfinalizou(){
		return USERFINALIZOU;
	}


	public void setUserfinalizou(String USERFINALIZOU){
		this.USERFINALIZOU = USERFINALIZOU;
	}

}