package br.com.opsocial.ejb.entity.sets;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="sets", schema="opsocial")
public class Set extends Persistent { 

	private static final long serialVersionUID = 1L;
	
	public static final Character SCHEDULED = 'O';
	public static final Character STARTED = 'S';
	public static final Character INTERRUPTED = 'I';
	public static final Character FINALIZED = 'F';
	
	public static final String ON = "on";
	public static final String OFF = "off";
	
	@Id
	@Column(name = "idset")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "set_idset_seq")
	private Long idSet;
	
	@Column(name="startdate")
	private Long startDate;
	
	@Column(name="enddate")
	private Long endDate;
	
	@Column(name="frequency")
	private Integer frequency;
	
	/*@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	private Profile profile;*/
	
	@Column(name="isschedule")
	private Character isSchedule;

	@Column(name="state")
	private String state;
	
	@Column(name="status")
	private Character status;
	
	@Column(name="postindex")
	private String postIndex;
	
	@Column(name="amountposts")
	private Integer amountPosts;

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "set", fetch=FetchType.LAZY)
	//@OrderBy("idPost desc")
	private List<Post> posts;
	
	@Column(name = "active")
	private char active = 'T';

	public Set() {
	}
	
	public Long getIdSet() {
		return idSet;
	}

	public void setIdSet(Long idSet) {
		this.idSet = idSet;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	/*public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}*/
	
	public Character getIsSchedule() {
		return isSchedule;
	}

	public void setIsSchedule(Character isSchedule) {
		this.isSchedule = isSchedule;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	@Override
	public Boolean isActive() {		
		return active == 'T';
	}

	public char getActive() {
		return active;
	}

	public void setActive(char  active) {
		this.active = active;
	}
	
	@Override
	public Long getId() {
		return idSet;
	}
	
	@Override
	public void setId(Long id) {
		idSet = id;
	}
	
	public Integer getAmountPosts() {
		return amountPosts;
	}

	public void setAmountPosts(Integer amountPosts) {
		this.amountPosts = amountPosts;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
}
