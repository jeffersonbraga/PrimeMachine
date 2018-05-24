package br.com.primum.modelo.databasechangelog;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

import java.util.Date;


@Entity
@Table(name="databasechangelog")
public class Databasechangelog  implements Serializable {

	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//1L;
	@Id
	@Column
	private String id;

	@Column
	private String author;

	@Column
	private String filename;

	@Column
	private Date dateexecuted;

	@Column
	private Integer orderexecuted;

	@Column
	private String exectype;

	@Column
	private String md5sum;

	@Column
	private String description;

	@Column
	private String comments;

	@Column
	private String tag;

	@Column
	private String liquibase;

	@Column
	private String contexts;

	@Column
	private String labels;


	public String getId(){
		return id;
	}


	public void setId(String id){
		this.id = id;
	}


	public String getAuthor(){
		return author;
	}


	public void setAuthor(String author){
		this.author = author;
	}


	public String getFilename(){
		return filename;
	}


	public void setFilename(String filename){
		this.filename = filename;
	}


	public Date getDateexecuted(){
		return dateexecuted;
	}


	public void setDateexecuted(Date dateexecuted){
		this.dateexecuted = dateexecuted;
	}


	public Integer getOrderexecuted(){
		return orderexecuted;
	}


	public void setOrderexecuted(Integer orderexecuted){
		this.orderexecuted = orderexecuted;
	}


	public String getExectype(){
		return exectype;
	}


	public void setExectype(String exectype){
		this.exectype = exectype;
	}


	public String getMd5sum(){
		return md5sum;
	}


	public void setMd5sum(String md5sum){
		this.md5sum = md5sum;
	}


	public String getDescription(){
		return description;
	}


	public void setDescription(String description){
		this.description = description;
	}


	public String getComments(){
		return comments;
	}


	public void setComments(String comments){
		this.comments = comments;
	}


	public String getTag(){
		return tag;
	}


	public void setTag(String tag){
		this.tag = tag;
	}


	public String getLiquibase(){
		return liquibase;
	}


	public void setLiquibase(String liquibase){
		this.liquibase = liquibase;
	}


	public String getContexts(){
		return contexts;
	}


	public void setContexts(String contexts){
		this.contexts = contexts;
	}


	public String getLabels(){
		return labels;
	}


	public void setLabels(String labels){
		this.labels = labels;
	}

}