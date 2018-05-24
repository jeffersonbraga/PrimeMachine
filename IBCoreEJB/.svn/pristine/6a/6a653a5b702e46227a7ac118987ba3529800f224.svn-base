package br.com.opsocial.ejb.entity.groups;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.GroupsRssfeedsId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.rssfeed.RssFeed;

@Entity
@Table(name = "groupsrssfeeds", schema = "opsocial")
@IdClass(GroupsRssfeedsId.class)
public class GroupsRssfeeds extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@JoinColumn(name="idgroup")
	private Groups groups;
	
	@Id
	@ManyToOne()
	@JoinColumn(name = "idrssfeed")
	private RssFeed rssFeed;

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}

	public void setRssFeed(RssFeed rssFeed) {
		this.rssFeed = rssFeed;
	}
}
