package br.com.opsocial.server.utils.instagram.entities;

import java.io.Serializable;

public class FriendshipStatus implements Serializable {

	private static final long serialVersionUID = 3840897058992702737L;
	
	private Boolean following;
	private Boolean followedBy;
	private Boolean blocking;
	private Boolean isPrivate;
	private Boolean incomingRequest;
	private Boolean outgoingRequest;
	private Boolean isBestie;
	
	public FriendshipStatus() {
	}

	public Boolean getFollowing() {
		return following;
	}

	public void setFollowing(Boolean following) {
		this.following = following;
	}

	public Boolean getFollowedBy() {
		return followedBy;
	}

	public void setFollowedBy(Boolean followedBy) {
		this.followedBy = followedBy;
	}

	public Boolean getBlocking() {
		return blocking;
	}

	public void setBlocking(Boolean blocking) {
		this.blocking = blocking;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Boolean getIncomingRequest() {
		return incomingRequest;
	}

	public void setIncomingRequest(Boolean incomingRequest) {
		this.incomingRequest = incomingRequest;
	}

	public Boolean getOutgoingRequest() {
		return outgoingRequest;
	}

	public void setOutgoingRequest(Boolean outgoingRequest) {
		this.outgoingRequest = outgoingRequest;
	}

	public Boolean getIsBestie() {
		return isBestie;
	}

	public void setIsBestie(Boolean isBestie) {
		this.isBestie = isBestie;
	}
}
