package br.com.primum.modelo.jhi_persistent_token;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_persistent_token")
public class Jhi_persistent_token  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String series;

	@Column
	private Integer user_id;

	@Column
	private String token_value;

	@Column
	private Date token_date;

	@Column
	private String ip_address;

	@Column
	private String user_agent;


	public String getSeries(){
		return series;
	}


	public void setSeries(String series){
		this.series = series;
	}


	public Integer getUser_id(){
		return user_id;
	}


	public void setUser_id(Integer user_id){
		this.user_id = user_id;
	}


	public String getToken_value(){
		return token_value;
	}


	public void setToken_value(String token_value){
		this.token_value = token_value;
	}


	public Date getToken_date(){
		return token_date;
	}


	public void setToken_date(Date token_date){
		this.token_date = token_date;
	}


	public String getIp_address(){
		return ip_address;
	}


	public void setIp_address(String ip_address){
		this.ip_address = ip_address;
	}


	public String getUser_agent(){
		return user_agent;
	}


	public void setUser_agent(String user_agent){
		this.user_agent = user_agent;
	}

}