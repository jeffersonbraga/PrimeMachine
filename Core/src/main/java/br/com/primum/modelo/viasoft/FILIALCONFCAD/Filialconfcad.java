package br.com.primum.modelo.viasoft.FILIALCONFCAD;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFCAD")
public class Filialconfcad  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private Integer ITEMGIRO;

	@Column
	private Integer ITEMRENTAB;

	@Column
	private Integer ITEMVENDA;

	@Column
	private Integer ITEMQTDE;

	@Column
	private Integer DIASVALPED;

	@Column
	private Integer DIASPREVENT;

	@Column
	private String USAMIXPAD;

	@Column
	private String TABPRECODISP;

	@Column
	private String VALCNPJF;

	@Column
	private Integer LIMCREDPAD;

	@Column
	private Integer LIMCREDPADMEN;

	@Column
	private Integer SISTCODBAR;

	@Column
	private String CODPAIS;

	@Column
	private String CODEMPRESA;

	@Column
	private Integer IDBANCUSTO;

	@Column
	private Integer IDBANPRECO;

	@Column
	private Integer IDBANITEPESS;

	@Column
	private String SERIENFE;

	@Column
	private Integer PERCRENDALIMMES;

	@Column
	private Integer PERCRENDALIMTOT;

	@Column
	private Integer DIASREAVPESS;

	@Column
	private Integer DIASBLOQULTVDA;

	@Column
	private Integer CARENCIASALDODEV;

	@Column
	private String USACUSTO;

	@Column
	private String IDITEMFRETE;

	@Column
	private Integer LIMCTPESSPAD;

	@Column
	private Integer IDBANITEMEST;

	@Column
	private String CLASSMERC;

	@Column
	private String ALTLOCRET;

	@Column
	private Integer PESSOAMODELO;

	@Column
	private String VALCADEND;

	@Column
	private Integer LIMRENDAFAM;

	@Column
	private Integer IDCONCEITOBLOQ;

	@Column
	private String VALREGIONAL;

	@Column
	private Integer ITEMMODELO;

	@Column
	private String INATIVASAIDA;

	@Column
	private Integer IDITEMDESPESA;

	@Column
	private Integer IDBANTRIB;

	@Column
	private Integer IDBANEMB;

	@Column
	private String VALEAN;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public Integer getItemgiro(){
		return ITEMGIRO;
	}


	public void setItemgiro(Integer ITEMGIRO){
		this.ITEMGIRO = ITEMGIRO;
	}


	public Integer getItemrentab(){
		return ITEMRENTAB;
	}


	public void setItemrentab(Integer ITEMRENTAB){
		this.ITEMRENTAB = ITEMRENTAB;
	}


	public Integer getItemvenda(){
		return ITEMVENDA;
	}


	public void setItemvenda(Integer ITEMVENDA){
		this.ITEMVENDA = ITEMVENDA;
	}


	public Integer getItemqtde(){
		return ITEMQTDE;
	}


	public void setItemqtde(Integer ITEMQTDE){
		this.ITEMQTDE = ITEMQTDE;
	}


	public Integer getDiasvalped(){
		return DIASVALPED;
	}


	public void setDiasvalped(Integer DIASVALPED){
		this.DIASVALPED = DIASVALPED;
	}


	public Integer getDiasprevent(){
		return DIASPREVENT;
	}


	public void setDiasprevent(Integer DIASPREVENT){
		this.DIASPREVENT = DIASPREVENT;
	}


	public String getUsamixpad(){
		return USAMIXPAD;
	}


	public void setUsamixpad(String USAMIXPAD){
		this.USAMIXPAD = USAMIXPAD;
	}


	public String getTabprecodisp(){
		return TABPRECODISP;
	}


	public void setTabprecodisp(String TABPRECODISP){
		this.TABPRECODISP = TABPRECODISP;
	}


	public String getValcnpjf(){
		return VALCNPJF;
	}


	public void setValcnpjf(String VALCNPJF){
		this.VALCNPJF = VALCNPJF;
	}


	public Integer getLimcredpad(){
		return LIMCREDPAD;
	}


	public void setLimcredpad(Integer LIMCREDPAD){
		this.LIMCREDPAD = LIMCREDPAD;
	}


	public Integer getLimcredpadmen(){
		return LIMCREDPADMEN;
	}


	public void setLimcredpadmen(Integer LIMCREDPADMEN){
		this.LIMCREDPADMEN = LIMCREDPADMEN;
	}


	public Integer getSistcodbar(){
		return SISTCODBAR;
	}


	public void setSistcodbar(Integer SISTCODBAR){
		this.SISTCODBAR = SISTCODBAR;
	}


	public String getCodpais(){
		return CODPAIS;
	}


	public void setCodpais(String CODPAIS){
		this.CODPAIS = CODPAIS;
	}


	public String getCodempresa(){
		return CODEMPRESA;
	}


	public void setCodempresa(String CODEMPRESA){
		this.CODEMPRESA = CODEMPRESA;
	}


	public Integer getIdbancusto(){
		return IDBANCUSTO;
	}


	public void setIdbancusto(Integer IDBANCUSTO){
		this.IDBANCUSTO = IDBANCUSTO;
	}


	public Integer getIdbanpreco(){
		return IDBANPRECO;
	}


	public void setIdbanpreco(Integer IDBANPRECO){
		this.IDBANPRECO = IDBANPRECO;
	}


	public Integer getIdbanitepess(){
		return IDBANITEPESS;
	}


	public void setIdbanitepess(Integer IDBANITEPESS){
		this.IDBANITEPESS = IDBANITEPESS;
	}


	public String getSerienfe(){
		return SERIENFE;
	}


	public void setSerienfe(String SERIENFE){
		this.SERIENFE = SERIENFE;
	}


	public Integer getPercrendalimmes(){
		return PERCRENDALIMMES;
	}


	public void setPercrendalimmes(Integer PERCRENDALIMMES){
		this.PERCRENDALIMMES = PERCRENDALIMMES;
	}


	public Integer getPercrendalimtot(){
		return PERCRENDALIMTOT;
	}


	public void setPercrendalimtot(Integer PERCRENDALIMTOT){
		this.PERCRENDALIMTOT = PERCRENDALIMTOT;
	}


	public Integer getDiasreavpess(){
		return DIASREAVPESS;
	}


	public void setDiasreavpess(Integer DIASREAVPESS){
		this.DIASREAVPESS = DIASREAVPESS;
	}


	public Integer getDiasbloqultvda(){
		return DIASBLOQULTVDA;
	}


	public void setDiasbloqultvda(Integer DIASBLOQULTVDA){
		this.DIASBLOQULTVDA = DIASBLOQULTVDA;
	}


	public Integer getCarenciasaldodev(){
		return CARENCIASALDODEV;
	}


	public void setCarenciasaldodev(Integer CARENCIASALDODEV){
		this.CARENCIASALDODEV = CARENCIASALDODEV;
	}


	public String getUsacusto(){
		return USACUSTO;
	}


	public void setUsacusto(String USACUSTO){
		this.USACUSTO = USACUSTO;
	}


	public String getIditemfrete(){
		return IDITEMFRETE;
	}


	public void setIditemfrete(String IDITEMFRETE){
		this.IDITEMFRETE = IDITEMFRETE;
	}


	public Integer getLimctpesspad(){
		return LIMCTPESSPAD;
	}


	public void setLimctpesspad(Integer LIMCTPESSPAD){
		this.LIMCTPESSPAD = LIMCTPESSPAD;
	}


	public Integer getIdbanitemest(){
		return IDBANITEMEST;
	}


	public void setIdbanitemest(Integer IDBANITEMEST){
		this.IDBANITEMEST = IDBANITEMEST;
	}


	public String getClassmerc(){
		return CLASSMERC;
	}


	public void setClassmerc(String CLASSMERC){
		this.CLASSMERC = CLASSMERC;
	}


	public String getAltlocret(){
		return ALTLOCRET;
	}


	public void setAltlocret(String ALTLOCRET){
		this.ALTLOCRET = ALTLOCRET;
	}


	public Integer getPessoamodelo(){
		return PESSOAMODELO;
	}


	public void setPessoamodelo(Integer PESSOAMODELO){
		this.PESSOAMODELO = PESSOAMODELO;
	}


	public String getValcadend(){
		return VALCADEND;
	}


	public void setValcadend(String VALCADEND){
		this.VALCADEND = VALCADEND;
	}


	public Integer getLimrendafam(){
		return LIMRENDAFAM;
	}


	public void setLimrendafam(Integer LIMRENDAFAM){
		this.LIMRENDAFAM = LIMRENDAFAM;
	}


	public Integer getIdconceitobloq(){
		return IDCONCEITOBLOQ;
	}


	public void setIdconceitobloq(Integer IDCONCEITOBLOQ){
		this.IDCONCEITOBLOQ = IDCONCEITOBLOQ;
	}


	public String getValregional(){
		return VALREGIONAL;
	}


	public void setValregional(String VALREGIONAL){
		this.VALREGIONAL = VALREGIONAL;
	}


	public Integer getItemmodelo(){
		return ITEMMODELO;
	}


	public void setItemmodelo(Integer ITEMMODELO){
		this.ITEMMODELO = ITEMMODELO;
	}


	public String getInativasaida(){
		return INATIVASAIDA;
	}


	public void setInativasaida(String INATIVASAIDA){
		this.INATIVASAIDA = INATIVASAIDA;
	}


	public Integer getIditemdespesa(){
		return IDITEMDESPESA;
	}


	public void setIditemdespesa(Integer IDITEMDESPESA){
		this.IDITEMDESPESA = IDITEMDESPESA;
	}


	public Integer getIdbantrib(){
		return IDBANTRIB;
	}


	public void setIdbantrib(Integer IDBANTRIB){
		this.IDBANTRIB = IDBANTRIB;
	}


	public Integer getIdbanemb(){
		return IDBANEMB;
	}


	public void setIdbanemb(Integer IDBANEMB){
		this.IDBANEMB = IDBANEMB;
	}


	public String getValean(){
		return VALEAN;
	}


	public void setValean(String VALEAN){
		this.VALEAN = VALEAN;
	}

}