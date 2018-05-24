package br.com.opsocial.server.utils.facebook.entities;

import java.io.Serializable;
import java.util.List;

public class ShareData implements Serializable {
	
	private static final long serialVersionUID = 1751771801172269594L;
	
	private List<Share> data;
	
	public ShareData() {
		
	}

	public List<Share> getData() {
		return data;
	}

	public void setData(List<Share> data) {
		this.data = data;
	}
}
