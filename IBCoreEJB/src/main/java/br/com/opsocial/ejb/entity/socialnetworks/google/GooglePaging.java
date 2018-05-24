package br.com.opsocial.ejb.entity.socialnetworks.google;

public class GooglePaging {
	
	private String nextPageToken;
	private String etag;
	private Long count;
	
	public GooglePaging() {
		
	}
	
	public GooglePaging(Long count) {
		this.count = count;
	}

	/**
	 * @return the nextPageToken
	 */
	public String getNextPageToken() {
		return nextPageToken;
	}

	/**
	 * @param nextPageToken the nextPageToken to set
	 */
	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	/**
	 * @return the etag
	 */
	public String getEtag() {
		return etag;
	}

	/**
	 * @param etag the etag to set
	 */
	public void setEtag(String etag) {
		this.etag = etag;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
