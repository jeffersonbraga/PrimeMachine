package br.com.lsd.service.pessoa;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.endereco.Endereco;
import br.com.primum.modelo.pessoa.Pessoa;

public class PessoaService {

	private GenericDAO dao;
	public GenericDAO getDao() {
		return dao;
	}
	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	public void gravarPessoa(Pessoa pessoa, Endereco endereco) throws Exception {
		try{
			this.setDao(new GenericDAO());
			this.getDao().salvar(pessoa);
			
			//endereco.setCd_pessoa(pessoa.getCd_pessoa());
			this.getDao().salvar(endereco);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
