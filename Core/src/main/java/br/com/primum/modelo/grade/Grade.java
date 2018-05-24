package br.com.primum.modelo.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grade")
public class Grade {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_grade;
	@Column
	private String ds_grade;
	
	public Integer getCd_grade() {
		return cd_grade;
	}
	public void setCd_grade(Integer cd_grade) {
		this.cd_grade = cd_grade;
	}
	public String getDs_grade() {
		return ds_grade;
	}
	public void setDs_grade(String ds_grade) {
		this.ds_grade = ds_grade;
	}
}
