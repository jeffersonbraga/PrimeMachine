import { Documento } from './../documento/Documento';
import { Endereco } from './../endereco/Endereco';

export class Pessoa {

	public cd_pessoa: string;

	public nm_pessoa: string;

	public nr_registro: string;

	public nr_cpf: string;

	public ds_email: string;

	public nr_telefone: string;

	public endereco: Endereco = new Endereco();

	public documentos: Documento[] = [];

}