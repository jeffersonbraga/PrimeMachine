package br.com.opsocial.ejb.entity.socialnetworks.facebook;

public class PageConsumptionsType {
	
	private Integer otherClicks = 0;
	private Integer linkClicks = 0;
	private Integer photoView = 0;
	private Integer videoPlay = 0;
	
	/**
	 * @return the otherClicks
	 */
	public Integer getOtherClicks() {
		return otherClicks;
	}
	/**
	 * @param otherClicks the otherClicks to set
	 */
	public void setOtherClicks(Integer otherClicks) {
		this.otherClicks = otherClicks;
	}
	/**
	 * @return the linkClicks
	 */
	public Integer getLinkClicks() {
		return linkClicks;
	}
	/**
	 * @param linkClicks the linkClicks to set
	 */
	public void setLinkClicks(Integer linkClicks) {
		this.linkClicks = linkClicks;
	}
	/**
	 * @return the photoView
	 */
	public Integer getPhotoView() {
		return photoView;
	}
	/**
	 * @param photoView the photoView to set
	 */
	public void setPhotoView(Integer photoView) {
		this.photoView = photoView;
	}
	public Integer getVideoPlay() {
		return videoPlay;
	}
	public void setVideoPlay(Integer videoPlay) {
		this.videoPlay = videoPlay;
	}

}
