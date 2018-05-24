package br.com.lsd.bo.pessoa;

import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.endereco.Endereco;
import br.com.primum.modelo.pessoa.Pessoa;

public class PessoaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public void gravarPessoa(Pessoa pessoa) {

		try {

			this.setDao(new GenericDAO());

			if (pessoa.getCd_pessoa() == null) {
				this.getDao().salvar(pessoa);
			} else {
				this.getDao().atualizar(pessoa);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void gravarPessoa(Pessoa pessoa, Endereco endereco) {

		try {
			this.setDao(new GenericDAO());
			this.getDao().abrirTransacao();
			if (pessoa.getCd_pessoa() == null) {
				
				this.getDao().salvar(pessoa);
				this.getDao().salvar(endereco);
			} else {
				
				this.getDao().atualizar(pessoa);
				if (endereco.getCd_endereco() == null) {
					this.getDao().salvar(endereco);
				} else {
					this.getDao().atualizar(endereco);
				}
			}
			
			this.getDao().fecharConexao();
			
		} catch (Exception e) {
			this.getDao().rollbackTransacao();
			e.printStackTrace();
		}
	}
}