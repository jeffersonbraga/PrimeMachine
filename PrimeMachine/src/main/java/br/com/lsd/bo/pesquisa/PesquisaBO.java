package br.com.lsd.bo.pesquisa;



import java.io.FileOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;










import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;









import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.campo_formulario.Campo_formulario;
import br.com.primum.modelo.categoria_ishikawa.Categoria_ishikawa;
import br.com.primum.modelo.classificacao_helpdesk.Classificacao_helpdesk;
import br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk;
import br.com.primum.modelo.dicionario.DicionarioBean;
import br.com.primum.modelo.documento.Documento;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.grupo_formulario.Grupo_formulario;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.notificacao_usuario.Notificacao_usuario;
import br.com.primum.modelo.ocorrencia_ouvidoria.Ocorrencia_ouvidoria;
import br.com.primum.modelo.periodicidade.Periodicidade;
import br.com.primum.modelo.permissao_acesso_documento.Permissao_acesso_documento;
import br.com.primum.modelo.resolucao_ocorrencia.Resolucao_ocorrencia;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.reflection.Reflection;
import br.com.primum.modelo.helpdesk_ocorrencia.Helpdesk_ocorrencia;
import br.com.primum.relatorio.modelo.formulario.resultPesquisaFormulario;

public class PesquisaBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<?> pesquisar(String datafield, String valor){
		this.setDao(new GenericDAO());
		
		//if(datafield!=null && valor!=null){
		//	List<Resolucao_ocorrencia> listaPesquisa = (List<Resolucao_ocorrencia>) this.getDao().getSession().createSQLQuery( "SELECT * FROM base_primum.resolucao_ocorrencia where st_ocorrencia_ouvidoria='2' order by dt_resolucao_ocorrencia ASC " ).addEntity(Resolucao_ocorrencia.class).list();
		//}
		
		//List<Resolucao_ocorrencia> lista = new ArrayList<>();
		//lista = this.getDao().getSession().createSQLQuery( "SELECT * FROM base_primum.resolucao_ocorrencia where st_resolucao_ocorrencia='3' order by dt_resolucao_ocorrencia ASC " ).list();
		List<Resolucao_ocorrencia> lista = this.getDao().getSession().createCriteria(Resolucao_ocorrencia.class).add(Restrictions.le("st_ocorrencia_ouvidoria", 1)).list();
		//List<Resolucao_ocorrencia> lista2 = (List<Resolucao_ocorrencia>) this.getDao().getSession().createSQLQuery( "SELECT * FROM resolucao_ocorrencia where st_ocorrencia_ouvidoria='2' order by dt_resolucao_ocorrencia ASC " ).addEntity(Resolucao_ocorrencia.class).list();
		List<Resolucao_ocorrencia> lista2 = (List<Resolucao_ocorrencia>) this.getDao().getSession().createCriteria(Resolucao_ocorrencia.class).add(Restrictions.eq("st_ocorrencia_ouvidoria", 2)).addOrder(Order.asc("dt_resolucao_ocorrencia")).list();
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		Integer soma;
		
		for( Resolucao_ocorrencia resolucao: lista){
			soma=1;
			pesquisa.buscarObjetoCompleto( resolucao );
			Ocorrencia_ouvidoria ocorrencia = (Ocorrencia_ouvidoria) this.getDao().getSession().createCriteria(Ocorrencia_ouvidoria.class).add(Restrictions.eq("cd_ocorrencia_ouvidoria", resolucao.getCd_ocorrencia_ouvidoria())).uniqueResult();
			resolucao.setOcorrencia_ouvidoria(ocorrencia);
			
//			if(resolucao.getOcorrencia_ouvidoria().getVl_gravidade()!=null)
//				soma = resolucao.getOcorrencia_ouvidoria().getVl_gravidade();
//			
//			if(resolucao.getOcorrencia_ouvidoria().getVl_urgencia()!=null)
//				soma = soma * resolucao.getOcorrencia_ouvidoria().getVl_urgencia();
//						
//			if(resolucao.getOcorrencia_ouvidoria().getVl_tendencia()!=null)
//				soma = soma * resolucao.getOcorrencia_ouvidoria().getVl_tendencia();
//			
//			resolucao.setVl_matrizGUT(soma);
			resolucao.setVl_matrizgut(resolucao.getOcorrencia_ouvidoria().getVl_matrizgut());
		}
		
		
//		Collections.sort(lista, new Comparator<Resolucao_ocorrencia>() {
//			@Override
//			public int compare(Resolucao_ocorrencia o1, Resolucao_ocorrencia o2) {
//				return o1.getVl_matrizGUT() < o2.getVl_matrizGUT() ? -1 : (o1.getVl_matrizGUT() > o2.getVl_matrizGUT() ? +1 : 0);
//			}
//			
//		});
//		
//		Collections.reverse(lista);
		
