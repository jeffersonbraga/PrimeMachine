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
@Table(name="exceptions", schema="opsocial")
public class Exception extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	public static final Integer FACE_POST_UNSAVED = 0;
	public static final Integer FACE_INFL_USERS_NOT_UPDATED = 1;
	public static final Integer FACE_INFL_USERS_UPDATE_ERROR = 2;
	public static final Integer INSTA_TAG_MONITORING_POSTS_ERROR = 3;
	public static final Integer INSTA_DELETE_MONITORING_POSTS_ERROR = 4;

	@Id
	@Column(name = "idexception")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "exceptions_idexception_seq")
	private Long idException;

	@Column(name = "source")
	private String source;

	@Column(name = "value")
	private Long value;

	@Column(name = "message")
	private String message;

	@Column(name = "type")
	private Character type;

	@Column(name = "objectid")
	private String objectId;

	@Column(name = "code")
	private Integer code;

	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name = "exception")
	private String exception;

	public Exception() {
	}

	public Long getIdException() {
		return idException;
	}

	public void setIdException(Long idException) {
		this.idException = idException;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
