package br.com.primum.modelo.reuniao_pic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pre_avaliacao_ideia_pic")
public class Pre_avaliacao_ideia_pic {

	@Id
	@Column
	private Integer cd_pre_avaliacao_ideia_pic;

	@Column
	private Integer cd_ideia_reuniao_pic;

	@Column
	private Integer tp_avaliacao;

	@Column 
	private Integer cd_participantes_reuniao_pic;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic", campoBuscar="cd_ideia_reuniao_pic", campoComparar="cd_ideia_reuniao_pic", persistir=false)
	private Ideia_reuniao_pic ideia_pic = new Ideia_reuniao_pic();

	
	
	public Integer getCd_participantes_reuniao_pic() {
		return cd_participantes_reuniao_pic;
	}

	public void setCd_participantes_reuniao_pic(Integer cd_participantes_reuniao_pic) {
		this.cd_participantes_reuniao_pic = cd_participantes_reuniao_pic;
	}

	public Integer getCd_pre_avaliacao_ideia_pic() {
		return cd_pre_avaliacao_ideia_pic;
	}

	public void setCd_pre_avaliacao_ideia_pic(Integer cd_pre_avaliacao_ideia_pic) {
		this.cd_pre_avaliacao_ideia_pic = cd_pre_avaliacao_ideia_pic;
	}

	public Integer getCd_ideia_reuniao_pic() {
		return cd_ideia_reuniao_pic;
	}

	public void setCd_ideia_reuniao_pic(Integer cd_ideia_reuniao_pic) {
		this.cd_ideia_reuniao_pic = cd_ideia_reuniao_pic;
	}

	public Integer getTp_avaliacao() {
		return tp_avaliacao;
	}

	public void setTp_avaliacao(Integer tp_avaliacao) {
		this.tp_avaliacao = tp_avaliacao;
	}

	public Ideia_reuniao_pic getIdeia_pic() {
		return ideia_pic;
	}

	public void setIdeia_pic(Ideia_reuniao_pic ideia_pic) {
		this.ideia_pic = ideia_pic;
	}

}
