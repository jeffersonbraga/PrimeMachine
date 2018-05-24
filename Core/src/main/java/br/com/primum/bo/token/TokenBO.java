package br.com.primum.bo.token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.hibernate.HibernateSessionFactory;
import br.com.primum.modelo.cliente.Cliente;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

public class TokenBO {
    public FlexSession session;

	public void validarToken(String token) {
	}

	public boolean criarSchema(Cliente cliente) {

		try {

			{
				BufferedReader reader = new BufferedReader( new FileReader(ContextoAplicacao.CAMINHO_APLICACAO + "//primum//modelo_schema.primum") );
				String         line = null;
				StringBuilder  stringBuilder = new StringBuilder();
				String         ls = System.getProperty("line.separator");
	
				while( ( line = reader.readLine() ) != null ) {
	
					line = line.replaceAll("nomeSchema", cliente.getNm_schema().toLowerCase());
				    stringBuilder.append( line );
				    stringBuilder.append( ls );
				}   

				FileOutputStream fos = new FileOutputStream(ContextoAplicacao.CAMINHO_APLICACAO + "//primum//novoSchema.primum");
				fos.write(stringBuilder.toString().getBytes());
		    	fos.close();
		    	fos.flush();

				{

		    		final Process p = Runtime.getRuntime().exec("C:\\Program Files\\PostgreSQL\\9.2\\bin\\psql.exe -U postgres -d checklist -f "+ ContextoAplicacao.CAMINHO_APLICACAO + "primum//novoSchema.primum -n");
				}
			}

			{
				BufferedReader reader = new BufferedReader( new FileReader(ContextoAplicacao.CAMINHO_APLICACAO + "//primum//insertNovoSchema.primum") );
				String         line = null;
				StringBuilder  stringBuilder = new StringBuilder();
				String         ls = System.getProperty("line.separator");

				while( ( line = reader.readLine() ) != null ) {

					line = line.replaceAll("nomeSchema", cliente.getNm_schema().toLowerCase());
				    stringBuilder.append( line );
				    stringBuilder.append( ls );
				}

				FileOutputStream fos = new FileOutputStream(ContextoAplicacao.CAMINHO_APLICACAO + "//primum//novoSchemaInsert.primum");
				System.out.println(stringBuilder.toString());
				fos.write(stringBuilder.toString().getBytes());
		    	fos.close();
		    	fos.flush();
			}

			try {
				this.backingUp(cliente.getNm_schema().toLowerCase());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public static void backingUp(String schema) throws IOException, InterruptedException{      

	}
}