package br.com.opsocial.client.entity.primitive;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class BitlyShortenUrlDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String longUrl;
	private String hash;
	private boolean isShortened;
	
	public BitlyShortenUrlDTO() {
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the longUrl
	 */
	public String getLongUrl() {
		return longUrl;
	}
	/**
	 * @param longUrl the longUrl to set
	 */
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the isShortened
	 */
	public boolean isShortened() {
		return isShortened;
	}

	/**
	 * @param isShortened the isShortened to set
	 */
	public void setShortened(boolean isShortened) {
		this.isShortened = isShortened;
	}
	
	

}
