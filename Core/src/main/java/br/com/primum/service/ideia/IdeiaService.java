package br.com.primum.service.ideia;

import java.util.List;

import br.com.primum.bo.ideia.IdeiaBO;
import br.com.primum.modelo.avaliacao_ideia.Avaliacao_ideia;
import br.com.primum.modelo.ideia_reuniao_pic.Ideia_reuniao_pic;
import br.com.primum.modelo.pergunta_avaliacao_ideia.Pergunta_avaliacao_ideia;
import br.com.primum.modelo.reuniao_pic.Pre_avaliacao_ideia_pic;
import br.com.primum.modelo.reuniao_pic.Reuniao_pic;

public class IdeiaService {

	private IdeiaBO ideiaBO;

	public IdeiaBO getIdeiaBO() {
		return ideiaBO;
	}

	public void setIdeiaBO(IdeiaBO ideiaBO) {
		this.ideiaBO = ideiaBO;
	}
	
	
	public List<Pre_avaliacao_ideia_pic> buscaPreAvaliacoes(Reuniao_pic reuniao_pic, Integer cd_usuario_logado){
		this.setIdeiaBO(new IdeiaBO());
		return this.getIdeiaBO().buscaPreAvaliacoes( reuniao_pic, cd_usuario_logado );
	}
	
	
	public Avaliacao_ideia buscaPerguntas(Ideia_reuniao_pic ideia){
		this.setIdeiaBO(new IdeiaBO());
		return this.getIdeiaBO().buscaPerguntas( ideia );
	}
	
}
