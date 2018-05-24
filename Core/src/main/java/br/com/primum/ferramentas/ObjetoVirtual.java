package br.com.primum.ferramentas;

import java.io.Serializable;

/**
 * Created by Jefferson on 09/04/2015.
 */
public class ObjetoVirtual implements Serializable {

    private String campoFiltro 		= "cd_campo_formulario";
    private String dataField		= "ds_resposta_exec_formulario";
    private String tabelaPesquisa	= "resposta_exec_formulario";
    private String campoBusca		= "ds_resposta_exec_formulario";
    private String campoRetorno		= "ds_resposta_exec_formulario";
    private String classeJava		= "br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario";
    private String valorFiltro		= null;

    public String getCampoFiltro() {
        return campoFiltro;
    }

    public void setCampoFiltro(String campoFiltro) {
        this.campoFiltro = campoFiltro;
    }

    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public String getTabelaPesquisa() {
        return tabelaPesquisa;
    }

    public void setTabelaPesquisa(String tabelaPesquisa) {
        this.tabelaPesquisa = tabelaPesquisa;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public String getCampoRetorno() {
        return campoRetorno;
    }

    public void setCampoRetorno(String campoRetorno) {
        this.campoRetorno = campoRetorno;
    }

    public String getClasseJava() {
        return classeJava;
    }

    public void setClasseJava(String classeJava) {
        this.classeJava = classeJava;
    }

    public String getValorFiltro() {
        return valorFiltro;
    }

    public void setValorFiltro(String valorFiltro) {
        this.valorFiltro = valorFiltro;
    }
}
