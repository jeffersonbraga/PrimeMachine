package br.com.primum.modelo.viasoft.WECPEDIDO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="WECPEDIDO")
public class Wecpedido  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer CLICODIGO;

	@Column
	private String CLINOMERAZAOSOC;

	@Column
	private String CLICNPJF;

	@Column
	private String CLIEMAIL;

	@Column
	private String CLIENDERECO;

	@Column
	private String CLINUMERO;

	@Column
	private String CLIBAIRRO;

	@Column
	private String CLICOMPL;

	@Column
	private String CLICEP;

	@Column
	private String CLICIDADE;

	@Column
	private String CLIESTADO;

	@Column
	private String CLIPAIS;

	@Column
	private String CLITEL;

	@Column
	private String CLICEL;

	@Column
	private String PEDIDOSTATUS;

	@Column
	private Date PEDATA;

	@Column
	private Integer PEDSUBTOTAL;

	@Column
	private String PEDFORMAENTREGA;

	@Column
	private Integer PEDFRETE;

	@Column
	private String PEDTIPOPAGTO;

	@Column
	private String PEDFORMAPAGTO;

	@Column
	private Integer PEDPARCELAS;

	@Column
	private String PEDTRANSACAOID;

	@Column
	private String ENTENDERECO;

	@Column
	private String ENTNUMERO;

	@Column
	private String ENTBAIRRO;

	@Column
	private String ENTCOMPL;

	@Column
	private String ENTCEP;

	@Column
	private String ENTCIDADE;

	@Column
	private String ENTESTADO;

	@Column
	private String ENTPAIS;

	@Column
	private String ENTTEL;

	@Column
	private String ENTCEL;

	@Column
	private Integer IDNOTA;

	@Column
	private Integer ESTAB;

	@Column
	private String PEDTIPOCC;

	@Column
	private Integer PEDTOTAL;

	@Column
	private String PEDCODIGO;


	public Integer getClicodigo(){
		return CLICODIGO;
	}


	public void setClicodigo(Integer CLICODIGO){
		this.CLICODIGO = CLICODIGO;
	}


	public String getClinomerazaosoc(){
		return CLINOMERAZAOSOC;
	}


	public void setClinomerazaosoc(String CLINOMERAZAOSOC){
		this.CLINOMERAZAOSOC = CLINOMERAZAOSOC;
	}


	public String getClicnpjf(){
		return CLICNPJF;
	}


	public void setClicnpjf(String CLICNPJF){
		this.CLICNPJF = CLICNPJF;
	}


	public String getCliemail(){
		return CLIEMAIL;
	}


	public void setCliemail(String CLIEMAIL){
		this.CLIEMAIL = CLIEMAIL;
	}


	public String getCliendereco(){
		return CLIENDERECO;
	}


	public void setCliendereco(String CLIENDERECO){
		this.CLIENDERECO = CLIENDERECO;
	}


	public String getClinumero(){
		return CLINUMERO;
	}


	public void setClinumero(String CLINUMERO){
		this.CLINUMERO = CLINUMERO;
	}


	public String getClibairro(){
		return CLIBAIRRO;
	}


	public void setClibairro(String CLIBAIRRO){
		this.CLIBAIRRO = CLIBAIRRO;
	}


	public String getClicompl(){
		return CLICOMPL;
	}


	public void setClicompl(String CLICOMPL){
		this.CLICOMPL = CLICOMPL;
	}


	public String getClicep(){
		return CLICEP;
	}


	public void setClicep(String CLICEP){
		this.CLICEP = CLICEP;
	}


	public String getClicidade(){
		return CLICIDADE;
	}


	public void setClicidade(String CLICIDADE){
		this.CLICIDADE = CLICIDADE;
	}


	public String getCliestado(){
		return CLIESTADO;
	}


	public void setCliestado(String CLIESTADO){
		this.CLIESTADO = CLIESTADO;
	}


	public String getClipais(){
		return CLIPAIS;
	}


	public void setClipais(String CLIPAIS){
		this.CLIPAIS = CLIPAIS;
	}


	public String getClitel(){
		return CLITEL;
	}


	public void setClitel(String CLITEL){
		this.CLITEL = CLITEL;
	}


	public String getClicel(){
		return CLICEL;
	}


	public void setClicel(String CLICEL){
		this.CLICEL = CLICEL;
	}


	public String getPedidostatus(){
		return PEDIDOSTATUS;
	}


	public void setPedidostatus(String PEDIDOSTATUS){
		this.PEDIDOSTATUS = PEDIDOSTATUS;
	}


	public Date getPedata(){
		return PEDATA;
	}


	public void setPedata(Date PEDATA){
		this.PEDATA = PEDATA;
	}


	public Integer getPedsubtotal(){
		return PEDSUBTOTAL;
	}


	public void setPedsubtotal(Integer PEDSUBTOTAL){
		this.PEDSUBTOTAL = PEDSUBTOTAL;
	}


	public String getPedformaentrega(){
		return PEDFORMAENTREGA;
	}


	public void setPedformaentrega(String PEDFORMAENTREGA){
		this.PEDFORMAENTREGA = PEDFORMAENTREGA;
	}


	public Integer getPedfrete(){
		return PEDFRETE;
	}


	public void setPedfrete(Integer PEDFRETE){
		this.PEDFRETE = PEDFRETE;
	}


	public String getPedtipopagto(){
		return PEDTIPOPAGTO;
	}


	public void setPedtipopagto(String PEDTIPOPAGTO){
		this.PEDTIPOPAGTO = PEDTIPOPAGTO;
	}


	public String getPedformapagto(){
		return PEDFORMAPAGTO;
	}


	public void setPedformapagto(String PEDFORMAPAGTO){
		this.PEDFORMAPAGTO = PEDFORMAPAGTO;
	}


	public Integer getPedparcelas(){
		return PEDPARCELAS;
	}


	public void setPedparcelas(Integer PEDPARCELAS){
		this.PEDPARCELAS = PEDPARCELAS;
	}


	public String getPedtransacaoid(){
		return PEDTRANSACAOID;
	}


	public void setPedtransacaoid(String PEDTRANSACAOID){
		this.PEDTRANSACAOID = PEDTRANSACAOID;
	}


	public String getEntendereco(){
		return ENTENDERECO;
	}


	public void setEntendereco(String ENTENDERECO){
		this.ENTENDERECO = ENTENDERECO;
	}


	public String getEntnumero(){
		return ENTNUMERO;
	}


	public void setEntnumero(String ENTNUMERO){
		this.ENTNUMERO = ENTNUMERO;
	}


	public String getEntbairro(){
		return ENTBAIRRO;
	}


	public void setEntbairro(String ENTBAIRRO){
		this.ENTBAIRRO = ENTBAIRRO;
	}


	public String getEntcompl(){
		return ENTCOMPL;
	}


	public void setEntcompl(String ENTCOMPL){
		this.ENTCOMPL = ENTCOMPL;
	}


	public String getEntcep(){
		return ENTCEP;
	}


	public void setEntcep(String ENTCEP){
		this.ENTCEP = ENTCEP;
	}


	public String getEntcidade(){
		return ENTCIDADE;
	}


	public void setEntcidade(String ENTCIDADE){
		this.ENTCIDADE = ENTCIDADE;
	}


	public String getEntestado(){
		return ENTESTADO;
	}


	public void setEntestado(String ENTESTADO){
		this.ENTESTADO = ENTESTADO;
	}


	public String getEntpais(){
		return ENTPAIS;
	}


	public void setEntpais(String ENTPAIS){
		this.ENTPAIS = ENTPAIS;
	}


	public String getEnttel(){
		return ENTTEL;
	}


	public void setEnttel(String ENTTEL){
		this.ENTTEL = ENTTEL;
	}


	public String getEntcel(){
		return ENTCEL;
	}


	public void setEntcel(String ENTCEL){
		this.ENTCEL = ENTCEL;
	}


	public Integer getIdnota(){
		return IDNOTA;
	}


	public void setIdnota(Integer IDNOTA){
		this.IDNOTA = IDNOTA;
	}


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getPedtipocc(){
		return PEDTIPOCC;
	}


	public void setPedtipocc(String PEDTIPOCC){
		this.PEDTIPOCC = PEDTIPOCC;
	}


	public Integer getPedtotal(){
		return PEDTOTAL;
	}


	public void setPedtotal(Integer PEDTOTAL){
		this.PEDTOTAL = PEDTOTAL;
	}


	public String getPedcodigo(){
		return PEDCODIGO;
	}


	public void setPedcodigo(String PEDCODIGO){
		this.PEDCODIGO = PEDCODIGO;
	}

}