package br.com.primum.modelo.avaliacao_ideia;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.pergunta_avaliacao_ideia.Pergunta_avaliacao_ideia;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="avaliacao_ideia")
public class Avaliacao_ideia {
	
	@Id
	@Column
	private Integer cd_avaliacao_ideia;
	
	@Column
	private Integer cd_ideia_reuniao_pic;
	
	@Column
	private Integer cd_reuniao_pic;
	
	@Column
	private Integer nr_pontuacao_media;
	
	@Column
	private Integer st_parecer;
	
	@Column
	private String ds_comentario;
	
	@Column
	private String ds_expectativa;
	
	@Column
	private String ds_critica_melhoria;
	
	@Column
	private String ds_justificativa;
	
	@Column
	private Integer st_aprovado;
	
	@Column
	private Integer cd_resp_ava_tecnica;
	
	@Column
	private Integer cd_resp_ava_financeira;
	
	
	@Transient
	@ListaBusca( caminhoClasseJava ="br.com.primum.modelo.pergunta_avaliacao_ideia.Pergunta_avaliacao_ideia", campoBuscar="cd_avaliacao_ideia", persistir=true)
	private List<Pergunta_avaliacao_ideia> listaPerguntas;


	public Integer getCd_avaliacao_ideia() {
		return cd_avaliacao_ideia;
	}


	public void setCd_avaliacao_ideia(Integer cd_avaliacao_ideia) {
		this.cd_avaliacao_ideia = cd_avaliacao_ideia;
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


	public Integer getNr_pontuacao_media() {
		return nr_pontuacao_media;
	}


	public void setNr_pontuacao_media(Integer nr_pontuacao_media) {
		this.nr_pontuacao_media = nr_pontuacao_media;
	}


	public Integer getSt_parecer() {
		return st_parecer;
	}


	public void setSt_parecer(Integer st_parecer) {
		this.st_parecer = st_parecer;
	}


	public String getDs_comentario() {
		return ds_comentario;
	}


	public void setDs_comentario(String ds_comentario) {
		this.ds_comentario = ds_comentario;
	}


	public String getDs_expectativa() {
		return ds_expectativa;
	}


	public void setDs_expectativa(String ds_expectativa) {
		this.ds_expectativa = ds_expectativa;
	}


	public String getDs_critica_melhoria() {
		return ds_critica_melhoria;
	}


	public void setDs_critica_melhoria(String ds_critica_melhoria) {
		this.ds_critica_melhoria = ds_critica_melhoria;
	}


	public String getDs_justificativa() {
		return ds_justificativa;
	}


	public void setDs_justificativa(String ds_justificativa) {
		this.ds_justificativa = ds_justificativa;
	}


	public Integer getSt_aprovado() {
		return st_aprovado;
	}


	public void setSt_aprovado(Integer st_aprovado) {
		this.st_aprovado = st_aprovado;
	}


	public Integer getCd_resp_ava_tecnica() {
		return cd_resp_ava_tecnica;
	}


	public void setCd_resp_ava_tecnica(Integer cd_resp_ava_tecnica) {
		this.cd_resp_ava_tecnica = cd_resp_ava_tecnica;
	}


	public Integer getCd_resp_ava_financeira() {
		return cd_resp_ava_financeira;
	}


	public void setCd_resp_ava_financeira(Integer cd_resp_ava_financeira) {
		this.cd_resp_ava_financeira = cd_resp_ava_financeira;
	}


	public List<Pergunta_avaliacao_ideia> getListaPerguntas() {
		return listaPerguntas;
	}


	public void setListaPerguntas(List<Pergunta_avaliacao_ideia> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

}
