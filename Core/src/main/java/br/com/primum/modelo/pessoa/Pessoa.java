package br.com.primum.modelo.pessoa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.fiscal_sif.Fiscal_sif;
import br.com.primum.modelo.usuario.Usuario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pessoa")
public class Pessoa {

	@Id
	private Integer cd_pessoa;

	@Column
	private String nm_pessoa;

	@Column
	private String nr_registro;

	@Column
	private String nr_cpf;

	////@Temporal(value=TemporalType.DATE)
	//@Temporal(value = TemporalType.DATE)
	private Date dt_cadastro;

	////@Temporal(value=TemporalType.DATE)
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_nascimento;

	@Column
	private String ds_email;

	@Column
	private Integer st_pessoa;

	@Column
	private Integer tp_sexo;

	@Column
	private String nr_telefone;

	@Column
	private Integer tp_situacao;

//	@Transient
//	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.fiscal_sif.Fiscal_sif", campoBuscar="cd_pessoa", campoComparar="cd_pessoa", notificador=false, persistir=true)
//	private Fiscal_sif fiscal_sif;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.usuario.Usuario", campoBuscar="cd_pessoa", campoComparar="cd_pessoa", notificador=false, persistir=true)
	private Usuario usuario;

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public String getNm_pessoa() {
		return nm_pessoa;
	}

	public void setNm_pessoa(String nm_pessoa) {
		this.nm_pessoa = nm_pessoa;
	}

	public String getNr_registro() {
		return nr_registro;
	}

	public void setNr_registro(String nr_registro) {
		this.nr_registro = nr_registro;
	}

	public String getNr_cpf() {
		return nr_cpf;
	}

	public void setNr_cpf(String nr_cpf) {
		this.nr_cpf = nr_cpf;
	}

	public Date getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public Date getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}

	public String getDs_email() {
		return ds_email;
	}

	public void setDs_email(String ds_email) {
		this.ds_email = ds_email;
	}

	public Integer getSt_pessoa() {
		return st_pessoa;
	}

	public void setSt_pessoa(Integer st_pessoa) {
		this.st_pessoa = st_pessoa;
	}

	public Integer getTp_sexo() {
		return tp_sexo;
	}

	public void setTp_sexo(Integer tp_sexo) {
		this.tp_sexo = tp_sexo;
	}

	public String getNr_telefone() {
		return nr_telefone;
	}

	public void setNr_telefone(String nr_telefone) {
		this.nr_telefone = nr_telefone;
	}

//	public Fiscal_sif getFiscal_sif() {
//		return fiscal_sif;
//	}
//
//	public void setFiscal_sif(Fiscal_sif fiscal_sif) {
//		this.fiscal_sif = fiscal_sif;
//	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getTp_situacao() {
		return tp_situacao;
	}

	public void setTp_situacao(Integer tp_situacao) {
		this.tp_situacao = tp_situacao;
	}
}