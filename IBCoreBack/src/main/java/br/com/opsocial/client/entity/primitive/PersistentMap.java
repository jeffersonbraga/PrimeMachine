package br.com.opsocial.client.entity.primitive;

import java.util.HashMap;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class PersistentMap<T, K> extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private HashMap<T, K> value;
	
	public PersistentMap() {
	}
	
	public PersistentMap(HashMap<T, K> value)  {
		this.value = value;
	}

	public HashMap<T, K> getValue() {
		return value;
	}

	public void setValue(HashMap<T, K> value) {
		this.value = value;
	}

}
