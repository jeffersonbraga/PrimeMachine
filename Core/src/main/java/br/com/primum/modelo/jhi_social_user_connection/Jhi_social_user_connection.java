package br.com.primum.modelo.jhi_social_user_connection;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_social_user_connection")
public class Jhi_social_user_connection  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer id;

	@Column
	private String user_id;

	@Column
	private String provider_id;

	@Column
	private String provider_user_id;

	@Column
	private Integer rank;

	@Column
	private String display_name;

	@Column
	private String profile_url;

	@Column
	private String image_url;

	@Column
	private String access_token;

	@Column
	private String secret;

	@Column
	private String refresh_token;

	@Column
	private Integer expire_time;


	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public String getUser_id(){
		return user_id;
	}


	public void setUser_id(String user_id){
		this.user_id = user_id;
	}


	public String getProvider_id(){
		return provider_id;
	}


	public void setProvider_id(String provider_id){
		this.provider_id = provider_id;
	}


	public String getProvider_user_id(){
		return provider_user_id;
	}


	public void setProvider_user_id(String provider_user_id){
		this.provider_user_id = provider_user_id;
	}


	public Integer getRank(){
		return rank;
	}


	public void setRank(Integer rank){
		this.rank = rank;
	}


	public String getDisplay_name(){
		return display_name;
	}


	public void setDisplay_name(String display_name){
		this.display_name = display_name;
	}


	public String getProfile_url(){
		return profile_url;
	}


	public void setProfile_url(String profile_url){
		this.profile_url = profile_url;
	}


	public String getImage_url(){
		return image_url;
	}


	public void setImage_url(String image_url){
		this.image_url = image_url;
	}


	public String getAccess_token(){
		return access_token;
	}


	public void setAccess_token(String access_token){
		this.access_token = access_token;
	}


	public String getSecret(){
		return secret;
	}


	public void setSecret(String secret){
		this.secret = secret;
	}


	public String getRefresh_token(){
		return refresh_token;
	}


	public void setRefresh_token(String refresh_token){
		this.refresh_token = refresh_token;
	}


	public Integer getExpire_time(){
		return expire_time;
	}


	public void setExpire_time(Integer expire_time){
		this.expire_time = expire_time;
	}

}