package br.com.lsd.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class MapsData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal idpess;

	private BigDecimal valor;

	private String endereco;

	private Object resultado;

	private Object idend;

	private BigDecimal latitude;

	private BigDecimal longitude;

	public BigDecimal getIdpess() {
		return idpess;
	}

	public void setIdpess(BigDecimal idpess) {
		this.idpess = idpess;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

	public Object getIdend() {
		return idend;
	}

	public void setIdend(Object idend) {
		this.idend = idend;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}