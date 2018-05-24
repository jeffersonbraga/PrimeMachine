package br.com.primum.bo.treinamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;

//import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.participantes_treinamento.Participantes_treinamento;
import br.com.primum.modelo.registro_treinamento.Registro_treinamento;


public class TreinamentoBO {
	
	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	public List<byte[]> buscarListaArquivosData(Integer cod, String pasta){
		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO +pasta+"/"+cod+""+pasta.toUpperCase());
		List<byte[]> lista = new  ArrayList<>();
		
		
		
		if(diretorio.exists()){
			for(int i = 0 ; i<diretorio.list().length;i++ ){
				
				/****/
				byte []buffer = new byte[(int) diretorio.listFiles()[i].length()];
			    InputStream ios = null;
			    try {
			        try {
						ios = new FileInputStream(diretorio.listFiles()[i]);
						try {
							if ( ios.read(buffer) == -1 ) {
								throw new IOException("EOF reached while trying to read the whole file");
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
			                
			    } finally { 
			        try {
			             if ( ios != null ) 
			                  ios.close();
			        } catch ( IOException e) {
			        }
			    }
				/****/
			    lista.add( buffer );
				//lista.addAll( (List<byte[]>) diretorio.listFiles()[i] );
			}
		}
		
		return lista;
	}
	
	public List<?> buscarListaArquivos(Integer cod, String pasta){
		
		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta+"/"+cod+""+pasta.toUpperCase());
		List<String> lista = new  ArrayList<>();
		
		if(diretorio.exists()){
			for(int i = 0 ; i<diretorio.list().length;i++ ){
				lista.add((String) diretorio.list()[i] );
			}
		}
		
		return lista;
	}
	
	public Boolean salvarArquivo(List<byte[]> lista,  List<String> listaNomes, Integer codigo , String pasta) {
		
		String strNome;
		String strFilePath;
		
		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta );
		if(!diretorio.exists())
			diretorio.mkdir();
		
		
		File subDiretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta+"/"+codigo+""+pasta.toUpperCase() );
		
		if(!subDiretorio.exists())
			subDiretorio.mkdir();
		
		//********************************
		//ROTINA QUE EXCLUI TODOS DOCUMENTOS QUE NAO ESTAO LISTADOS NO BANCO
		//for(int e = 0 ; e < diretorio.list().length ;e++){
		//	Boolean existe=false;
		//	for(int i = 0 ; i < lista.size(); i++){
		//		if((String)diretorio.list()[e] == listaNomes.get(i)){
		//			existe=true;
		//			break;
		//		}
		//	}
		//	
		//	if(!existe)
		//		this.excluirArquivo((String)diretorio.list()[e], codigo, pasta);
		//}
		//********************************
		
		for(int i = 0 ; i < lista.size(); i++){
			
			strNome = listaNomes.get(i);
			strFilePath = subDiretorio.getAbsolutePath(); 
					//"C:/jboss-4.2.3.GA/server/default/deploy/Builder.war/WEB-INF/descritivoTreinamento/"+codigo+"DescritivoTreinamento";
			
			try{
		    	FileOutputStream fos = new FileOutputStream(strFilePath+"/"+strNome);
		    	
		    	fos.write(lista.get(i));
		    	fos.close();
		    
		    	
		    }catch(FileNotFoundException ex){
		    	
		    	//LogPrimum.mensagem("FileNotFoundException : " + ex);
		    	return false;
		    	
		    }catch(IOException ioe){
		    	
		    	//LogPrimum.mensagem("IOException : " + ioe);
		    	return false;
		    	
		    }
		}
		
