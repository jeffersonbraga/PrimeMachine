package br.com.opsocial.ejb.entity.application;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="oplog", schema="opsocial")
public class OpLog extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idoplog")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "oplog_idoplog_seq")
	private Long idOpLog;
	
    @Column(name = "entity")
	private String entity;
    
    @Column(name = "operation")
    private String operation;
    
    @Column(name = "iduser")
    private Long idUser;
    
    @Column(name = "entitykey")
    private Long entityKey;
    
    @Column(name="date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

	public Long getIdOpLog() {
		return idOpLog;
	}

	public void setIdOpLog(Long idOpLog) {
		this.idOpLog = idOpLog;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getIdtUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Long getId() {		
		return idOpLog;
	}

	@Override
	public void setId(Long id) {
		this.idOpLog = id;		
	}
}
