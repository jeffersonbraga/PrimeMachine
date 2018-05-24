package br.com.lsd.service.verificacao_fiscal;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import br.com.lsd.bo.verificacao_fiscal.Verificacao_fiscalBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;

public class Verificacao_fiscalService {

	public List<Execucao_formulario> buscarExecucoesCriptografadas() {
		Verificacao_fiscalBO verif = new Verificacao_fiscalBO();
		return verif.buscarExecucoesCriptografadas();
	}

	public List<Execucao_formulario> buscarExecucoes(byte[] dados) {
		Verificacao_fiscalBO verif = new Verificacao_fiscalBO();
		File file = null;
		try {
			file = new File(ContextoAplicacao.CAMINHO_APLICACAO + "/temp/tmp_" + (new Date().getTime()) + ".primum");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(dados);
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return verif.buscarExecucoes(file);
	}

}
