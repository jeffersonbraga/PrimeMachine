package br.com.lsd.service.dbconf;

import br.com.lsd.bo.dbconf.DbconfBO;
import br.com.primum.modelo.dbconf.Dbconf;

public class DbconfService {

	private DbconfBO bo;

	public DbconfBO getBo() {
		return bo;
	}

	public void setBo(DbconfBO bo) {
		this.bo = bo;
	}
	
	public void salvarArquivo(Dbconf dbconf) {
		this.setBo(new DbconfBO());
		this.getBo().salvarDbconf(dbconf);
	}
}
