package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facebookpostsinsights", schema="opsocial")
public class FacebookPostInsight extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idfacebookpostinsight")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "facebookpostsinsights_idfacebookpostinsight_seq")
	private Long idFacebookPostInsight;
	
	@Column(name = "postid")
	private String postId;
	
	@Column(name="property")
	private String property;
	
	@Column(name="value")
	private Long value;
	
	@Column(name="type")
	private String type;
	
	public FacebookPostInsight() {
	}

	public Long getIdFacebookPostInsight() {
		return idFacebookPostInsight;
	}

	public void setIdFacebookPostInsight(Long idFacebookPostInsight) {
		this.idFacebookPostInsight = idFacebookPostInsight;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
