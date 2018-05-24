package br.com.opsocial.ejb.entity.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name="twitterpagings", schema="opsocial")
public class TwitterPaging extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idtwitterpaging")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterpagings_idtwitterpaging_seq")
	private Long idTwitterPaging;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "term")
	private String term;
	
	@Column(name="maxid")
	private Long maxId;
	
	@Column(name="sinceid")
	private Long sinceId;

	@Column(name="count")
	private Integer count;
	
	@Column(name="page")
	private Integer page;
	
	public TwitterPaging() {
	}

	public Long getIdTwitterPaging() {
		return idTwitterPaging;
	}

	public void setIdTwitterPaging(Long idTwitterPaging) {
		this.idTwitterPaging = idTwitterPaging;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Long getSinceId() {
		return sinceId;
	}

	public void setSinceId(Long sinceId) {
		this.sinceId = sinceId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	@Override
	public Long getId() {
		return idTwitterPaging;
	}
	
	@Override
	public void setId(Long id) {
		idTwitterPaging = id;
	}
}
