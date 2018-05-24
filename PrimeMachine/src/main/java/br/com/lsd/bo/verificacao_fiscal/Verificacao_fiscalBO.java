package br.com.lsd.bo.verificacao_fiscal;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.hibernate.HibernateException;

import br.com.primum.bo.criptografia.Criptografar;
import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.certificado.CertificadoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.espelho.Espelho;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;

public class Verificacao_fiscalBO {

	@SuppressWarnings("unchecked")
	public List<Execucao_formulario> buscarExecucoesCriptografadas() {
		
		List<Execucao_formulario> listaExecucao = new ArrayList<>();
		try {
			GenericDAO dao = new GenericDAO();
			List<Espelho> lista = (List<Espelho>) dao.getSession().createCriteria(Espelho.class).list();
			for (Espelho espelho : lista) {
				File fPub = new File ("D:\\jboss-4.2.3.GA\\server\\default\\deploy\\Quality.war\\WEB-INF\\certificado\\chave.privada");
				byte[] textoCifrado = espelho.getDs_bytedata_espelho()[0];
				byte[] chaveCifrada = espelho.getDs_bytedata_espelho()[1];
				byte[] resultado = Criptografar.decifra(CertificadoBO.carregaChavePrivada(fPub), textoCifrado, chaveCifrada);
				Object objetoFinal = Criptografar.deserializarObjeto(resultado);
				listaExecucao.add((Execucao_formulario) objetoFinal);
				LogPrimum.mensagem("C�digo da execu��o:" + ((Execucao_formulario) objetoFinal).getCd_execucao_formulario() );
				LogPrimum.mensagem("Conclu�do");
			}
		} catch (HibernateException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException
				| InvalidAlgorithmParameterException | ClassNotFoundException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaExecucao;
	}

	@SuppressWarnings("unchecked")
	public List<Execucao_formulario> buscarExecucoes(File chave) {
		
		List<Execucao_formulario> listaExecucao = new ArrayList<>();
		try {
			GenericDAO dao = new GenericDAO();
			List<Espelho> lista = (List<Espelho>) dao.getSession().createCriteria(Espelho.class).list();
			for (Espelho espelho : lista) {
				//File fPub = new File ("D:\\jboss-4.2.3.GA\\server\\default\\deploy\\Quality.war\\WEB-INF\\certificado\\chave.privada");
				byte[] textoCifrado = espelho.getDs_bytedata_espelho()[0];
				byte[] chaveCifrada = espelho.getDs_bytedata_espelho()[1];
				byte[] resultado = Criptografar.decifra(CertificadoBO.carregaChavePrivada(chave), textoCifrado, chaveCifrada);
				Object objetoFinal = Criptografar.deserializarObjeto(resultado);
				listaExecucao.add((Execucao_formulario) objetoFinal);
				LogPrimum.mensagem("C�digo da execu��o:" + ((Execucao_formulario) objetoFinal).getCd_execucao_formulario() );
				LogPrimum.mensagem("Conclu�do");
			}
		} catch (HibernateException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException
				| InvalidAlgorithmParameterException | ClassNotFoundException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaExecucao;
	}
}
