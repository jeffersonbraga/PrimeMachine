package br.com.opsocial.ejb.entity.ipag;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IpagObject implements Serializable {

	private String IPAG_USER = "adm@opsocial.com.br";

	private String IPAG_OPERATION_PAGAMENTO;

	private String IPAG_URL_CALLBACK = "http://minhaloja.com";

	private String IPAG_RETURN_TYPE;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//FIXO
	private String identificacao 	= "adm@opsocial.com.br";
	private String operacao 		= "Pagamento";
	private String url_retorno 		= "http://minhaloja.com";
	private String retorno_tipo 	= "xml";

	//BUSCAR USUÁRIO CRIADO PELO FORM CADASTRO
	private String nome 			= "teste ipag";
	private String email 			= "ipag@teste.com.br";
	private String fone 			= "1839161627";
	private String endereco 		= "Rua Teste";
	private String numero_endereco 	= "1000";
	private String complemento 		= "";
	private String bairro 			= "Bairro Teste";
	private String cidade 			= "São Paulo";
	private String estado 			= "SP";
	private String pais 			= "Brasil";
	private String cep 				= "01156060";

	//ALTERNATIVO E INFORMADO PELO USUÁRIO
	private String metodo 		= "visa";
	private String parcelas 	= "1";
	private String nome_cartao 	= "JOSE TESTE";
	private String num_cartao 	= "4066553613548107";
	private String cvv_cartao 	= "123";
	private String mes_cartao 	= "10";
	private String ano_cartao 	= "17";

	//PROCESSAMENTO DA VENDA
	private String pedido 		= "10000000";
	private String valor 		= "1.00";
	private String doc 			= "11111111100";
	
	public IpagObject() {

		//FIXO
		this.identificacao 	= "adm@opsocial.com.br";
		this.operacao 		= "Pagamento";
		this.url_retorno 	= "http://minhaloja.com";
		this.retorno_tipo 	= "xml";

		//BUSCAR USUÁRIO CRIADO PELO FORM CADASTRO
		this.nome 				= "teste ipag";
		this.email 				= "ipag@teste.com.br";
		this.fone 				= "1839161627";
		this.endereco 			= "Rua Teste";
		this.numero_endereco 	= "1000";
		this.complemento 		= "";
		this.bairro 			= "Bairro Teste";
		this.cidade 			= "São Paulo";
		this.estado 			= "SP";
		this.pais 				= "Brasil";
		this.cep 				= "01156060";

		//ALTERNATIVO E INFORMADO PELO USUÁRIO
		this.metodo 		= "visa";
		this.parcelas 		= "1";
		this.nome_cartao 	= "JOSE TESTE";
		this.num_cartao 	= "4066553613548107";
		this.cvv_cartao 	= "123";
		this.mes_cartao 	= "10";
		this.ano_cartao 	= "17";

		//PROCESSAMENTO DA VENDA
		this.pedido 		= "10000000";
		this.valor 			= "1.00";
		this.doc 			= "11111111100";
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public String getOperacao() {
		return operacao;
	}

	public String getUrl_retorno() {
		return url_retorno;
	}

	public String getRetorno_tipo() {
		return retorno_tipo;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getFone() {
		return fone;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getNumero_endereco() {
		return numero_endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

	public String getPais() {
		return pais;
	}

	public String getCep() {
		return cep;
	}

	public String getMetodo() {
		return metodo;
	}

	public String getParcelas() {
		return parcelas;
	}

	public String getNome_cartao() {
		return nome_cartao;
	}

	public String getNum_cartao() {
		return num_cartao;
	}

	public String getCvv_cartao() {
		return cvv_cartao;
	}

	public String getMes_cartao() {
		return mes_cartao;
	}

	public String getAno_cartao() {
		return ano_cartao;
	}

	public String getPedido() {
		return pedido;
	}

	public String getValor() {
		return valor;
	}

	public String getDoc() {
		return doc;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public void setUrl_retorno(String url_retorno) {
		this.url_retorno = url_retorno;
	}

	public void setRetorno_tipo(String retorno_tipo) {
		this.retorno_tipo = retorno_tipo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setNumero_endereco(String numero_endereco) {
		this.numero_endereco = numero_endereco;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public void setParcelas(String parcelas) {
		this.parcelas = parcelas;
	}

	public void setNome_cartao(String nome_cartao) {
		this.nome_cartao = nome_cartao;
	}

	public void setNum_cartao(String num_cartao) {
		this.num_cartao = num_cartao;
	}

	public void setCvv_cartao(String cvv_cartao) {
		this.cvv_cartao = cvv_cartao;
	}

	public void setMes_cartao(String mes_cartao) {
		this.mes_cartao = mes_cartao;
	}

	public void setAno_cartao(String ano_cartao) {
		this.ano_cartao = ano_cartao;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

}
