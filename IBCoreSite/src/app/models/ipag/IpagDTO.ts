export class IpagDTO {

	public static RETORNO_TIPO_XML : string 			= "";

	public static RETORNO_TIPO_JSON: string 			= "";

	public static METODO_BANDEIRA_VISA: string 		    = "";

	public static METODO_BANDEIRA_MASTER: string		= "";

	public static METODO_BANDEIRA_AMERICAN: string	    = "";

	public static  METODO_BANDEIRA_DINNERS: string 	    = "";

	//FIXO
	public identificacao: string;

	public operacao: string;

	public url_retorno: string;

	public retorno_tipo: string;

	//BUSCAR USUÁRIO CRIADO PELO FORM CADASTRO
	public nome: string;

	public email: string;

	public fone: string;

	public endereco: string;

	public numero_endereco: string;

	public complemento: string;

	public bairro: string;

	public cidade: string;

	public estado: string;

	public pais: string;

	public cep: string;

	//ALTERNATIVO E INFORMADO PELO USUÁRIO
	public metodo: string;

	public parcelas: string;

	public nome_cartao: string;

	public num_cartao: string;

	public cvv_cartao: string;

	public mes_cartao: string;

    public ano_cartao: string;

	public frequencia: string;

	public intervalo: string;

	public inicio: string;

	public ciclos: string;

	public trial: string;

	public valor_rec: string;

	//PROCESSAMENTO DA VENDA
	public pedido: string;

	public valor: string;

	public doc: string;

	public plantype: string;
}