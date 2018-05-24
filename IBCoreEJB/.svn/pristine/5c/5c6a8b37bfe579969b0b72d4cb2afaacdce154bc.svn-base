package br.com.opsocial.ejb.entity.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="trendingtopics", schema="opsocial")
public class TrendingTopic extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idtrendingtopic")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "trendingtopics_idtrendingtopic_seq")
	private Long idTrendingTopic;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "query")
	private String query;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "trendingdate")
	private Long trendingDate;
	
	@Column(name = "position")
	private Integer position;
	
	public TrendingTopic() {
		
	}

	public Long getIdTrendingTopic() {
		return idTrendingTopic;
	}

	public void setIdTrendingTopic(Long idTrendingTopic) {
		this.idTrendingTopic = idTrendingTopic;
	}
	
	@Override
	public Long getId() {
		return idTrendingTopic;
	}
	
	@Override
	public void setId(Long id) {
		this.idTrendingTopic = id;
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

}
