package br.com.primum.modelo.jhi_user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_user")
public class Jhi_user  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Integer id;

	@Column
	private String login;

	@Column
	private String password_hash;

	@Column
	private String first_name;

	@Column
	private String last_name;

	@Column
	private String email;

	@Column
	private String lang_key;

	@Column
	private String activation_key;

	@Column
	private String reset_key;

	@Column
	private String created_by;

	@Column
	private String last_modified_by;


	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id = id;
	}


	public String getLogin(){
		return login;
	}


	public void setLogin(String login){
		this.login = login;
	}


	public String getPassword_hash(){
		return password_hash;
	}


	public void setPassword_hash(String password_hash){
		this.password_hash = password_hash;
	}


	public String getFirst_name(){
		return first_name;
	}


	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}


	public String getLast_name(){
		return last_name;
	}


	public void setLast_name(String last_name){
		this.last_name = last_name;
	}


	public String getEmail(){
		return email;
	}


	public void setEmail(String email){
		this.email = email;
	}


	public String getLang_key(){
		return lang_key;
	}


	public void setLang_key(String lang_key){
		this.lang_key = lang_key;
	}


	public String getActivation_key(){
		return activation_key;
	}


	public void setActivation_key(String activation_key){
		this.activation_key = activation_key;
	}


	public String getReset_key(){
		return reset_key;
	}


	public void setReset_key(String reset_key){
		this.reset_key = reset_key;
	}


	public String getCreated_by(){
		return created_by;
	}


	public void setCreated_by(String created_by){
		this.created_by = created_by;
	}


	public String getLast_modified_by(){
		return last_modified_by;
	}


	public void setLast_modified_by(String last_modified_by){
		this.last_modified_by = last_modified_by;
	}

}