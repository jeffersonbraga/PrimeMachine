package br.com.opsocial.server.utils.facebook.entities;

import java.io.Serializable;
import java.util.List;

public class AttachmentData implements Serializable {

	private static final long serialVersionUID = 2899946618758527282L;
	
	private List<Attachment> data;
	
	public AttachmentData() {
	
	}

	public List<Attachment> getData() {
		return data;
	}

	public void setData(List<Attachment> data) {
		this.data = data;
	}
}
