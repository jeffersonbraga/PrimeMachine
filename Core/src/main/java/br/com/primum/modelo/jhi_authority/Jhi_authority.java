package br.com.primum.modelo.jhi_authority;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="jhi_authority")
public class Jhi_authority  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String name;


	public String getName(){
		return name;
	}


	public void setName(String name){
		this.name = name;
	}

}