		return true;
	}
	
	public Boolean excluirArquivo(String nomeArquivo, Integer cd_chaveprimaria, String pasta){

		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta+"/"+cd_chaveprimaria+""+pasta.toUpperCase() );

		if(diretorio.exists()){
			File arquivo = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta+"/"+cd_chaveprimaria+""+pasta.toUpperCase() +"/"+(String) nomeArquivo);
			try {
				arquivo.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	public Boolean excluirPasta(Integer cd_chaveprimaria, String pasta){
		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO+pasta+"/"+cd_chaveprimaria+""+pasta.toUpperCase() );
		List<?> listaArquivos = this.buscarListaArquivos(cd_chaveprimaria, pasta);
		Boolean exclusao = true;

		for(int i = 0 ; i<listaArquivos.size();i++ ){
			exclusao = this.excluirArquivo((String) diretorio.list()[0], cd_chaveprimaria, pasta);
			if(!exclusao){
				break;
			}
		}

		if(exclusao){
			if(diretorio.exists()){
				try {
					diretorio.delete();
				}catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public byte[] returnByteArray(String fileName) throws Exception{
        byte[] data = null;
        try {
            //FileInputStream fis = new FileInputStream(System.getProperty("java.io.tmpdir") + fileName);
            File diretorio = new File(fileName);
            if(diretorio.exists()){
	        	FileInputStream fis = new FileInputStream( fileName );

	            FileChannel fc = fis.getChannel();
	            data = new byte[(int) (fc.size())];
	            ByteBuffer bb = ByteBuffer.wrap(data);
	            fc.read(bb);

	            return data;
            }else{
            	return null;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

	@SuppressWarnings("unchecked")
	public List<?> getListaTreinamentosPorPessoa(Integer cd_pessoa) throws Exception{

		this.setDao(new GenericDAO());
		List<Object> lista = new ArrayList<>();
		List<?> listaTreinamentos = this.getDao().getSession().createCriteria(Participantes_treinamento.class).add(Restrictions.eq("cd_pessoa", cd_pessoa)).list();
		List<Participantes_treinamento> listaDados 		= new ArrayList<>();
		List<Participantes_treinamento> listaAgendados 	= new ArrayList<>();

		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();

		for (Participantes_treinamento participacao : (List<Participantes_treinamento>) listaTreinamentos) {
			pesquisa.buscarObjetoCompleto(participacao);
			Registro_treinamento registro = (Registro_treinamento) this.getDao().getSession().createCriteria(Registro_treinamento.class).add(Restrictions.eq("cd_registro_treinamento", participacao.getCd_registro_treinamento())).uniqueResult();

			GregorianCalendar dataAtual = new GregorianCalendar();
			dataAtual.setTime(new Date());

			GregorianCalendar dataComparar = new GregorianCalendar();
			dataComparar.setTime(registro.getDt_termino());

			if (dataAtual.after(dataComparar)) {
				listaDados.add(participacao);
			} else if (dataAtual.before(dataComparar)) {
				listaAgendados.add(participacao);
			}

			Descritivo_treinamento registro_descritivo = (Descritivo_treinamento) this.getDao().getSession().createCriteria(Descritivo_treinamento.class).add(Restrictions.eq("cd_descritivo_treinamento", registro.getCd_descritivo_treinamento())).uniqueResult();

			participacao.setRegistro_treinamento(registro);
			participacao.setDescritivo_treinamento(registro_descritivo);
		}

		lista.add(listaDados);
		lista.add(listaAgendados);

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Formulario> getListaAtividades(Integer cd_profissao){
		this.setDao(new GenericDAO());			

		List<Formulario> listaAtividades = (List<Formulario>) this.getDao().getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_profissao", cd_profissao)).list();
		return listaAtividades;
	}

	public Boolean gravaCopiaControlada(byte[] dados, String nomeArquivo, String pasta){

		String strFilePath;
		File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO + pasta);

		if(!diretorio.exists())
			diretorio.mkdir();

		strFilePath = diretorio.getAbsolutePath(); 

		try{

			FileOutputStream fos = new FileOutputStream(strFilePath+"/"+nomeArquivo);

	    	fos.write(dados);
	    	fos.close();
	    }catch(FileNotFoundException ex){

	    	//LogPrimum.mensagem("FileNotFoundException : " + ex);
	    	return false;
	    }catch(IOException ioe){

	    	//LogPrimum.mensagem("IOException : " + ioe);
	    	return false;
	    }

		return true;
	}

	public Boolean gravaCopiaNaoControlada(byte[] dados, String nomeArquivo, String pasta){
		return gravaCopiaControlada(dados, nomeArquivo, pasta);
	}

	public Boolean gravaArquivo(byte[] dados, String nomeArquivo, String pasta){
		return gravaCopiaControlada(dados, nomeArquivo, pasta);
	}

	public Boolean excluiCopiaControlada(String nomeArquivo){
		File arquivo = new File((String) nomeArquivo);
		
		if(arquivo.exists()){
			try {
				arquivo.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean excluiCopiaNaoControlada(String nomeArquivo){
		return excluiCopiaControlada(nomeArquivo);
	}
	
}
