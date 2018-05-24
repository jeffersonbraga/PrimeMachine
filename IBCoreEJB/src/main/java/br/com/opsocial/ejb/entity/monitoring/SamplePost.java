package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="samplesposts", schema="opsocial")
public class SamplePost extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idsamplepost")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "samplesposts_idsamplepost_seq")
	private Long idSamplePost;
	
	@JoinColumn(name = "idsample", referencedColumnName = "idsample")
	@ManyToOne()
	private Sample sample;
	
	@Column(name = "postid")
	private String postId;
	
	@Column(name = "network")
	private Character network;

	public SamplePost() {
	}

	public Long getIdSamplePost() {
		return idSamplePost;
	}

	public void setIdSamplePost(Long idSamplePost) {
		this.idSamplePost = idSamplePost;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}
}
