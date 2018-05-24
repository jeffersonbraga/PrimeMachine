package br.com.primum.modelo.espelho;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="espelho")
public class Espelho {

	@Id
	private Integer cd_espelho;
	
	@Column
	private byte[][] ds_bytedata_espelho;

	public Integer getCd_espelho() {
		return cd_espelho;
	}

	public void setCd_espelho(Integer cd_espelho) {
		this.cd_espelho = cd_espelho;
	}

	public byte[][] getDs_bytedata_espelho() {
		return ds_bytedata_espelho;
	}

	public void setDs_bytedata_espelho(byte[][] ds_bytedata_espelho) {
		this.ds_bytedata_espelho = ds_bytedata_espelho;
	}
}
