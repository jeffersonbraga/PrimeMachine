package br.com.opsocial.client.entity.twitter;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class TrendingTopicDTO extends Persistent implements Comparable<TrendingTopicDTO> {

	private static final long serialVersionUID = 1L;
	
	private Long idTrendingTopic;
	private String name;
	private String query;
	private String url;
	private Long trendingDate;
	private Integer position;
	
	public TrendingTopicDTO() {
		
	}

	public Long getIdTrendingTopic() {
		return idTrendingTopic;
	}

	public void setIdTrendingTopic(Long idTrendingTopic) {
		this.idTrendingTopic = idTrendingTopic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getTrendingDate() {
		return trendingDate;
	}

	public void setTrendingDate(Long trendingDate) {
		this.trendingDate = trendingDate;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public int compareTo(TrendingTopicDTO o) {
		
		if(this.position < o.position) {  
            return -1;  
        }  
		
		if (this.position > o.position) {  
            return 1; 
		}
		
		return 0;
	}
}
