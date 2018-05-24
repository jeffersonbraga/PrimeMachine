package br.com.primum.modelo.pre_avaliacao_ideia_pic;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="pre_avaliacao_ideia_pic")
public class Pre_avaliacao_ideia_pic  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private Integer cd_pre_avaliacao_ideia_pic;

	@Column
	private Integer cd_ideia_reuniao_pic;

	@Column
	private Integer tp_avaliacao;

	@Column
	private Integer cd_participantes_reuniao_pic;


	public Integer getCd_pre_avaliacao_ideia_pic(){
		return cd_pre_avaliacao_ideia_pic;
	}


	public void setCd_pre_avaliacao_ideia_pic(Integer cd_pre_avaliacao_ideia_pic){
		this.cd_pre_avaliacao_ideia_pic = cd_pre_avaliacao_ideia_pic;
	}


	public Integer getCd_ideia_reuniao_pic(){
		return cd_ideia_reuniao_pic;
	}


	public void setCd_ideia_reuniao_pic(Integer cd_ideia_reuniao_pic){
		this.cd_ideia_reuniao_pic = cd_ideia_reuniao_pic;
	}


	public Integer getTp_avaliacao(){
		return tp_avaliacao;
	}


	public void setTp_avaliacao(Integer tp_avaliacao){
		this.tp_avaliacao = tp_avaliacao;
	}


	public Integer getCd_participantes_reuniao_pic(){
		return cd_participantes_reuniao_pic;
	}


	public void setCd_participantes_reuniao_pic(Integer cd_participantes_reuniao_pic){
		this.cd_participantes_reuniao_pic = cd_participantes_reuniao_pic;
	}

}