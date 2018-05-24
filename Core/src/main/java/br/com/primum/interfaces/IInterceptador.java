package br.com.primum.interfaces;

public interface IInterceptador {
	
	public void setObjetoInterceptado(Object o);

	public Object getObjetoInterceptado();

	public void setTransactionID(String id);

	public void enviarNotificacao(Object o);
}
