package br.com.primum.modelo.dbconf;

public class Dbconf {

	private String dialetoconexao;

	private String driverconexao;

	private String tipobancodados;

	private String caminhoconexao;

	private String usuarioconexao;

	private String senhaconexao;
	
	private String urlservidor;
	
	
	public String getUrlservidor() {
		return urlservidor;
	}

	public void setUrlservidor(String urlservidor) {
		this.urlservidor = urlservidor;
	}

	public String getDialetoconexao() {
		return dialetoconexao;
	}

	public void setDialetoconexao(String dialetoconexao) {
		this.dialetoconexao = dialetoconexao;
	}

	public String getDriverconexao() {
		return driverconexao;
	}

	public void setDriverconexao(String driverconexao) {
		this.driverconexao = driverconexao;
	}

	public String getTipobancodados() {
		return tipobancodados;
	}

	public void setTipobancodados(String tipobancodados) {
		this.tipobancodados = tipobancodados;
	}

	public String getCaminhoconexao() {
		return caminhoconexao;
	}

	public void setCaminhoconexao(String caminhoconexao) {
		this.caminhoconexao = caminhoconexao;
	}

	public String getUsuarioconexao() {
		return usuarioconexao;
	}

	public void setUsuarioconexao(String usuarioconexao) {
		this.usuarioconexao = usuarioconexao;
	}

	public String getSenhaconexao() {
		return senhaconexao;
	}

	public void setSenhaconexao(String senhaconexao) {
		this.senhaconexao = senhaconexao;
	}
}