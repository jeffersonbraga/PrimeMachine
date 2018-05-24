package br.com.primum.service.plano_acao;

import java.util.Date;
import java.util.List;

import br.com.primum.bo.plano_acao.Plano_acao_BO;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.plano_acao_hp.Plano_acao_hp;

public class Plano_acao_Service {

	private Plano_acao_BO planoBO;

	public Plano_acao_BO getPlanoBO() {
		return planoBO;
	}

	public void setPlanoBO(Plano_acao_BO planoBO) {
		this.planoBO = planoBO;
	}

	public List<Plano_acao> buscarPlano_acao(Integer cd_usuario, Integer st_plano_acao) throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().buscarPlano_acao(cd_usuario, st_plano_acao);
	}

	public String getGrupoECampoNC( Integer cd_plano_acao ) throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().getGrupoECampoNC( cd_plano_acao );
	}
	
	
	public Plano_acao_hp buscarPlano_acaoHDPelaAcao(Integer cd_acao_plano_acao_hp) throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().buscarPlano_acaoHDPelaAcao(cd_acao_plano_acao_hp);
	}

	public Plano_acao_hp buscarPlano_acaoHD(Integer cd_plano_acao_hp) throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().buscarPlano_acaoHD(cd_plano_acao_hp);
	}

	public List<?> buscarPlano_acaoFeed() throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().buscarPlano_acaoFeed();
	}

	public Boolean salvaNotificacao(Integer cd_plano_acao) throws Exception {
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().salvaNotificacao(cd_plano_acao);
	}

//	public Boolean atualizaNotificacao(Integer cd_plano_acao) throws Exception {
//		this.setPlanoBO(new Plano_acao_BO());
//		return this.getPlanoBO().atualizaNotificacao(cd_plano_acao);
//	}

	public Integer qtdeFeeds(){
		this.setPlanoBO(new Plano_acao_BO());
		return this.getPlanoBO().qtdeFeeds();
	}
	
	public Boolean agendarEnvio( Plano_acao_hp plano_acao_hp, String jobName, Integer vl_tipo_notificacao ){
		this.setPlanoBO(new Plano_acao_BO());
		return getPlanoBO().agendarEnvio( plano_acao_hp, jobName, vl_tipo_notificacao);
	 }
	
	public Boolean agendarEnvioAcao(  Acao_plano_acao_hp acao, String jobName , Integer vl_tipo_notificacao, Date dt_notificacao ){
		this.setPlanoBO(new Plano_acao_BO());
		return getPlanoBO().agendarEnvioAcao( acao, jobName, vl_tipo_notificacao, dt_notificacao);
	 }
}