package br.com.primum.modelo.resposta_exec_formulario;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.evidencia_resposta_exec.Evidencia_resposta_exec;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.plano_acao_formulario.Plano_acao_formulario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="resposta_exec_formulario")
public class Resposta_exec_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-6109832094232366679L;

	@Transient	
	private List<PropertyChangeListener> listener = new ArrayList<>();

	@Transient	
    private List<PropertyChangeListener> listenerCpoCalculado = new ArrayList<>();

	@Id
	@Column
	private Integer cd_resposta_exec_formulario;

	@Column
	@ChaveEstrangeira(nomeObjetoOrigem="cd_execucao_formulario")
	private Integer cd_execucao_formulario;

	@Column
	private Integer cd_campo_formulario;

	@Column
	private Integer cd_resposta_campo_formulario;

	@Column
	private String ds_resposta_exec_formulario;

	@Column
	private Integer tp_resposta_exec_formulario;

	@Column
	private Integer vl_indice_grid;

	@Transient
	//@Temporal(value = TemporalType.DATE)
	private Date dt_execucao_formulario;

	@Transient
	private Execucao_formulario execucao_formulario;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao_formulario.Plano_acao_formulario", campoBuscar="cd_resposta_exec_formulario", campoComparar="cd_resposta_exec_formulario", persistir=true)
	private Plano_acao_formulario plano_acao_formulario;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.campo_formulario.Campo_formulario", campoBuscar="cd_campo_formulario", campoComparar="cd_campo_formulario", persistir=false)
	private Campo_formulario campo_formulario;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.evidencia_resposta_exec.Evidencia_resposta_exec", campoBuscar="cd_resposta_exec_formulario", persistir=true)
	private List<Evidencia_resposta_exec> listaEvidencias;

	
	public Integer getCd_resposta_exec_formulario() {
		return cd_resposta_exec_formulario;
	}

	public void setCd_resposta_exec_formulario(Integer cd_resposta_exec_formulario) {
		this.cd_resposta_exec_formulario = cd_resposta_exec_formulario;
	}

	public Integer getCd_execucao_formulario() {
		return cd_execucao_formulario;
	}

	public void setCd_execucao_formulario(Integer cd_execucao_formulario) {
		this.cd_execucao_formulario = cd_execucao_formulario;
	}

	public Integer getCd_campo_formulario() {
		return cd_campo_formulario;
	}

	public void setCd_campo_formulario(Integer cd_campo_formulario) {
		this.cd_campo_formulario = cd_campo_formulario;
	}

	public Integer getCd_resposta_campo_formulario() {
		return cd_resposta_campo_formulario;
	}

	public void setCd_resposta_campo_formulario(Integer cd_resposta_campo_formulario) {
		this.cd_resposta_campo_formulario = cd_resposta_campo_formulario;
	}

	public String getDs_resposta_exec_formulario() {
		return ds_resposta_exec_formulario;
	}

	public void setDs_resposta_exec_formulario(String ds_resposta_exec_formulario) {
		this.ds_resposta_exec_formulario = ds_resposta_exec_formulario;
	}

	public Integer getTp_resposta_exec_formulario() {
		return tp_resposta_exec_formulario;
	}

	public void setTp_resposta_exec_formulario(Integer tp_resposta_exec_formulario) {
		this.tp_resposta_exec_formulario = tp_resposta_exec_formulario;
	}

	public Plano_acao_formulario getPlano_acao_formulario() {
		return plano_acao_formulario;
	}

	public void setPlano_acao_formulario(Plano_acao_formulario plano_acao_formulario) {
		this.plano_acao_formulario = plano_acao_formulario;
	}

	public Campo_formulario getCampo_formulario() {
		return campo_formulario;
	}

	public void setCampo_formulario(Campo_formulario campo_formulario) {
		this.campo_formulario = campo_formulario;
	}

	public Integer getVl_indice_grid() {
		return vl_indice_grid;
	}

	public void setVl_indice_grid(Integer vl_indice_grid) {
		this.vl_indice_grid = vl_indice_grid;
	}

	public Date getDt_execucao_formulario() {
		return dt_execucao_formulario;
	}

	public void setDt_execucao_formulario(Date dt_execucao_formulario) {
		this.dt_execucao_formulario = dt_execucao_formulario;
	}

	public Execucao_formulario getExecucao_formulario() {
		return execucao_formulario;
	}

	public void setExecucao_formulario(Execucao_formulario execucao_formulario) {
		this.execucao_formulario = execucao_formulario;
	}

	public List<Evidencia_resposta_exec> getListaEvidencias() {
		return listaEvidencias;
	}

	public void setListaEvidencias(List<Evidencia_resposta_exec> listaEvidencias) {
		this.listaEvidencias = listaEvidencias;
	}

    @Override
    public String toString() {

        return this.getCd_resposta_exec_formulario() + " - " + this.getDs_resposta_exec_formulario();
    }

    private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void addChangeListenerCpoCalculado(PropertyChangeListener newListener) {

        if (!listenerCpoCalculado.contains(newListener)) {

            listenerCpoCalculado.add(newListener);
        }
    }

    public void removeChangeListener() {

        listener = null;
    }

    public void removeChangeListenerCpoCalculado() {

        listenerCpoCalculado = null;
    }

}