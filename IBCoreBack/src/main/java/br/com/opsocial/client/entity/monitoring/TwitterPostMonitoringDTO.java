package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.twitter.TwitterPost;

public class TwitterPostMonitoringDTO extends PostMonitoringDTO {
	
	private static final long serialVersionUID = 1L;
	
	public static final Character POSITIVE = 'P';
	public static final Character NEUTER = 'S';
	public static final Character NEGATIVE = 'N';
	
	private TwitterPost twitterPostDTO;
	private String idLocation;
	
	public TwitterPostMonitoringDTO() {
		
	}
	
	public TwitterPost getTwitterPostDTO() {
		return twitterPostDTO;
	}

	public void setTwitterPostDTO(TwitterPost twitterPostDTO) {
		this.twitterPostDTO = twitterPostDTO;
	}

	public String getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}
}