		for(int i=0; i< lista2.size();i++){
			lista.add(lista2.get(i));
		}
		
		
		return lista;
	}

	
	@SuppressWarnings("unchecked")
	public Boolean criarBeans(String nm_tabela){
		this.setDao(new GenericDAO());
		String sql_nome="select attname "+
		"from pg_attribute "+
		"where "+
		"    attstattarget = -1 and "+ 
		"   attrelid = ( "+
		"       select pg_class.oid as table_id "+ 
		"       from pg_class "+
		"       left join pg_namespace on pg_class.relnamespace = pg_namespace.oid "+ 
		"       where "+
		"          pg_class.relname = '"+ nm_tabela + "' and "+ 
		"          pg_namespace.nspname = 'public') ";
		
		String sql_tipo="select attlen "+
				"from pg_attribute "+
				"where "+
				"    attstattarget = -1 and "+ 
				"   attrelid = ( "+
				"       select pg_class.oid as table_id "+ 
				"       from pg_class "+
				"       left join pg_namespace on pg_class.relnamespace = pg_namespace.oid "+ 
				"       where "+
				"          pg_class.relname = '"+ nm_tabela + "' and "+ 
				"          pg_namespace.nspname = 'public') ";
		
		//List<String> listaDeModulos = (List<String>) this.getDao().getSession().createSQLQuery( sql ).setResultTransformer(Transformers.aliasToBean(String.class)).list();
		List<String> listaNomeCampos = (List<String>) this.getDao().getSession().createSQLQuery( sql_nome ).list();
		List<Object> listaTipoCampos = (List<Object>) this.getDao().getSession().createSQLQuery( sql_tipo ).list();
		
		for(String campo: listaNomeCampos){
			System.out.println("campo: " + campo );
		}
		
		for(Object o: listaTipoCampos){
			System.out.println("tipo: " + o.toString() );
		}
		
		String strClasse = "package br.com.lsd.modelo."+nm_tabela+";\n" +
						   "\n" +
						   "import javax.persistence.Column;\n" +
							"import javax.persistence.Entity;\n" +
							"import javax.persistence.Id;\n" +
							"import javax.persistence.Table;\n" +
							"import javax.persistence.Transient;\n" +
							"\n" + 
							"\n" +
							"@Entity @org.hibernate.annotations.Entity(mutable=true)\n"+
							"@Table(name='"+nm_tabela+"')\n"+
							"public class "+ (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase() +"{\n"+
							"\n"+
							"\t@Id\n";
		
		for(Integer e = 0; e< listaNomeCampos.size() ; e++){
			strClasse+="\t@Column\n";
			if(listaTipoCampos.get(e).toString().equals("4")){
				strClasse+= "\tprivate Integer "+listaNomeCampos.get(e).toString()+";\n\n";
			}else if(listaTipoCampos.get(e).toString().equals("8")){
				strClasse+= "\tprivate Date "+listaNomeCampos.get(e).toString()+";\n\n";
			}else if(listaTipoCampos.get(e).toString().equals("-1")){
				strClasse+= "\tprivate String "+listaNomeCampos.get(e).toString()+";\n\n";
			}
		}
		
		for(Integer i = 0; i< listaTipoCampos.size() ; i++){
			
			if(listaTipoCampos.get(i).toString().equals("4")){
				
				strClasse+="\n\tpublic ";
				strClasse += "Integer get"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(){\n";
				strClasse += "\t\treturn "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
				strClasse+="\n\tpublic void set"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(Integer "+listaNomeCampos.get(i)+"){\n";
				strClasse+="\t\tthis."+listaNomeCampos.get(i)+" = "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
			}else if(listaTipoCampos.get(i).toString().equals("8")){
				
				strClasse+="\n\tpublic ";
				strClasse += "Date get"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(){\n";
				strClasse += "\t\treturn "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
				strClasse+="\n\tpublic void set"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(Date "+listaNomeCampos.get(i)+"){\n";
				strClasse+="\t\tthis."+listaNomeCampos.get(i)+" = "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
			}else if(listaTipoCampos.get(i).toString().equals("-1")){
				
				strClasse+="\n\tpublic ";
				strClasse += "String get"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(){\n";
				strClasse += "\t\treturn "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
				strClasse+="\n\tpublic void set"+(String) listaNomeCampos.get(i).substring(0,1).toUpperCase()+""+ (String) listaNomeCampos.get(i).substring(1).toLowerCase() +"(String "+listaNomeCampos.get(i)+"){\n";
				strClasse+="\t\tthis."+listaNomeCampos.get(i)+" = "+ listaNomeCampos.get(i)+";\n\t}\n\n";
				
			}
			
		}
		
		strClasse+="}";
		
		try {  
            // Gravando no arquivo  
            File arquivo = new File("C://Users//Usuario//Desktop//"+(String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase()+".java"); 
            FileOutputStream fos = new FileOutputStream(arquivo);  
            fos.write(strClasse.getBytes());  
            fos.close();  
  
            // Lendo do arquivo  
//            arquivo = new File("arquivo.txt");  
//            FileInputStream fis = new FileInputStream(arquivo);  
//  
//            int ln;  
//            while ( (ln = fis.read()) != -1 ) {  
//                System.out.print( (char)ln );  
//            }  
  
    //        fis.close();  
        }  
        catch (Exception ee) {  
            ee.printStackTrace();  
        }  
		
		
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer verificaNroSequencial(){
		this.setDao(new GenericDAO());
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class).addOrder(Order.desc("cd_documento")).list();
		
		if(listaDoc.size()>0)
			return listaDoc.get(0).getCd_documento() +1;
		else
			return 1;
	}
	
	public Integer verificaNroSequencialParametros(String chavePrimaria, String tabela){
		
		this.setDao(new GenericDAO());
		Integer codigo = (Integer) this.getDao().getSession().createSQLQuery("SELECT max("+chavePrimaria+") FROM "+ tabela+"").uniqueResult();
		
	    if(codigo != null ){
			return codigo+1;
		}
		return 1;
	}
	
	
	@SuppressWarnings("unchecked")
	public Integer getNumDocumento(Integer tp_documento, Integer tp_processo){
		
		this.setDao(new GenericDAO());
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class).add(Restrictions.eq("tp_documento", tp_documento)).add(Restrictions.eq("tp_processo", tp_processo)).list();
		
		if(listaDoc.size()>0)
			return listaDoc.size()+1;
		
		return 1;
	}
	
	
	public Integer mudaRevisao(Integer numRevisao, Integer revisaoAtual){
		this.setDao(new GenericDAO());
		if(!numRevisao.equals(revisaoAtual))
			revisaoAtual = revisaoAtual+1;
		
		return revisaoAtual;
	}
	
	@SuppressWarnings("unchecked")
	public Integer verificaExistencia(Integer tp_documento, Integer tp_processo, Integer nr_sequencial){
		this.setDao(new GenericDAO());
		
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class)
				.add(Restrictions.eq("tp_documento", tp_documento))
				.add(Restrictions.eq("tp_processo", tp_processo))
				.add(Restrictions.eq("nr_sequencial", nr_sequencial)).addOrder(Order.desc("nr_revisao")).list();
		
		
		if(listaDoc.size()>0)
			return listaDoc.get(0).getNr_revisao()+1;
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List<Documento> getListaDocumentos(Integer cd_usuario){
		
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class).list();
		List<Documento> listaDocAux = new ArrayList<>(); 
		
		for(Documento doc:listaDoc){
			
			pesquisa.buscarObjetoCompleto(doc);
			
			if(doc.getTp_disponibilidade().equals(0)){
				listaDocAux.add(doc);
			}else{
				
				if(doc.getSetor()!=null){
					if(doc.getSetor().getCd_responsavel_setor().equals(cd_usuario)){
						//SE SITUA��O � REPROVADA
						//if(doc.getSt_documento().equals(4)){
							listaDocAux.add(doc);
						//}
					}
				}
				
				//SE SITUACAO FOR AGUARDANDO QUALIDADE E PESSOA FOR RESPONSAVEL
				if( doc.getSt_documento().equals(0)){
					Usuario u = (Usuario) this.getDao().getSession().createCriteria(Usuario.class).add(Restrictions.eq("cd_usuario", cd_usuario)).uniqueResult();
					if(u.getCd_usuario().equals( cd_usuario ) ){
						listaDocAux.add(doc);
					}
				}
				
				//SE FOR REPROVADO E � O CARA QUE CRIOU
				if( doc.getSt_documento().equals(4) && doc.getCd_responsavel_criar().equals(cd_usuario)){
					listaDocAux.add(doc);
				}
				
				if(doc.getListaPermissoes()!=null){
					if(doc.getNotificacao()!=null){
						if(doc.getNotificacao().getLista_notificacao_usuario()!=null){

							boolean encontrou = false;
							for(Permissao_acesso_documento permissao:doc.getListaPermissoes()){
								if(permissao!=null && permissao.getCd_colaborador().equals(cd_usuario)){
									listaDocAux.add(doc);
									encontrou = true;
									break;
								}
							}

							if (!encontrou) {
								for(Notificacao_usuario notify : doc.getNotificacao().getLista_notificacao_usuario() ){
									if(notify!=null && notify.getCd_usuario().equals(cd_usuario) ){
										if( notify.getSt_notificar().equals(doc.getSt_documento() ) ){
											listaDocAux.add(doc);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return listaDocAux;
	}
	
	@SuppressWarnings("unchecked")
	public List<List<?>> getListaDocumentosListaMestra(){

		this.setDao(new GenericDAO());
		List<Documento> listaDoc = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class)
				.addOrder(Order.desc("cd_documento"))
				.list();
		
		List<List<?>> listaAux = new ArrayList<>();
		List<List<?>> listaAux2 = new ArrayList<>();
		
		List<Object> listaCodFilhos = new ArrayList<>();
		
		
		for(int i=0; i< listaDoc.size();i++){
			List<Documento> listaDocAux = (List<Documento>) this.getDao().getSession().createCriteria(Documento.class)
					.add(Restrictions.eq("tp_documento", listaDoc.get(i).getTp_documento()))
					.add(Restrictions.eq("nr_sequencial", listaDoc.get(i).getNr_sequencial()))
					.add(Restrictions.ne("cd_documento", listaDoc.get(i).getCd_documento()))
					.list();

			Boolean existeNaListaDeFilhos=false;
			for(Object o: listaCodFilhos){
				if(listaDoc.get(i).getCd_documento().equals(o)){
					existeNaListaDeFilhos=true;
					break;
				}
			}
			
			if(listaDocAux!=null && listaDocAux.size()>0){
				
				if(!existeNaListaDeFilhos){
					
					for(Documento doc: listaDocAux){
						listaCodFilhos.add( doc.getCd_documento() );
					}
					
					listaAux2.add(listaDocAux);
				}else{
					listaAux2.add(new ArrayList<Documento>());
				}
			}else{
				listaAux2.add(new ArrayList<Documento>());
			}
		}
		
		listaAux.add(listaDoc);
		listaAux.add(listaAux2);
		
		return listaAux;
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Helpdesk_ocorrencia> getListaHelpDesksAbertos(Integer cd_usuario){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Helpdesk_ocorrencia> listaOcorrencias = new ArrayList<>();
		listaOcorrencias = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.eq("cd_responsavel_registro", cd_usuario ))
				.add(Restrictions.ne("cd_classificacao_helpdesk", 1))
				.add(Restrictions.ne("cd_classificacao_helpdesk", 2))
				.add(Restrictions.isNull("tp_parecer_reclamante"))
				.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
				.list();

		//.add(Restrictions.ge("st_helpdesk_ocorrencia", 4 ))
		
		for(Helpdesk_ocorrencia hd: listaOcorrencias){
			pesquisa.buscarObjetoCompleto(hd);
		}
		
		return listaOcorrencias;
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	public List<Helpdesk_ocorrencia> getListaHelpDesks(Integer cd_usuario, Date dt_inicial, Date dt_final, Integer cd_unidade, Integer st_hd, Boolean buscarComFiltro){

		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Helpdesk_ocorrencia> listaOcorrenciasAux = new ArrayList<>();
		List<Helpdesk_ocorrencia> listaOcorrencias = new ArrayList<>();
		
		GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
		dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
		dataInicialDia.set(Calendar.MINUTE,0);     
		dataInicialDia.set(Calendar.SECOND,0);     
		
		GregorianCalendar dataFinalDia = (GregorianCalendar) GregorianCalendar.getInstance();
		dataFinalDia.set(Calendar.HOUR_OF_DAY,23);     
		dataFinalDia.set(Calendar.MINUTE,59);     
		dataFinalDia.set(Calendar.SECOND,59); 
		
		
		if(cd_usuario!=null){
			
			List<Configuracao_helpdesk> listaConfiguracoesHelpdesk =  (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
					.add(Restrictions.eq("cd_colaborador", cd_usuario))
					.list();
			
			if( listaConfiguracoesHelpdesk != null){
				for(Configuracao_helpdesk conf: listaConfiguracoesHelpdesk){
					
					Criteria c = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
					if(conf.getCd_natureza_ocorrencia() != null){
						c.add( Restrictions.eq("cd_natureza_ocorrencia", conf.getCd_natureza_ocorrencia() ));
					}
					
					if(buscarComFiltro){
						if(dt_inicial==null){
							dt_final = new Date();
						}
						if(dt_final==null){
							dt_final = new Date();
						}
						
						dataInicialDia.set(Calendar.DATE, dt_final.getDate());
						dataFinalDia.set(Calendar.DATE, dt_final.getDate());
						
						if(cd_unidade!=null){
							if(st_hd!=null){
								
								listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
										.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
										.add(Restrictions.eq("cd_unidade_registro", cd_unidade))
										.add(Restrictions.eq("st_helpdesk_ocorrencia", st_hd))
										.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
										.list();
							}else{
								listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
										.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
										.add(Restrictions.eq("cd_unidade_registro", cd_unidade))
										.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
										.list();
							}
						}else{
							if(st_hd!=null){
								listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
										.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
										.add(Restrictions.eq("st_helpdesk_ocorrencia", st_hd))
										.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
										.list();
							}else{
								listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
										.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
										.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
										.list();
							}
						}
					}else{
						if(!conf.getCd_classificacao_helpdesk().equals(1) && !conf.getCd_classificacao_helpdesk().equals(2) ){
							
							listaOcorrencias =  (List<Helpdesk_ocorrencia>) c.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
									.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
									.list();
						}
					}
					
					if( listaOcorrencias != null){
						for(Integer i = 0 ; i< listaOcorrencias.size() ; i++){
							//pesquisa.buscarObjetoCompleto(hd);
							listaOcorrenciasAux.add(listaOcorrencias.get(i));
						}
					}
				}
			}
		}else{
			Criteria c = this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class);
			if(buscarComFiltro){
				if(dt_inicial==null){
					dt_final = new Date();
				}
				if(dt_final==null){
					dt_final = new Date();
				}
				
				dataInicialDia.set(Calendar.DATE, dt_final.getDate());
				dataFinalDia.set(Calendar.DATE, dt_final.getDate());
				
				if(cd_unidade!=null){
					if(st_hd!=null){
						listaOcorrencias = c.add(Restrictions.between("dt_criacao", dt_inicial, dt_final))
								.add(Restrictions.eq("cd_unidade_registro", cd_unidade))
								.add(Restrictions.eq("st_helpdesk_ocorrencia", st_hd))
								.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
								.list();
					}else{
						listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
								.add(Restrictions.eq("cd_unidade_registro", cd_unidade))
								.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
								.list();
					}
				}else{
					if(st_hd!=null){
						listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
								.add(Restrictions.eq("st_helpdesk_ocorrencia", st_hd))
								.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
								.list();
					}else{
						listaOcorrencias = (List<Helpdesk_ocorrencia>) c.add(Restrictions.between("dt_criacao", dataInicialDia.getTime(), dataFinalDia.getTime()))
								.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
								.list();
					}
				}
			}else{
				listaOcorrencias = (List<Helpdesk_ocorrencia>) c.addOrder(Order.desc("cd_helpdesk_ocorrencia")).list();
			}
			
			if( listaOcorrencias != null){
				for(Integer i = 0 ; i< listaOcorrencias.size() ; i++){
					//pesquisa.buscarObjetoCompleto(hd);
					listaOcorrenciasAux.add(listaOcorrencias.get(i));
				}
			}
			
		}
		
		
		/************************************************/
		/*** PARA REGISTROS DE AMBIENTAL OU QUALIDADE ***/
		
		List<Helpdesk_ocorrencia> listaHelpdesk =  ( List<Helpdesk_ocorrencia> ) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("cd_classificacao_helpdesk", 2))
				.add(Restrictions.le("st_helpdesk_ocorrencia", 1))
				.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
				.list();
				
		if(listaHelpdesk!=null){
			for(Helpdesk_ocorrencia h : listaHelpdesk ){
				
				if(h.getCd_setor()!=null){
					Setor s = (Setor) getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_setor", h.getCd_setor())).uniqueResult();
					if(s != null){
						if( s.getCd_responsavel_setor() != null ){
							if( s.getCd_responsavel_setor().equals( cd_usuario ) ){
								//pesquisa.buscarObjetoCompleto( h );
								listaOcorrenciasAux.add( h );
							}
						}
					}
				}else{
					Unidade u = (Unidade) getDao().getSession().createCriteria(Unidade.class).add(Restrictions.eq("cd_unidade", h.getCd_unidade())).uniqueResult();
					if(u != null){
						if( u.getCd_responsavel_unidade() != null ){
							if( u.getCd_responsavel_unidade().equals( cd_usuario ) ){
								//pesquisa.buscarObjetoCompleto( h );
								listaOcorrenciasAux.add( h );
							}
						}
					}
				}
			}
		}
		Collections.sort(listaOcorrenciasAux, new Comparator() {
			public int compare(Object o1, Object o2) {  
                Helpdesk_ocorrencia h1 = (Helpdesk_ocorrencia) o1;  
                Helpdesk_ocorrencia h2 = (Helpdesk_ocorrencia) o2;  
                return h1.getCd_helpdesk_ocorrencia() < h2.getCd_helpdesk_ocorrencia() ? +1 : (h1.getCd_helpdesk_ocorrencia() > h2.getCd_helpdesk_ocorrencia()? -1 : 0);  
            }	
		}); 
		return listaOcorrenciasAux;
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Helpdesk_ocorrencia> getListaHelpDesksAnalise(Integer cd_usuario){

		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		
		List<Helpdesk_ocorrencia> listaHDs = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.eq("cd_resp_resolucao", cd_usuario))
				.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
				.list();
		
		List<Helpdesk_ocorrencia> listaHDsFinal = new ArrayList<>();
		
		for(Helpdesk_ocorrencia hd: listaHDs){
			
			if( !hd.getCd_classificacao_helpdesk().equals(1) &&
					!hd.getCd_classificacao_helpdesk().equals(2) ){
				
				pesquisa.buscarObjetoCompleto(hd);
				listaHDsFinal.add( hd );
			}else{
				if(hd.getSt_helpdesk_ocorrencia().equals(5) || 
						hd.getSt_helpdesk_ocorrencia().equals(6) ){
					pesquisa.buscarObjetoCompleto(hd);
					listaHDsFinal.add( hd );
				}
				
			}
		}
		
		/************************************************/
		/*** PARA REGISTROS DE AMBIENTAL OU QUALIDADE ***/
		List<Helpdesk_ocorrencia> listaHelpdeskAmbientalQualidade =  ( List<Helpdesk_ocorrencia> ) getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
				.add(Restrictions.le("cd_classificacao_helpdesk", 2))
				.addOrder(Order.desc("cd_helpdesk_ocorrencia"))
				.list();
		
		if(listaHelpdeskAmbientalQualidade!=null){
			for(Helpdesk_ocorrencia h : listaHelpdeskAmbientalQualidade ){
				
				List<Configuracao_helpdesk> listaConfiguracaoHelpdesk =  ( List<Configuracao_helpdesk> ) getDao().getSession().createCriteria(Configuracao_helpdesk.class)
						.add(Restrictions.eq("cd_classificacao_helpdesk", h.getCd_classificacao_helpdesk())).list();
				for(Configuracao_helpdesk conf : listaConfiguracaoHelpdesk ){
					if( conf.getCd_colaborador().equals( cd_usuario )){
						pesquisa.buscarObjetoCompleto( h );
						listaHDsFinal.add( h );
					}
				}
			}
		}
		
		
		return listaHDsFinal;
		
//		List<Helpdesk_ocorrencia> listaOcorrenciasAux = new ArrayList<>();
//		
//		List<Configuracao_helpdesk> listaConfiguracoes = (List<Configuracao_helpdesk>) this.getDao().getSession().createCriteria(Configuracao_helpdesk.class)
//				.add(Restrictions.eq("cd_colaborador", cd_usuario)).list();
//		
//		for(Configuracao_helpdesk conf: listaConfiguracoes){
//			List<Helpdesk_ocorrencia> listaOcorrencias = (List<Helpdesk_ocorrencia>) this.getDao().getSession().createCriteria(Helpdesk_ocorrencia.class)
//					.add(Restrictions.eq("cd_classificacao_helpdesk", conf.getCd_classificacao_helpdesk() ))
//					.list();
//		
//			for(Helpdesk_ocorrencia hd: listaOcorrencias){
//				pesquisa.buscarObjetoCompleto(hd);
//				listaOcorrenciasAux.add(hd);
//			}
//		
//		}
//		return listaOcorrenciasAux;
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Categoria_ishikawa> getCategoriasIshikawa(){
		
		this.setDao(new GenericDAO());
		List<Categoria_ishikawa> listaCategorias = (List<Categoria_ishikawa>) this.getDao().getSession().createCriteria(Categoria_ishikawa.class).list();
		return listaCategorias;
		
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Periodicidade> getPeriodicidadePorSituacao(){
		this.setDao(new GenericDAO());
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		List<Periodicidade> listaPeriodicidade = (List<Periodicidade>) this.getDao().getSession().createCriteria(Periodicidade.class)
				.add(Restrictions.eq("tp_periodicidade", 1))
				.addOrder(Order.asc("cd_periodicidade"))
				.list();
		
		for(Periodicidade p : listaPeriodicidade){
			pesquisa.buscarObjetoCompleto(p);
		}
		
		return listaPeriodicidade;
	}
	
	@SuppressWarnings({"unchecked" })
	public List<?> getListaObjetos(String ds_caminhoclasse, Boolean buscarCompleto, String campoOrdenar, Integer tipoOrdenacao) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		//tipoOrdenacao = 0 ASC 
		//tipoOrdenacao = 1 DESC
		
		
		this.setDao(new GenericDAO());
		String classe = ds_caminhoclasse;
		Object obj = Class.forName(classe).newInstance();
		List<Object> lst;
		
		if(campoOrdenar!=null && campoOrdenar != ""){
			if(tipoOrdenacao != null ){
				if(tipoOrdenacao.equals(0)){
					lst = this.getDao().getSession().createCriteria(obj.getClass()).addOrder(Order.asc(campoOrdenar)).list();
				}else{
					lst = this.getDao().getSession().createCriteria(obj.getClass()).addOrder(Order.desc(campoOrdenar)).list();
				}
			}else{
				lst = this.getDao().getSession().createCriteria(obj.getClass()).list();
			}
		}else{
			lst = this.getDao().getSession().createCriteria(obj.getClass()).list();
		}
		 
		
		if(buscarCompleto){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			for(Object objetoLista: lst){
				pesquisa.buscarObjetoCompleto(objetoLista);
			}
		}
		
		return lst;
		
	}
	
	@SuppressWarnings({"unchecked" })
	public Object getObjeto(String ds_caminhoclasse, Boolean buscarCompleto, Integer valorBuscar) throws InstantiationException, IllegalAccessException, ClassNotFoundException{

		this.setDao(new GenericDAO());
		String classe = ds_caminhoclasse;
		Object obj = Class.forName(classe).newInstance();
		try {
			//Reflection.setValorChavePrimaria(obj, valorBuscar);
			obj = this.getDao().getSession().createCriteria(obj.getClass()).add(Restrictions.eq(Reflection.getNomeChavePrimaria(obj), valorBuscar)).uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(buscarCompleto){
			PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
			pesquisa.setDao(this.getDao());
			pesquisa.buscarObjetoCompleto(obj);
		}

		return obj;
	}

	@SuppressWarnings({"unchecked" })
	public List<Object> getObjetoByTabelaEID(Integer cd_tabela, Integer cd_obj){

		this.setDao(new GenericDAO());
		Tabela table = (Tabela) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("cd_tabela", cd_tabela )).uniqueResult();
		if ( (table != null) ) {

			String classe = table.getDs_caminhoclasse();
			Object obj;

			try {
				obj = Class.forName(classe).newInstance();

				List<Object> lst = new ArrayList<>();

				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					if(field.isAnnotationPresent(Id.class)){
						lst = this.getDao().getSession().createCriteria(obj.getClass())
								.add(Restrictions.eq(field.getName(), cd_obj))
								.list();
						break;
					}
				}
				return lst;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Setor> pesquisaServiceSetor(Integer cd_unidade) {

		this.setDao(new GenericDAO());
		List<?> listaSetores = this.getDao().getSession().createCriteria(Setor.class).add(Restrictions.eq("cd_unidade", cd_unidade)).list();

		return (List<Setor>) listaSetores;
	}

	public List<?> getListaTabelasNotificacao() throws Exception {

		this.setDao(new GenericDAO());
		List<?> lista = this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("tp_conf_notificacao", 1)).list();
		return lista;
	}

	public Campo_formulario buscarCampoIdentificador(Formulario formulario) {

		try {

			CrudGenericBO crud = new CrudGenericBO();
			crud.buscarObjetoCompleto(formulario);

			for (Grupo_formulario grupo : formulario.getListaGrupo_formulario()) {
				for (Campo_formulario campo : grupo.getListaCampo_formulario()) {
					if (campo.getTp_identificador() != null && campo.getTp_identificador().equals(1)) {
						return campo;
					}
				}
			}

			return null;			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}