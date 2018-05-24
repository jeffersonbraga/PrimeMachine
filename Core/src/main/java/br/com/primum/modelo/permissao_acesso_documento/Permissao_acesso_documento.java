package br.com.primum.modelo.permissao_acesso_documento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="permissao_acesso_documento")
public class Permissao_acesso_documento {

	@Id
	@Column
	private Integer cd_permissao_acesso_documento;

	@Column
	private Integer cd_documento;

	@Column
	private Integer cd_colaborador;

	@Column
	private Integer vl_download_cc;
	
	@Column
	private Integer vl_download_cnc;
	
	@Column
	private Integer vl_impressao_cc;

	@Column
	private Integer vl_impressao_cnc;

	@Column
	private Integer vl_visualizar_cc;

	@Column
	private Integer vl_visualizar_cnc;

	
	public Integer getVl_download_cc() {
		return vl_download_cc;
	}

	public void setVl_download_cc(Integer vl_download_cc) {
		this.vl_download_cc = vl_download_cc;
	}

	public Integer getVl_download_cnc() {
		return vl_download_cnc;
	}

	public void setVl_download_cnc(Integer vl_download_cnc) {
		this.vl_download_cnc = vl_download_cnc;
	}

	public Integer getCd_permissao_acesso_documento() {
		return cd_permissao_acesso_documento;
	}

	public void setCd_permissao_acesso_documento(
			Integer cd_permissao_acesso_documento) {
		this.cd_permissao_acesso_documento = cd_permissao_acesso_documento;
	}

	public Integer getCd_documento() {
		return cd_documento;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public Integer getCd_colaborador() {
		return cd_colaborador;
	}

	public void setCd_colaborador(Integer cd_colaborador) {
		this.cd_colaborador = cd_colaborador;
	}

	public Integer getVl_impressao_cc() {
		return vl_impressao_cc;
	}

	public void setVl_impressao_cc(Integer vl_impressao_cc) {
		this.vl_impressao_cc = vl_impressao_cc;
	}

	public Integer getVl_impressao_cnc() {
		return vl_impressao_cnc;
	}

	public void setVl_impressao_cnc(Integer vl_impressao_cnc) {
		this.vl_impressao_cnc = vl_impressao_cnc;
	}

	public Integer getVl_visualizar_cc() {
		return vl_visualizar_cc;
	}

	public void setVl_visualizar_cc(Integer vl_visualizar_cc) {
		this.vl_visualizar_cc = vl_visualizar_cc;
	}

	public Integer getVl_visualizar_cnc() {
		return vl_visualizar_cnc;
	}

	public void setVl_visualizar_cnc(Integer vl_visualizar_cnc) {
		this.vl_visualizar_cnc = vl_visualizar_cnc;
	}
}
