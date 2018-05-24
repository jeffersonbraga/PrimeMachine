package br.com.primum.modelo.config_indicadores;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.config_envio_indicadores.Config_envio_indicadores;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="config_indicadores")
public class Config_indicadores{

	@Id
	@Column
	private Integer cd_config_indicadores;

	@Column
	private Integer vl_posicao_x;

	@Column
	private Integer vl_posicao_y;

	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_unidade;

	@Column
	private Integer cd_menu_indicadores;

	@Column
	private String ds_menu_indicadores;

	@Column
	private Integer cd_periodicidade;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.config_envio_indicadores.Config_envio_indicadores", campoBuscar="cd_config_indicadores", campoComparar="cd_config_indicadores", persistir=true, notificador=false)
	private Config_envio_indicadores config_envio_indicadores;

	
	public Integer getCd_unidade() {
		return cd_unidade;
	}


	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}


	public Integer getCd_config_indicadores(){
		return cd_config_indicadores;
	}


	public void setCd_config_indicadores(Integer cd_config_indicadores){
		this.cd_config_indicadores = cd_config_indicadores;
	}


	public Integer getVl_posicao_x(){
		return vl_posicao_x;
	}


	public void setVl_posicao_x(Integer vl_posicao_x){
		this.vl_posicao_x = vl_posicao_x;
	}


	public Integer getVl_posicao_y(){
		return vl_posicao_y;
	}


	public void setVl_posicao_y(Integer vl_posicao_y){
		this.vl_posicao_y = vl_posicao_y;
	}


	public Integer getCd_usuario(){
		return cd_usuario;
	}


	public void setCd_usuario(Integer cd_usuario){
		this.cd_usuario = cd_usuario;
	}


	public Integer getCd_menu_indicadores(){
		return cd_menu_indicadores;
	}


	public void setCd_menu_indicadores(Integer cd_menu_indicadores){
		this.cd_menu_indicadores = cd_menu_indicadores;
	}


	public String getDs_menu_indicadores(){
		return ds_menu_indicadores;
	}


	public void setDs_menu_indicadores(String ds_menu_indicadores){
		this.ds_menu_indicadores = ds_menu_indicadores;
	}


	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}


	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}


	public Date getDt_inicio() {
		return dt_inicio;
	}


	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}


	public Config_envio_indicadores getConfig_envio_indicadores() {
		return config_envio_indicadores;
	}


	public void setConfig_envio_indicadores(
			Config_envio_indicadores config_envio_indicadores) {
		this.config_envio_indicadores = config_envio_indicadores;
	}
	

}