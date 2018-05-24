package br.com.opsocial.ejb.entity.feed;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="tab", schema="opsocial")
public class Tab extends Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idtab")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tab_idtab_seq")
	private Long idTab;
	
	@Column(name = "name")
	private String name;
	
	@JoinColumn(name = "iduser", referencedColumnName = "iduser")
	@ManyToOne()
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tab", fetch=FetchType.EAGER)
	@OrderBy("streamOrder asc")
	private List<Stream> streams;

	public Long getIdTab() {
		return idTab;
	}

	public void setIdTab(Long idTab) {
		this.idTab = idTab;
	}

	public List<Stream> getStreams() {
		return streams;
	}

	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Long getId() {		
		return idTab;
	}

	@Override
	public void setId(Long id) {
		this.idTab = id;		
	}

}
