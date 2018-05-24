package br.com.primum.modelo.viasoft.FILIALCONFPRINT;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="FILIALCONFPRINT")
public class Filialconfprint  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer ESTAB;

	@Column
	private String DOCTO;

	@Column
	private String RELATORIO;

	@Column
	private String PRINTER;

	@Column
	private String TELAIMP;

	@Column
	private String VIEWIMP;

	@Column
	private String ENVEMAIL;


	public Integer getEstab(){
		return ESTAB;
	}


	public void setEstab(Integer ESTAB){
		this.ESTAB = ESTAB;
	}


	public String getDocto(){
		return DOCTO;
	}


	public void setDocto(String DOCTO){
		this.DOCTO = DOCTO;
	}


	public String getRelatorio(){
		return RELATORIO;
	}


	public void setRelatorio(String RELATORIO){
		this.RELATORIO = RELATORIO;
	}


	public String getPrinter(){
		return PRINTER;
	}


	public void setPrinter(String PRINTER){
		this.PRINTER = PRINTER;
	}


	public String getTelaimp(){
		return TELAIMP;
	}


	public void setTelaimp(String TELAIMP){
		this.TELAIMP = TELAIMP;
	}


	public String getViewimp(){
		return VIEWIMP;
	}


	public void setViewimp(String VIEWIMP){
		this.VIEWIMP = VIEWIMP;
	}


	public String getEnvemail(){
		return ENVEMAIL;
	}


	public void setEnvemail(String ENVEMAIL){
		this.ENVEMAIL = ENVEMAIL;
	}

}