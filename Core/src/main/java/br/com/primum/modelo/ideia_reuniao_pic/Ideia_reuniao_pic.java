package br.com.primum.modelo.ideia_reuniao_pic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.pic.Pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="ideia_reuniao_pic")
public class Ideia_reuniao_pic {

	@Id
	@Column
	private Integer cd_ideia_reuniao_pic;
	
	@Column
	private Integer cd_reuniao_pic;
	
	@Column
	private Integer cd_pic;
	
	@Column
	private Integer nr_gravidade;
	
	@Column
	private Integer nr_tendencia;
	
	@Column
	private Integer nr_urgencia;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.pic.Pic", campoBuscar="cd_pic", campoComparar="cd_pic", persistir=false)
	private Pic pic;


	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	public Integer getCd_ideia_reuniao_pic() {
		return cd_ideia_reuniao_pic;
	}

	public void setCd_ideia_reuniao_pic(Integer cd_ideia_reuniao_pic) {
		this.cd_ideia_reuniao_pic = cd_ideia_reuniao_pic;
	}

	public Integer getCd_reuniao_pic() {
		return cd_reuniao_pic;
	}

	public void setCd_reuniao_pic(Integer cd_reuniao_pic) {
		this.cd_reuniao_pic = cd_reuniao_pic;
	}

	public Integer getCd_pic() {
		return cd_pic;
	}

	public void setCd_pic(Integer cd_pic) {
		this.cd_pic = cd_pic;
	}

	public Integer getNr_gravidade() {
		return nr_gravidade;
	}

	public void setNr_gravidade(Integer nr_gravidade) {
		this.nr_gravidade = nr_gravidade;
	}

	public Integer getNr_tendencia() {
		return nr_tendencia;
	}

	public void setNr_tendencia(Integer nr_tendencia) {
		this.nr_tendencia = nr_tendencia;
	}

	public Integer getNr_urgencia() {
		return nr_urgencia;
	}

	public void setNr_urgencia(Integer nr_urgencia) {
		this.nr_urgencia = nr_urgencia;
	}

}
