package br.com.primum.modelo.configuracao_ouvidoria;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="configuracao_ouvidoria")
public class Configuracao_ouvidoria {

	@Id
	@Column
	private Integer cd_configuracao_ouvidoria;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_classificacao_ouvidoria;
	

	public Integer getCd_configuracao_ouvidoria() {
		return cd_configuracao_ouvidoria;
	}

	public void setCd_configuracao_ouvidoria(Integer cd_configuracao_ouvidoria) {
		this.cd_configuracao_ouvidoria = cd_configuracao_ouvidoria;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_classificacao_ouvidoria() {
		return cd_classificacao_ouvidoria;
	}

	public void setCd_classificacao_ouvidoria(Integer cd_classificacao_ouvidoria) {
		this.cd_classificacao_ouvidoria = cd_classificacao_ouvidoria;
	}

}
