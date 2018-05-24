package br.com.primum.bo.dicionario;

import java.util.Comparator;

import br.com.primum.modelo.campo.Campo;

public class OrdenadorDicionario implements Comparator<Campo> {

	@Override
	public int compare(Campo arg0, Campo arg1) {
		// TODO Auto-generated method stub
		
		
		int retorno = 1;
		if ((arg0.getTp_chaveprimaria() != null) && (arg0.getTp_chaveprimaria() == 1) ) {
			retorno = -1;
		} else if ((arg0.getTp_identificador() != null) && (arg0.getTp_identificador() == 1) ) {
			retorno = -1;
		}
		
//		//LogPrimum.mensagem("*************** COMPARE ********************");
//		//LogPrimum.mensagem(arg0.getCd_campo().compareTo(arg1.getCd_campo()));
//		//LogPrimum.mensagem(arg1.getCd_campo().compareTo(arg0.getCd_campo()));
//		//LogPrimum.mensagem("arg0.getCd_campo(): " + arg0.getCd_campo());
//		//LogPrimum.mensagem("arg1.getCd_campo(): " + arg1.getCd_campo());
//		//LogPrimum.mensagem("retorno: " + retorno);
//		
//		if (arg0.getCd_campo() < arg1.getCd_campo()) {
//			//LogPrimum.mensagem("*************** INVERSAO ********************");
//		}
		return retorno;
	}
}
