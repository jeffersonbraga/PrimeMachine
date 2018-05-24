package br.com.opsocial.client.entity.monitoring;

import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;

public class PostMonitoringDTO extends Persistent implements Comparable<PostMonitoringDTO> {
	
	private static final long serialVersionUID = 1L;
	
	public static final Character POSITIVE = 'P';
	public static final Character NEUTER = 'S';
	public static final Character NEGATIVE = 'N';
	public static final Character NOT_QUALIF = 'Q';
	
	private Monitoring monitoringDTO;
	private String term;
	private Character visible = 'T';
	private Character qualification;
	private Character network;
	private Long createdTime;
	private Long retrievedDate;
	private Boolean hasHashTags;
	private Boolean hasLinks;
	private String postId;
	private List<MonitoringPostTag> monitoringPostTags;
	private Boolean selected = false;
	private MonitoringPostLocation monitoringPostLocation;
	private String userSex;
	
	public PostMonitoringDTO() {
		
	}

	public Monitoring getMonitoring() {
		return monitoringDTO;
	}

	public void setMonitoring(Monitoring monitoringDTO) {
		this.monitoringDTO = monitoringDTO;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Character getVisible() {
		return visible;
	}

	public void setVisible(Character visible) {
		this.visible = visible;
	}

	public Character getQualification() {
		return qualification;
	}

	public void setQualification(Character qualification) {
		this.qualification = qualification;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getRetrievedDate() {
		return retrievedDate;
	}

	public void setRetrievedDate(Long retrievedDate) {
		this.retrievedDate = retrievedDate;
	}

	public Boolean getHasHashTags() {
		return hasHashTags;
	}

	public void setHasHashTags(Boolean hasHashTags) {
		this.hasHashTags = hasHashTags;
	}

	public Boolean getHasLinks() {
		return hasLinks;
	}

	public void setHasLinks(Boolean hasLinks) {
		this.hasLinks = hasLinks;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public List<MonitoringPostTag> getMonitoringPostTags() {
		return monitoringPostTags;
	}

	public void setMonitoringPostTags(List<MonitoringPostTag> monitoringPostTags) {
		this.monitoringPostTags = monitoringPostTags;
	}
	
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public MonitoringPostLocation getMonitoringPostLocation() {
		return monitoringPostLocation;
	}

	public void setMonitoringPostLocation(
			MonitoringPostLocation monitoringPostLocation) {
		this.monitoringPostLocation = monitoringPostLocation;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	@Override
	public int compareTo(PostMonitoringDTO o) {
		if (this.createdTime < o.createdTime) {  
            return -1;  
        }  
		
        if (this.createdTime > o.createdTime) {  
            return 1;  
        }
        
        return 0;  
	}
}
