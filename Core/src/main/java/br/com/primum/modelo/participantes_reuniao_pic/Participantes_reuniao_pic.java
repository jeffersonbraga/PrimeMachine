package br.com.primum.modelo.participantes_reuniao_pic;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.modelo.reuniao_pic.Pre_avaliacao_ideia_pic;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="participantes_reuniao_pic")
public class Participantes_reuniao_pic {

	@Id
	@Column
	private Integer cd_participantes_reuniao_pic;
	
	@Column
	private Integer cd_reuniao_pic;
	
	@Column
	private Boolean tp_presenca;
	
	@Column
	private Integer tp_confirmacao_presenca;

	@Column
	private Integer cd_pessoa;

	@Transient
	List<Pre_avaliacao_ideia_pic> lista_PreAvaliacoes;
	
	
	public Integer getTp_confirmacao_presenca() {
		return tp_confirmacao_presenca;
	}

	public void setTp_confirmacao_presenca(Integer tp_confirmacao_presenca) {
		this.tp_confirmacao_presenca = tp_confirmacao_presenca;
	}

	public List<Pre_avaliacao_ideia_pic> getLista_PreAvaliacoes() {
		return lista_PreAvaliacoes;
	}

	public void setLista_PreAvaliacoes(
			List<Pre_avaliacao_ideia_pic> lista_PreAvaliacoes) {
		this.lista_PreAvaliacoes = lista_PreAvaliacoes;
	}

	public Integer getCd_participantes_reuniao_pic() {
		return cd_participantes_reuniao_pic;
	}

	public void setCd_participantes_reuniao_pic(Integer cd_participantes_reuniao_pic) {
		this.cd_participantes_reuniao_pic = cd_participantes_reuniao_pic;
	}

	public Integer getCd_reuniao_pic() {
		return cd_reuniao_pic;
	}

	public void setCd_reuniao_pic(Integer cd_reuniao_pic) {
		this.cd_reuniao_pic = cd_reuniao_pic;
	}

	public Boolean getTp_presenca() {
		return tp_presenca;
	}

	public void setTp_presenca(Boolean tp_presenca) {
		this.tp_presenca = tp_presenca;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

}
