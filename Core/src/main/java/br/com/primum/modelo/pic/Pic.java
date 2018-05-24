package br.com.primum.modelo.pic;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.documento_pic.Documento_pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pic")
public class Pic {

	@Id
	@Column
	private Integer cd_pic;
	
	@Column
	private Integer cd_colaborador;
	
	@Column
	private String nm_usuario;
	
	@Column
	private String ds_email;
	
	@Column
	private String nr_fone;
	
	@Column
	private Integer tp_area_aplicacao;
	
	@Column
	private String ds_ideia;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_ideia;
	
	@Column
	private String ds_detalhamento;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.documento_pic.Documento_pic", campoBuscar="cd_pic", persistir=true)
	List<Documento_pic> listaDocumentos;

	
	
	public Integer getCd_pic() {
		return cd_pic;
	}

	public void setCd_pic(Integer cd_pic) {
		this.cd_pic = cd_pic;
	}

	public Integer getCd_colaborador() {
		return cd_colaborador;
	}

	public void setCd_colaborador(Integer cd_colaborador) {
		this.cd_colaborador = cd_colaborador;
	}

	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public String getDs_email() {
		return ds_email;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public String getNr_fone() {
		return nr_fone;
	}

	public void setNr_fone(String nr_fone) {
		this.nr_fone = nr_fone;
	}

	public Integer getTp_area_aplicacao() {
		return tp_area_aplicacao;
	}

	public void setTp_area_aplicacao(Integer tp_area_aplicacao) {
		this.tp_area_aplicacao = tp_area_aplicacao;
	}

	public String getDs_ideia() {
		return ds_ideia;
	}

	public void setDs_ideia(String ds_ideia) {
		this.ds_ideia = ds_ideia;
	}

	public String getDs_detalhamento() {
		return ds_detalhamento;
	}

	public void setDs_detalhamento(String ds_detalhamento) {
		this.ds_detalhamento = ds_detalhamento;
	}

	public List<Documento_pic> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento_pic> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public Date getDt_ideia() {
		return dt_ideia;
	}

	public void setDt_ideia(Date dt_ideia) {
		this.dt_ideia = dt_ideia;
	}

}
