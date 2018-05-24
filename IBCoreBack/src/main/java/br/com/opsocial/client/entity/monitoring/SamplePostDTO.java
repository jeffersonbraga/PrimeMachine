package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class SamplePostDTO extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	private Long idSamplePost;
	private SampleDTO sampleDTO;
	private String postId;
	private Character network;

	public SamplePostDTO() {
	}

	public Long getIdSamplePost() {
		return idSamplePost;
	}

	public void setIdSamplePost(Long idSamplePost) {
		this.idSamplePost = idSamplePost;
	}

	public SampleDTO getSampleDTO() {
		return sampleDTO;
	}

	public void setSampleDTO(SampleDTO sampleDTO) {
		this.sampleDTO = sampleDTO;
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
