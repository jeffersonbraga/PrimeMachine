package br.com.primum.bo.criar_bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.postgres.PostgresqlDTO;
import br.com.primum.modelo.tabela.Tabela;

public class Criar_beanBO {

	private GenericDAO dao;

	public GenericDAO getDao() {
		return dao; 
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public boolean Criar_arquivos(String nm_tabela) throws Exception {

		this.setDao(new GenericDAO());
		String sql = "SELECT attname as \"nm_coluna\", pg_type.typname as \"tp_campo\", cast(pg_attribute.attnum as VARCHAR(2)) as \"nr_indice_coluna\" FROM pg_attribute, pg_class, pg_type WHERE attrelid = pg_class.oid AND pg_attribute.attisdropped = False AND relname = '" + nm_tabela + "' AND attnum > 0 AND atttypid = pg_type.oid";
		//String sql = "SELECT COLUMN_NAME as \"nm_coluna\", DATA_TYPE as \"tp_campo\", cast(column_id as varchar(2)) as \"nr_indice_coluna\" FROM USER_TAB_COLUMNS WHERE table_name = '" + nm_tabela + "'";

		//List<String> listaDeModulos 	= (List<String>) this.getDao().getSession().createSQLQuery( sql ).setResultTransformer(Transformers.aliasToBean(String.class)).list();
		//List<String> listaNomeCampos 	= (List<String>) this.getDao().getSession().createSQLQuery( sql_nome ).list();
		//List<Object> listaTipoCampos 	= (List<Object>) this.getDao().getSession().createSQLQuery( sql_tipo ).list();
		List<Object> listaDadosCampos 	= (List<Object>) this.getDao().findBySql(sql, new PostgresqlDTO());

		String strClasseJava 		= this.criar_java(nm_tabela, listaDadosCampos);
		//String strClasseJava 		= this.criar_javaMobile(nm_tabela, listaNomeCampos, listaTipoCampos);
		//String strClasseFlex = this.criar_flex(nm_tabela, listaNomeCampos, listaTipoCampos);

		try {  

			File diretorioJava = new File("D:\\Projetos\\Bean_factory\\modelo\\" 	+ nm_tabela);
			if(!diretorioJava.exists())
				diretorioJava.mkdir();

			/*String strFilePath;
			File diretorioFlex = new File("D:\\Projetos\\Bean_factory\\modelo\\" 		+ nm_tabela);

			if(!diretorioFlex.exists())
				diretorioFlex.mkdir();
			*/
            // Gravando no arquivo  
            File arquivoJava = new File("D:\\Projetos\\Bean_factory\\modelo\\" 	+ nm_tabela + "\\" + (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase()+".java");
            //File arquivoFlex = new File("D:\\Projetos\\Bean_factory\\modelo\\" 		+ nm_tabela + "\\" + (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase()+".as");
            //File arquivoFlex = new File("C:\\Users\\Usuario\\Desktop "  		+ nm_tabela + "\\" + (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase()+"Aux.as");
            
            System.out.println("LOL");
            
            FileOutputStream fosJava = new FileOutputStream(arquivoJava);
            //FileOutputStream fosFlex = new FileOutputStream(arquivoFlex);

            fosJava.write(strClasseJava.getBytes());  
            fosJava.close();

            /*fosFlex.write(strClasseFlex.getBytes());  
            fosFlex.close();*/
        }  
        catch (Exception ee) {  
            ee.printStackTrace();
    		return false;  
        }
		return true;
	}

	private String criar_java(String nm_tabela, List<?> listaDadosCampos) {

		String strClasse = "package br.com.primum.modelo."+nm_tabela+";\n" +
						   "\n" +
						   "import java.io.Serializable;\n" +
						   "import javax.persistence.Column;\n" +
							"import javax.persistence.Entity;\n" +
							"import javax.persistence.Id;\n" +
							"import javax.persistence.Table;\n" +
							"import javax.persistence.Transient;\n" +
							"import java.util.Date;\n" +
							"\n" + 
							"\n" + 
							"@Entity\n"+
							"@Table(name=\""+nm_tabela+"\")\n"+
							"public class "+ (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase() +"  implements Serializable {\n"+
							"\n"
							+ "\t@Transient\n"
							+ "\tprivate static final long serialVersionUID = 1L;\n";

		String strClasse_getSetter = "";
		for(PostgresqlDTO p : ((List<PostgresqlDTO>) listaDadosCampos)){

			if ( p.getNr_indice_coluna().equals("1")) {
				strClasse += "\t@Id\n";
			}
			String tipo_campo = "";

			if(p.getTp_campo().toLowerCase().indexOf("number") > -1){
				tipo_campo 	= "Integer";
				strClasse	+= "\t@Column\n\tprivate Integer "+ p.getNm_coluna() +";\n\n";
			}else if(p.getTp_campo().toLowerCase().indexOf("date") > -1){
				tipo_campo 	= "Date";
				strClasse	+= "\t@Column\n\tprivate Date "+ p.getNm_coluna() +";\n\n";
			}else if(p.getTp_campo().toLowerCase().indexOf("varchar") > -1){
				tipo_campo 	= "String";
				strClasse	+= "\t@Column\n\tprivate String "+ p.getNm_coluna() +";\n\n";
			}

			if (tipo_campo != "") {
				strClasse_getSetter += "\n\tpublic ";
				strClasse_getSetter += "" + tipo_campo + " get"+ p.getNm_coluna().substring(0,1).toUpperCase()+""+ p.getNm_coluna().substring(1).toLowerCase() +"(){\n";
				strClasse_getSetter += "\t\treturn "+ p.getNm_coluna() +";\n\t}\n\n";

				strClasse_getSetter += "\n\tpublic void set"+ p.getNm_coluna().substring(0,1).toUpperCase()+""+ p.getNm_coluna().substring(1).toLowerCase() +"(" + tipo_campo + " " + p.getNm_coluna() +"){\n";
				strClasse_getSetter += "\t\tthis."+p.getNm_coluna()+" = "+ p.getNm_coluna()+";\n\t}\n\n";
			}
		}

		strClasse += strClasse_getSetter;

		strClasse += "}";

		return strClasse;
	}

	private String criar_javaMobile(String nm_tabela, List<?> listaDadosCampos) {

		String strClasse = "package br.com.primum.modelo."+nm_tabela+";\n" +
						   "\n" +
						   "import java.io.Serializable;\n" +
						   "import java.util.Date;\n" +
						   "\n" +
							"public class "+ (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase() +"  implements Serializable {\n"+
							"\n"
							+ "\tprivate static final long serialVersionUID = 1L;\n";

		String strClasse_getSetter = "";
		for(PostgresqlDTO p : ((List<PostgresqlDTO>) listaDadosCampos)){

			String tipo_campo = "";

			if(p.getTp_campo().toLowerCase().indexOf("int") > -1){
				tipo_campo 	= "Integer";
				strClasse	+= "\tprivate Integer "+ p.getNm_coluna() +";\n\n";
			}else if(p.getTp_campo().toLowerCase().indexOf("date") > -1){
				tipo_campo 	= "Date";
				strClasse	+= "\tprivate Date "+ p.getNm_coluna() +";\n\n";
			}else if(p.getTp_campo().toLowerCase().indexOf("varchar") > -1){
				tipo_campo 	= "String";
				strClasse	+= "\tprivate String "+ p.getNm_coluna() +";\n\n";
			}

			strClasse_getSetter += "\n\tpublic ";
			strClasse_getSetter += "" + tipo_campo + " get"+ p.getNm_coluna().substring(0,1).toUpperCase()+""+ p.getNm_coluna().substring(1).toLowerCase() +"(){\n";
			strClasse_getSetter += "\t\treturn "+ p.getNm_coluna() +";\n\t}\n\n";

			strClasse_getSetter += "\n\tpublic void set"+ p.getNm_coluna().substring(0,1).toUpperCase()+""+ p.getNm_coluna().substring(1).toLowerCase() +"(Integer "+ p.getNm_coluna() +"){\n";
			strClasse_getSetter += "\t\tthis."+p.getNm_coluna()+" = "+ p.getNm_coluna()+";\n\t}\n\n";
		}

		strClasse += strClasse_getSetter;

		strClasse += "}";

		return strClasse;
	}
	
	

	private String criar_flex(String nm_tabela, List<String> listaNomeCampos, List<Object> listaTipoCampos) {

		Tabela tabela = ( Tabela ) this.getDao().getSession().createCriteria(Tabela.class).add(Restrictions.eq("nm_tabela", nm_tabela)).uniqueResult();
		
		String strCabeca = "package br.com.lsd.modelo."+nm_tabela+";\n\n";
		String strImports = "\timport mx.collections.ArrayCollection;\n";
		String strClasse =  "\n\n\t[Bindable]\n" +
							"\t[RemoteClass(alias=\"br.com.primum.modelo." + nm_tabela + "." + (String) nm_tabela.substring(0,1).toUpperCase() + ""+ (String) nm_tabela.substring(1).toLowerCase() +"\")]\n";
							if(tabela!=null && tabela.getTp_sincronizar() != null && tabela.getTp_sincronizar().equals(1)){
								strClasse+= "\tpublic class "+ (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase() +" implements IPrimumBean {\n";	
							}else{
								strClasse+= "\tpublic class "+ (String) nm_tabela.substring(0,1).toUpperCase()+""+ (String) nm_tabela.substring(1).toLowerCase() +"{\n";
							}
							strClasse+= "\n";

		for(Integer e = 0; e< listaNomeCampos.size() ; e++){
			if(listaTipoCampos.get(e).toString().equals("4")){
				strClasse+= "\t\tpublic var "+listaNomeCampos.get(e).toString()+" : Object;\n\n";
			}else if(listaTipoCampos.get(e).toString().equals("8")){
				strClasse+= "\t\tpublic var "+listaNomeCampos.get(e).toString()+" : Date;\n\n";
			}else if(listaTipoCampos.get(e).toString().equals("-1")){
				strClasse+= "\t\tpublic var "+listaNomeCampos.get(e).toString()+" : String;\n\n";
			}
		}
		
		
		if(tabela!=null && tabela.getTp_sincronizar() != null && tabela.getTp_sincronizar().equals(1)){
			
			strImports+="\timport br.com.lsd.interfaces.IPrimumBean;\n"; 
			strImports+="\timport br.com.lsd.util.Util;\n";
			
			strClasse+= "\t\tpublic function getSQLinsert() : String {\n";
			strClasse+= "\t\t\tvar sql : String = 'insert into "+ nm_tabela +" (';\n\n";
				
			strClasse+= "\t\t\tif ( (this.cd_"+ nm_tabela +"!= null) && (this.cd_"+ nm_tabela +"!= '') ) {\n";
			strClasse+= "\t\t\t\tsql = sql + 'cd_" + nm_tabela +",';\n";
			strClasse+= "\t\t\t}\n\n";
			
			strClasse+= "\t\t\tsql = sql + \n";
			for(Integer e = 0; e< listaNomeCampos.size() ; e++){
				if( !listaNomeCampos.get(e).toString().equals("cd_"+nm_tabela.toLowerCase())){
					if(e == (listaNomeCampos.size() - 1)){
						strClasse+= "\t\t\t\t'"+listaNomeCampos.get(e).toString() + ") values (';\n\n";
					}else{
						strClasse+= "\t\t\t\t'"+listaNomeCampos.get(e).toString() + ",' +\n";
					}
				}
			}
			
			strClasse+= "\t\t\tif ( (this.cd_"+ nm_tabela +" != null) && (this.cd_"+nm_tabela +" != '') ) {\n";
			strClasse+= "\t\t\t\tsql = sql + this.cd_"+ nm_tabela +"', ';\n";
			strClasse+= "\t\t\t}\n\n";
			
			strClasse+= "\n";
			for(Integer e = 0; e< listaNomeCampos.size() ; e++){
				
				if(listaTipoCampos.get(e).toString().equals("8")){
					if(e == (listaNomeCampos.size() - 1)){
						strClasse+= "\n\t\t\t if (this."+ listaNomeCampos.get(e).toString() +" != null) {\n";
						strClasse+= "\t\t\t\tsql = sql + 'Datetime' 		+ Util.getDataSQLiteInsert(this."+listaNomeCampos.get(e).toString()+") + '); ';\n";
						strClasse+= "\t\t\t} else {\n";
						strClasse+= "\t\t\t\tsql = sql + 'null); ';\n";
						strClasse+= "\t\t\t}\n\n";
					}else{
						strClasse+= "\n\t\t\t if (this."+ listaNomeCampos.get(e).toString() +" != null) {\n";
						strClasse+= "\t\t\t\tsql = sql + 'Datetime' 		+ Util.getDataSQLiteInsert(this."+listaNomeCampos.get(e).toString()+") + ', ';\n";
						strClasse+= "\t\t\t} else {\n";
						strClasse+= "\t\t\t\tsql = sql + 'null, ';\n";
						strClasse+= "\t\t\t}\n\n";
					}
				}else{
					if( !listaNomeCampos.get(e).toString().equals("cd_"+nm_tabela.toLowerCase())){
						if(e == (listaNomeCampos.size() - 1)){
							strClasse+= "\t\t\tsql = sql + this."+ listaNomeCampos.get(e).toString() +" 				+ ');';\n";
						}else{
							strClasse+= "\t\t\tsql = sql + this."+ listaNomeCampos.get(e).toString() +" 				+ ', ' ;\n";
						}
					}
				}
			}
			strClasse+= "\n";
			strClasse+= "\t\t\treturn sql;\n";
			strClasse+= "\t\t}\n\n";
			
			strClasse+= "\t\tpublic function getSQLupdate() : String {\n";
			strClasse+= "\t\t\tvar sql : String = 'update "+ nm_tabela +" set ';\n";
			
			for(Integer e = 0; e< listaNomeCampos.size() ; e++){
				if(listaTipoCampos.get(e).toString().equals("8")){
					if(e == (listaNomeCampos.size() - 1)){
						strClasse+= "\n\t\t\t if (this."+ listaNomeCampos.get(e).toString() +" != null) {\n";
						strClasse+= "\t\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=Datetime' 		+ Util.getDataSQLiteInsert(this."+listaNomeCampos.get(e).toString()+") + ' where cd_"+nm_tabela+" = '+ this.cd_"+nm_tabela+" + ';'; \n";
						strClasse+= "\t\t\t} else {\n";
						strClasse+= "\t\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=null where cd_"+nm_tabela+" = '+ this.cd_"+nm_tabela+" + ';'; \n";
						strClasse+= "\t\t\t}\n\n";
					}else{
						strClasse+= "\n\t\t\t if (this."+ listaNomeCampos.get(e).toString() +" != null) {\n";
						strClasse+= "\t\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=Datetime' 		+ Util.getDataSQLiteInsert(this."+listaNomeCampos.get(e).toString()+") + ', ';\n";
						strClasse+= "\t\t\t} else {\n";
						strClasse+= "\t\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=null, ';\n";
						strClasse+= "\t\t\t}\n\n";	
					}
				}else{
					if( !listaNomeCampos.get(e).toString().equals("cd_"+nm_tabela.toLowerCase())){
						if(e == (listaNomeCampos.size() - 1)){
							if(listaTipoCampos.get(e).toString().equals("-1")){
								strClasse+="\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"='   + this."+ listaNomeCampos.get(e).toString() +" + ' where cd_"+nm_tabela+" = '+ this.cd_"+nm_tabela+" + ';'; \n";
							}else{
								strClasse+="\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=   + this."+ listaNomeCampos.get(e).toString() +" +  where cd_"+nm_tabela+" = '+ this.cd_"+nm_tabela+" + ';'; \n";
							}
						}else{
							if(listaTipoCampos.get(e).toString().equals("-1")){
								strClasse+="\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"='   + this."+ listaNomeCampos.get(e).toString() +" + ', ';\n";
							}else{
								strClasse+="\t\t\tsql = sql + '"+listaNomeCampos.get(e).toString()+"=   + this."+ listaNomeCampos.get(e).toString() +" + , ';\n";
							}
						}
					}
				}
			}
			
			strClasse+= "\n\n\t\t\treturn sql;\n\t\t}\n";
		
		}
		
		
		strClasse+="\t}";

		
		
		return strCabeca+strImports+strClasse;
	}
}
