package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.InstagramPostTagId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="instagrampoststags", schema="opsocial")
@IdClass(InstagramPostTagId.class)
public class InstagramPostTag extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmedia")
	private InstagramReportPost instagramReportPost;
	
	@Id
	@Column(name="tag")
	private String tag;
	
	public InstagramPostTag() {
	}

	public InstagramReportPost getInstagramReportPost() {
		return instagramReportPost;
	}

	public void setInstagramReportPost(InstagramReportPost instagramReportPost) {
		this.instagramReportPost = instagramReportPost;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
