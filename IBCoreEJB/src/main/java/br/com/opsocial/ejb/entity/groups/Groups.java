package br.com.opsocial.ejb.entity.groups;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="groups", schema="opsocial")
public class Groups extends Persistent {
	
	private static final long serialVersionUID = 1L;

	public static final Character PROFILEGROUP = 'P';
	public static final Character RSSFEEDGROUP = 'R';
	
	@Id
	@Column(name = "idgroup")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "groups_idgroup_seq")
	private Long idGroup;
	
    @Column(name = "name")
	private String name;
    
	@JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	@ManyToOne()
	private Account account;
	
	@Column(name = "grouptype")
	private Character groupType;
	
	@Transient
	private List<Object> groupsObject;

	public List<Object> getGroupsObject() {
		return groupsObject;
	}

	public void setGroupsObject(List<Object> groupsObject) {
		this.groupsObject = groupsObject;
	}

	public Character getGroupType() {
		return groupType;
	}

	public void setGroupType(Character groupType) {
		this.groupType = groupType;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Long getId() {		
		return idGroup;
	}

	@Override
	public void setId(Long id) {
		this.idGroup = id;		
	}
}
