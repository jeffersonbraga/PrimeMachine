package br.com.primum.ferramentas;

import java.io.Serializable;

/**
 * Created by Jefferson on 14/08/2016.
 */
public class ObjetoJHipster extends ObjetoVirtual implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object objetoJson = null;

	public Object getObjetoJson() {
		return objetoJson;
	}

	public void setObjetoJson(Object objetoJson) {
		this.objetoJson = objetoJson;
	}
}