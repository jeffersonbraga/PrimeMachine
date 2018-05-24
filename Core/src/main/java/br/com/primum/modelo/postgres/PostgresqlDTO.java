package br.com.primum.modelo.postgres;

public class PostgresqlDTO {
	
	public String nm_coluna;
	
	public String tp_campo;
	
	public String nr_indice_coluna;

	public String getNm_coluna() {
		return nm_coluna;
	}

	public void setNm_coluna(String nm_coluna) {
		this.nm_coluna = nm_coluna;
	}

	public String getTp_campo() {
		return tp_campo;
	}

	public void setTp_campo(String tp_campo) {
		this.tp_campo = tp_campo;
	}

	public String getNr_indice_coluna() {
		return nr_indice_coluna;
	}

	public void setNr_indice_coluna(Integer nr_indice_coluna) {
		this.nr_indice_coluna = nr_indice_coluna.toString();
	}

	public void setNr_indice_coluna(String nr_indice_coluna) {
		this.nr_indice_coluna = nr_indice_coluna;
	}
}
