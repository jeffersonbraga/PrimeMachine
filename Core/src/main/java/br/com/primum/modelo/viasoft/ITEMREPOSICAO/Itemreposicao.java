package br.com.primum.modelo.viasoft.ITEMREPOSICAO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="ITEMREPOSICAO")
public class Itemreposicao  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private Integer ESTABLOCALRET;

	@Column
	private Integer DIASESTOQUEMIN;

	@Column
	private Integer DIASESTOQUEMAX;

	@Column
	private Integer MEDIAVENDA;

	@Column
	private Integer SAZOINI;

	@Column
	private Integer SAZOFIM;

	@Column
	private Date DTULTREPOSICAO;

	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ESTABITEM;

	@Column
	private String IDITEM;

	@Column
	private Integer ESTOQUEMINIMO;

	@Column
	private Integer ESTOQUEMAXIMO;

	@Column
	private String GIRO;

	@Column
	private Integer PRAZOMEDIO;

	@Column
	private Integer QTDEMINLOJA;

	@Column
	private Integer QTDEMAXLOJA;

	@Column
	private Integer IDMOTIVOINAT;

	@Column
	private String ENCOMENDA;

	@Column
	private String INATIVO;

	@Column
	private Date INATIVOATE;

	@Column
	private Integer PONTAESTQTD;

	@Column
	private String PONTAEST;

	@Column
	private String CLASSMARGEM;

	@Column
	private String CLASSLUCRO;

	@Column
	private String CLASSVDA;

	@Column
	private String CLASSQTDE;

	@Column
	private String CLASSCUSTO;

	@Column
	private String PADENTREGA;

	@Column
	private Integer IDLOCALRETIRADA;


	public Integer getEstablocalret(){
		return ESTABLOCALRET;
	}


	public void setEstablocalret(Integer ESTABLOCALRET){
		this.ESTABLOCALRET = ESTABLOCALRET;
	}


	public Integer getDiasestoquemin(){
		return DIASESTOQUEMIN;
	}


	public void setDiasestoquemin(Integer DIASESTOQUEMIN){
		this.DIASESTOQUEMIN = DIASESTOQUEMIN;
	}


	public Integer getDiasestoquemax(){
		return DIASESTOQUEMAX;
	}


	public void setDiasestoquemax(Integer DIASESTOQUEMAX){
		this.DIASESTOQUEMAX = DIASESTOQUEMAX;
	}


	public Integer getMediavenda(){
		return MEDIAVENDA;
	}


	public void setMediavenda(Integer MEDIAVENDA){
		this.MEDIAVENDA = MEDIAVENDA;
	}


	public Integer getSazoini(){
		return SAZOINI;
	}


	public void setSazoini(Integer SAZOINI){
		this.SAZOINI = SAZOINI;
	}


	public Integer getSazofim(){
		return SAZOFIM;
	}


	public void setSazofim(Integer SAZOFIM){
		this.SAZOFIM = SAZOFIM;
	}


	public Date getDtultreposicao(){
		return DTULTREPOSICAO;
	}


	public void setDtultreposicao(Date DTULTREPOSICAO){
		this.DTULTREPOSICAO = DTULTREPOSICAO;
	}


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


	public Integer getEstoqueminimo(){
		return ESTOQUEMINIMO;
	}


	public void setEstoqueminimo(Integer ESTOQUEMINIMO){
		this.ESTOQUEMINIMO = ESTOQUEMINIMO;
	}


	public Integer getEstoquemaximo(){
		return ESTOQUEMAXIMO;
	}


	public void setEstoquemaximo(Integer ESTOQUEMAXIMO){
		this.ESTOQUEMAXIMO = ESTOQUEMAXIMO;
	}


	public String getGiro(){
		return GIRO;
	}


	public void setGiro(String GIRO){
		this.GIRO = GIRO;
	}


	public Integer getPrazomedio(){
		return PRAZOMEDIO;
	}


	public void setPrazomedio(Integer PRAZOMEDIO){
		this.PRAZOMEDIO = PRAZOMEDIO;
	}


	public Integer getQtdeminloja(){
		return QTDEMINLOJA;
	}


	public void setQtdeminloja(Integer QTDEMINLOJA){
		this.QTDEMINLOJA = QTDEMINLOJA;
	}


	public Integer getQtdemaxloja(){
		return QTDEMAXLOJA;
	}


	public void setQtdemaxloja(Integer QTDEMAXLOJA){
		this.QTDEMAXLOJA = QTDEMAXLOJA;
	}


	public Integer getIdmotivoinat(){
		return IDMOTIVOINAT;
	}


	public void setIdmotivoinat(Integer IDMOTIVOINAT){
		this.IDMOTIVOINAT = IDMOTIVOINAT;
	}


	public String getEncomenda(){
		return ENCOMENDA;
	}


	public void setEncomenda(String ENCOMENDA){
		this.ENCOMENDA = ENCOMENDA;
	}


	public String getInativo(){
		return INATIVO;
	}


	public void setInativo(String INATIVO){
		this.INATIVO = INATIVO;
	}


	public Date getInativoate(){
		return INATIVOATE;
	}


	public void setInativoate(Date INATIVOATE){
		this.INATIVOATE = INATIVOATE;
	}


	public Integer getPontaestqtd(){
		return PONTAESTQTD;
	}


	public void setPontaestqtd(Integer PONTAESTQTD){
		this.PONTAESTQTD = PONTAESTQTD;
	}


	public String getPontaest(){
		return PONTAEST;
	}


	public void setPontaest(String PONTAEST){
		this.PONTAEST = PONTAEST;
	}


	public String getClassmargem(){
		return CLASSMARGEM;
	}


	public void setClassmargem(String CLASSMARGEM){
		this.CLASSMARGEM = CLASSMARGEM;
	}


	public String getClasslucro(){
		return CLASSLUCRO;
	}


	public void setClasslucro(String CLASSLUCRO){
		this.CLASSLUCRO = CLASSLUCRO;
	}


	public String getClassvda(){
		return CLASSVDA;
	}


	public void setClassvda(String CLASSVDA){
		this.CLASSVDA = CLASSVDA;
	}


	public String getClassqtde(){
		return CLASSQTDE;
	}


	public void setClassqtde(String CLASSQTDE){
		this.CLASSQTDE = CLASSQTDE;
	}


	public String getClasscusto(){
		return CLASSCUSTO;
	}


	public void setClasscusto(String CLASSCUSTO){
		this.CLASSCUSTO = CLASSCUSTO;
	}


	public String getPadentrega(){
		return PADENTREGA;
	}


	public void setPadentrega(String PADENTREGA){
		this.PADENTREGA = PADENTREGA;
	}


	public Integer getIdlocalretirada(){
		return IDLOCALRETIRADA;
	}


	public void setIdlocalretirada(Integer IDLOCALRETIRADA){
		this.IDLOCALRETIRADA = IDLOCALRETIRADA;
	}

}