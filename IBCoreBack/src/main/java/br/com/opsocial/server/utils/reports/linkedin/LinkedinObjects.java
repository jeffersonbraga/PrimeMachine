package br.com.opsocial.server.utils.reports.linkedin;

import java.io.Serializable;
import java.util.List;

public class LinkedinObjects implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long _total;
	List<LinkedinObject> values;

	public List<LinkedinObject> getValues() {
		return values;
	}

	public void setValues(List<LinkedinObject> values) {
		this.values = values;
	}

	public Long get_total() {
		return _total;
	}

	public void set_total(Long _total) {
		this._total = _total;
	}

	public LinkedinObjects() {}
}
