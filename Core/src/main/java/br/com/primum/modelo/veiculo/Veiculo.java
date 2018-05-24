package br.com.primum.modelo.veiculo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="veiculo")
public class Veiculo  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer cd_veiculo;

	@Column
	private Integer cd_pessoa;

	@Column
	private String nm_veiculo;

	@Column
	private String ds_veiculo;

	@Column
	private String nr_frota;

	@Column
	private String nr_placa_veiculo;

	@Column
	private Integer nr_ano_veiculo;

	@Column
	private String nr_placa_reboque;

	@Column
	private String ds_reboque;

	@Column
	private Integer nr_ano_reboque;

	@Column
	private String ds_complementar;

	@Column
	private Integer st_veiculo;


	public Integer getCd_veiculo(){
		return cd_veiculo;
	}


	public void setCd_veiculo(Integer cd_veiculo){
		this.cd_veiculo = cd_veiculo;
	}


	public Integer getCd_pessoa(){
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa){
		this.cd_pessoa = cd_pessoa;
	}


	public String getNm_veiculo(){
		return nm_veiculo;
	}


	public void setNm_veiculo(String nm_veiculo){
		this.nm_veiculo = nm_veiculo;
	}


	public String getDs_veiculo(){
		return ds_veiculo;
	}


	public void setDs_veiculo(String ds_veiculo){
		this.ds_veiculo = ds_veiculo;
	}


	public String getNr_frota(){
		return nr_frota;
	}


	public void setNr_frota(String nr_frota){
		this.nr_frota = nr_frota;
	}


	public String getNr_placa_veiculo(){
		return nr_placa_veiculo;
	}


	public void setNr_placa_veiculo(String nr_placa_veiculo){
		this.nr_placa_veiculo = nr_placa_veiculo;
	}


	public Integer getNr_ano_veiculo(){
		return nr_ano_veiculo;
	}


	public void setNr_ano_veiculo(Integer nr_ano_veiculo){
		this.nr_ano_veiculo = nr_ano_veiculo;
	}


	public String getNr_placa_reboque(){
		return nr_placa_reboque;
	}


	public void setNr_placa_reboque(String nr_placa_reboque){
		this.nr_placa_reboque = nr_placa_reboque;
	}


	public String getDs_reboque(){
		return ds_reboque;
	}


	public void setDs_reboque(String ds_reboque){
		this.ds_reboque = ds_reboque;
	}


	public Integer getNr_ano_reboque(){
		return nr_ano_reboque;
	}


	public void setNr_ano_reboque(Integer nr_ano_reboque){
		this.nr_ano_reboque = nr_ano_reboque;
	}


	public String getDs_complementar(){
		return ds_complementar;
	}


	public void setDs_complementar(String ds_complementar){
		this.ds_complementar = ds_complementar;
	}


	public Integer getSt_veiculo(){
		return st_veiculo;
	}


	public void setSt_veiculo(Integer st_veiculo){
		this.st_veiculo = st_veiculo;
	}

}