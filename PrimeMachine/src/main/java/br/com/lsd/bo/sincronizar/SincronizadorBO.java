package br.com.lsd.bo.sincronizar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.crud.LogPrimum;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.acao_preventiva.Acao_preventiva;
import br.com.primum.modelo.atividades.Atividades;
import br.com.primum.modelo.campo.Campo;
import br.com.primum.modelo.campospesquisa.CamposPesquisa;
import br.com.primum.modelo.classificacao_helpdesk.Classificacao_helpdesk;
import br.com.primum.modelo.codigosfixos.CodigosFixos;
import br.com.primum.modelo.empresa.Empresa;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.menu.Menu;
import br.com.primum.modelo.modulo.Modulo;
import br.com.primum.modelo.natureza_ocorrencia.Natureza_ocorrencia;
import br.com.primum.modelo.periodicidade.Periodicidade;
import br.com.primum.modelo.permissao_acesso.Permissao_acesso;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.plano_acao.Plano_acao;
import br.com.primum.modelo.profissao.Profissao;
import br.com.primum.modelo.programa.Programa;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.revisao_programa_qualidade.Revisao_programa_qualidade;
import br.com.primum.modelo.setor.Setor;
import br.com.primum.modelo.tabela.Tabela;
import br.com.primum.modelo.tipo_turno.Tipo_turno;
import br.com.primum.modelo.unidade.Unidade;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.reflection.Reflection;

public class SincronizadorBO {

	public boolean sincronizar(List<Execucao_formulario> listaSincronizar) {
		//percorrer os objetos retirando as chaves primarias e estrangeiras.
		for (Execucao_formulario exec : listaSincronizar) {
			LogPrimum.mensagem("LIMPAR CHAVES");
			if (exec.getSt_sincronizado() == null) {
				this.limparChaves(exec);
			}
		}

		CrudGenericBO crud = new CrudGenericBO();
		for (Execucao_formulario exec : listaSincronizar) {
			LogPrimum.mensagem("EXECU��O: " + ((exec.getCd_execucao_formulario() != null)?exec.getCd_execucao_formulario():""));
			for (Resposta_exec_formulario resposta : exec.getListaRespostas_execucao_formulario()) {
				if ((resposta.getTp_resposta_exec_formulario() != null) && (resposta.getTp_resposta_exec_formulario() == 0)) {
					if (resposta.getPlano_acao_formulario() != null) {
						try {
							LogPrimum.mensagem("SALVAR PLANO DE A��O");
							crud.salvarObjetoCompleto(resposta.getPlano_acao_formulario().getPlano_acao());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						resposta.getPlano_acao_formulario().setCd_plano_acao(resposta.getPlano_acao_formulario().getPlano_acao().getCd_plano_acao());
					}
				}
			}

			crud.salvarObjetoCompleto(exec);
		}
		return false;
	}

	public void limparChaves(Object o){
		Field[] fields = o.getClass().getDeclaredFields();

		try {
			String nomeChavePrimaria = Reflection.getNomeChavePrimaria(o);
			for (Field field : fields) {

				if(field.getName().equals(nomeChavePrimaria)){
					Reflection.setValorChavePrimaria(o,null);
				}else{
					if ( field.getType().getName().equals("java.util.List") ) {
						if (field.isAnnotationPresent(ListaBusca.class)) {

							List<?> lista = (List<?>) Reflection.getValorCampo(o, field.getName());
							if ( (lista != null) && (lista.size() > 0) ) {

								for (Object objetoFilho : lista) {
									limparChaves(objetoFilho);
								}
							}
						}
					}else if (field.isAnnotationPresent(ObjetoBusca.class)) {
						Object objetoFilho = Reflection.getValorCampo(o, field.getName());						
						if(objetoFilho != null) {
							limparChaves(objetoFilho);
						}
					}else{
						if( field.isAnnotationPresent(ChaveEstrangeira.class) ){
							Object aux = null;
							String nome = field.getName();
							nome = "set" + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
							Method metodo = o.getClass().getMethod(nome, Integer.class);
							metodo.invoke( o, aux );
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void construirBanco() {
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:base_primum.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    LogPrimum.mensagem("Opened database successfully");
	}

	@SuppressWarnings("unchecked")
	public String criarBaseSQLite(String schema) {

    	HttpServletRequest request = null;// = FlexContext.getHttpRequest();
    	String nomeSchema="public";
    	
    	if (request != null) {
        	String s = (String) request.getAttribute("SCHEMAPRIMUM");
        	if ( (s != null) && (s != "") &&  (s != "public") ) {
        		nomeSchema = s;
        	} 
    	} 

    	if (schema != null && schema != "") {
    		nomeSchema = schema;
    	}

		//String sqlTabelas = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname NOT IN ('pg_catalog', 'information_schema', 'pg_toast', 'public') ORDER BY schemaname, tablename";
		String sqlTabelas = "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname IN ('"+nomeSchema+"') ORDER BY schemaname, tablename";
		GenericDAO dao = new GenericDAO();
		Object o = dao.getSession().createSQLQuery(sqlTabelas).list();

		List<Tabela> listaTabelaChecar = dao.getSession().createCriteria(Tabela.class).list();

		String criar 	= "";
		ArrayList<String> lTipos = new ArrayList<>();
		for (String nm_tabela : (ArrayList<String>) o) {
			boolean sicronizarTabela = false;

			for (Tabela t : listaTabelaChecar) {
				if (t.getNm_tabela().equalsIgnoreCase(nm_tabela) && ((t.getTp_sincronizar() != null) && (t.getTp_sincronizar().equals(1)))) {
					sicronizarTabela = true;
					listaTabelaChecar.remove(t);
					break;
				}
			}

			if (!nm_tabela.equals("espelho")) {

				//String sqlCamposTabela = "select column_name as nm_campo, data_type as ds_descricao, case when is_nullable = 'YES' then 1 else 0 end as st_aceitavalornull from information_schema.columns where table_name = '" + nm_tabela + "' and table_schema NOT IN ('pg_catalog', 'information_schema', 'pg_toast', 'public')";
				String sqlCamposTabela = "select column_name as nm_campo, data_type as ds_descricao, case when is_nullable = 'YES' then 1 else 0 end as st_aceitavalornull from information_schema.columns where table_name = '" + nm_tabela + "' and table_schema IN ('"+nomeSchema+"')";
				//ArrayList<ArrayList<?>> listaColunas = (ArrayList<ArrayList<?>>) dao.getSession().createSQLQuery(sqlCamposTabela).list();
				List<?> listaColunas = null;
				try {
					listaColunas = dao.findBySql(sqlCamposTabela, new Campo());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int i = 0;
				String temp 	= "";
				temp += "DROP TABLE IF EXISTS " + nm_tabela + "�CREATE TABLE IF NOT EXISTS " + nm_tabela + "(";
				String cpoInsert = "";
				String caminho = "";

				if (nm_tabela.equals("campospesquisa")) {				
					nm_tabela ="CamposPesquisa";
				} else if (nm_tabela.equals("codigosfixos")) {				
					nm_tabela ="CodigosFixos";
				} else if (nm_tabela.equals("sessaodocumento")) {				
					nm_tabela ="SessaoDocumento";
				}

				if (nm_tabela.equals("pre_avaliacao_ideia_pic")) {
					caminho = "br.com.primum.modelo.reuniao_pic." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
				} else {
					caminho = "br.com.primum.modelo." + nm_tabela.toLowerCase() + "." + nm_tabela.substring(0, 1).toUpperCase() + nm_tabela.substring(1);
				}

				try {

					String ch = Reflection.getNomeChavePrimaria(Class.forName(caminho));

					for (Campo itemColuna : (List<Campo>) listaColunas) {

						String tipoColuna = itemColuna.getDs_descricao();
						if (lTipos.toString().indexOf(tipoColuna) == -1) {
							LogPrimum.mensagem(tipoColuna);
							lTipos.add(tipoColuna);
						}

						if ( (tipoColuna == "timestamp with time zone") || (tipoColuna.indexOf("timestamp") > -1) || (tipoColuna.indexOf("date") > -1) || (tipoColuna.indexOf("character varying") > -1) ) {
							tipoColuna = "text";
						} else if ( tipoColuna.indexOf("char") > -1 ) {
							tipoColuna = "varchar";
						} else if ( tipoColuna.indexOf("double precision") > -1 ) {
							tipoColuna = "numeric(12,2)";
						} else if ( tipoColuna.indexOf("boolean") > -1 ) {
							tipoColuna = "integer";
						}

						String pk = "";
						if(ch.toLowerCase().equals(itemColuna.getNm_campo().toLowerCase())) {
							pk = " PRIMARY KEY ";
						}

						if (i == 0) {
							temp += itemColuna.getNm_campo() + " " + tipoColuna + pk + ((itemColuna.getSt_aceitavalornull() == 0)?" NOT NULL":"") ;
							cpoInsert += itemColuna.getNm_campo();
							i++;
						} else {
							temp += "," + itemColuna.getNm_campo() + " " + tipoColuna + pk + ((itemColuna.getSt_aceitavalornull() == 0)?" NOT NULL":"") ;
							cpoInsert += "," + itemColuna.getNm_campo();
						}
					}

					if (nm_tabela.equals("execucao_formulario")) {
						temp += ", st_sincronizado integer";
					} else if (nm_tabela.equals("campo_formulario")) {				
						temp += ", identificador integer";				
					}

					List<Object> listaRegistros = null;
					temp += ")�";

					if ((!nm_tabela.equalsIgnoreCase("permissao_acesso")) && sicronizarTabela) {

						if ( nm_tabela.toLowerCase().equals("cliente") ) {
							dao = new GenericDAO("public");
						} else {
							dao = new GenericDAO(nomeSchema);
						}

						listaRegistros = dao.getSession().createCriteria(Class.forName(caminho)).list();
						if( (listaRegistros != null) && (listaRegistros.size() > 0)) {

							String insert = "";				
							String[] listaCampos = cpoInsert.split(",");
							for (Object itemInserir : listaRegistros) {

								insert += "insert into " + nm_tabela + "(" + cpoInsert + ") values (";								

								for (int a = 0; a < listaCampos.length; a++) {
									Object v = Reflection.getValorCampo(itemInserir, listaCampos[a]);
									if (v == null) {
										v = "NULL";
									}

									if ( (v instanceof java.lang.String) || (v instanceof java.util.Date) ) {

										if (v instanceof java.util.Date) {
											GregorianCalendar cal = new GregorianCalendar();
											cal.setTime( ((Date) v) );
											String mes 		= "";
											String dia 		= "";
											String hora 	= "";
											String minutos 	= "";

											if (cal.get(cal.DAY_OF_MONTH) < 10) {
												dia = "0" + cal.get(cal.DAY_OF_MONTH);
											} else {
												dia = cal.get(cal.DAY_OF_MONTH) + "";
											}

											if ( (cal.get(cal.MONTH) + 1) < 10) {
												mes = "0" + (cal.get(cal.MONTH) + 1);
											} else {
												mes = (cal.get(cal.MONTH) + 1) + "";
											}

											if (cal.get(cal.HOUR_OF_DAY) < 10) {
												hora = "0" + (cal.get(cal.HOUR_OF_DAY));
											} else {
												hora = (cal.get(cal.HOUR_OF_DAY)) + "";
											}

											if (cal.get(cal.MINUTE) < 10) {
												minutos = "0" + cal.get(cal.MINUTE);
											} else {
												minutos = cal.get(cal.MINUTE) + "";
											}

											v = "Datetime('" + cal.get(cal.YEAR) + "-" + mes + "-"  + dia + " "  + hora + ":"  + minutos + ":00')";
											LogPrimum.mensagem(v.toString());
										} else {

											if (!v.equals("null")) {
												String aa = (String) v;
												v = "'" + aa.replaceAll("'", "\"") + "'";
											}
										}
									} else if ( v instanceof java.lang.Boolean ) {
										if ((Boolean) v) {
											v = 1;
										} else {
											v = 0;
										}
									}

									insert += v + ",";
								}

								insert = insert.substring(0, insert.length() - 1);
								insert += ")�";
							}
							temp += insert;
						}
					} else if (nm_tabela.equalsIgnoreCase("permissao_acesso")) {

						List<Permissao_acesso> listaPermissaoAcesso = new ArrayList<>();
						List<Usuario> listaUsuario = new GenericDAO().getSession().createCriteria(Usuario.class).list();
						for (Usuario u : listaUsuario) {
							Permissao_acesso p = new Permissao_acesso();
							p.setCd_modulo(3);
							p.setCd_permissao_acesso(u.getCd_usuario());
							p.setCd_programa(1);
							p.setCd_unidade(1);

							String insert = "insert into permissao_acesso(" +
									"cd_programa, " +
									"cd_usuario, " +
									"tp_salvar, " +
									"tp_excluir, " +
									"tp_visualizar, " +
									"tp_especial, " +
									"tp_editar," +
									"cd_unidade ) values(" +
									p.getCd_programa() + "," +
									u.getCd_usuario() + "," +
									"1,1,1,1,1," + 
									p.getCd_unidade() + ")�";
							temp = temp + insert;
							System.out.println(insert);
						}
					}
				} catch(NoClassDefFoundError e) {
					e.printStackTrace();
					temp = "";
					dao.getSession().flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					temp = "";
					dao.getSession().flush();
				} catch (Exception e) {
					e.printStackTrace();
					temp = "";
					dao.getSession().flush();
				}

				criar += temp;
			}
		}

		criar += "update execucao_formulario set st_sincronizado = 1";
		//criar = criar.substring(0, criar.length() - 1);
		//LogPrimum.mensagem(criar);
		return criar;
	}

	public byte[] criarEstrutura(String sql) {
		String sqlA = sql;
		String[] arr = sql.split("�");
	    	Throwable tb = null;

		Connection c = null;
	    Statement stmt = null;
	    try {

			File diretorio = new File(ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.db");
			
			if(diretorio.exists()){
				try {
					diretorio.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.db");
			
			LogPrimum.mensagem("Opened database successfully");

//	      stmt = c.createStatement();
//	      String sql = "CREATE TABLE COMPANY " +
//	                   "(ID INT PRIMARY KEY     NOT NULL," +
//	                   " NAME           TEXT    NOT NULL, " + 
//	                   " AGE            INT     NOT NULL, " + 
//	                   " ADDRESS        CHAR(50), " + 
//	                   " SALARY         REAL)";

	      for (int i =0; i < arr.length ; i++) {

	    	  sql = arr[i];
	    	  //LogPrimum.debug(sql);

	  	    try {

	  	    	stmt = c.createStatement();
	  	    	stmt.executeUpdate(sql);
	  	    	//c.commit();
	  	    } catch( Exception e666 ) {
	  	    	//TODO
	  	    	if (tb == null) {
	  	    		tb = e666;
	  	    	} else {

		  	    	tb.addSuppressed(e666);
	  	    	}
	  	    }
	     }

	     // stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      //System.exit(0);
	    }
	    LogPrimum.mensagem("Table created successfully");

	    File file = new File(ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.db");
//	    File file2 = new File(ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.txt");
	    byte[] b = new byte[(int) file.length()];
        
	    try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            
//            FileInputStream fileInputStreamA = new FileInputStream(file2);
//            fileInputStreamA.read(sqlA.getBytes());
         } catch (FileNotFoundException e) {
                     LogPrimum.mensagem("File Not Found.");
                     e.printStackTrace();
         }
         catch (IOException e1) {
                  LogPrimum.mensagem("Error Reading The File.");
                   e1.printStackTrace();
         }

	    return b;
	}

	public void inserirDados(String sql) {
		String[] arr 	= sql.split("INSERT INTO");

		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:base_primum.db");
	      LogPrimum.mensagem("Opened database successfully");

	      stmt = c.createStatement();
//	      String sql = "CREATE TABLE COMPANY " +
//	                   "(ID INT PRIMARY KEY     NOT NULL," +
//	                   " NAME           TEXT    NOT NULL, " + 
//	                   " AGE            INT     NOT NULL, " + 
//	                   " ADDRESS        CHAR(50), " + 
//	                   " SALARY         REAL)";

	      for (int i =1; i < arr.length ; i++) {

	    	  sql = "INSERT INTO " + arr[i];
		      stmt.executeUpdate(sql);
	      }

	     // stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    LogPrimum.mensagem("Table created successfully");
	}

	@SuppressWarnings("unchecked")
	public List<Object> sincronizarEscalonamento(Usuario usuario) throws Exception {

		GregorianCalendar dataInicialDia = (GregorianCalendar) GregorianCalendar.getInstance();
		dataInicialDia.set(Calendar.HOUR_OF_DAY,0);     
		dataInicialDia.set(Calendar.MINUTE,0);     
		dataInicialDia.set(Calendar.SECOND,0);

		GenericDAO dao = new GenericDAO();
		List<Object> lista = new ArrayList<>();
		List<Escalonamento_formulario> listaEscalonamento = dao.getSession()
				.createCriteria(Escalonamento_formulario.class)
				.addOrder(Order.asc("cd_escalonamento_formulario"))
				.list();

		List<Execucao_formulario> listaExecucao = dao.getSession().createCriteria(Execucao_formulario.class)
				.add(Restrictions.ge("dt_execucao_formulario", dataInicialDia.getTime()))
				.list();

		List<Plano_acao> listaPlano_acao = dao.getSession().createCriteria(Plano_acao.class)
				.list();

		CrudGenericBO crud = new CrudGenericBO();
		for (Escalonamento_formulario esc : listaEscalonamento) {
			crud.buscarObjetoCompleto(esc);
		}

		dao = new GenericDAO();
		for (Execucao_formulario exec : listaExecucao) {

			List<?> lst = dao.getSession().createCriteria(Resposta_exec_formulario.class)
			.add(Restrictions.eq("cd_execucao_formulario", exec.getCd_execucao_formulario()))
			.list();

			if (lst != null && lst.size() > 0) {
				exec.setListaRespostas_execucao_formulario( (List<Resposta_exec_formulario>) lst );
			}
		}

		dao = new GenericDAO();
		for (Plano_acao plano_acao : listaPlano_acao) {
			crud.buscarObjetoCompleto(plano_acao);
		}

		lista.add(listaEscalonamento);
		lista.add(listaExecucao);
		lista.add(listaPlano_acao);
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object> sincronizarDispositivo() throws Exception {

		GenericDAO dao = new GenericDAO();

		List<Object> listas = new ArrayList<>();

		List<Atividades> listaAtividades = dao.getSession().createCriteria(Atividades.class).list();
		List<Programa_qualidade> listaPrograma_qualidade = dao.getSession().createCriteria(Programa_qualidade.class).list();
		List<Revisao_programa_qualidade> listaRevisao_programa_qualidade = dao.getSession().createCriteria(Revisao_programa_qualidade.class).list();
		List<Profissao> listaProfissao = dao.getSession().createCriteria(Profissao.class).list();
		List<Pessoa> listaPessoa = dao.getSession().createCriteria(Pessoa.class).list();
		List<Usuario> listaUsuario = dao.getSession().createCriteria(Usuario.class).list();
		List<Periodicidade> listaPeriodicidade = dao.getSession().createCriteria(Periodicidade.class).list();
		List<Acao_corretiva> listaAcao_corretiva = dao.getSession().createCriteria(Acao_corretiva.class).list();
		List<Acao_preventiva> listaAcao_preventiva = dao.getSession().createCriteria(Acao_corretiva.class).list();

		List<Formulario> listaFormulario = dao.getSession().createCriteria(Formulario.class).list();

		List<Classificacao_helpdesk> listaClassificacao = dao.getSession().createCriteria(Classificacao_helpdesk.class).list();
		List<Natureza_ocorrencia> listaNaturezaOcorrencia = dao.getSession().createCriteria(Natureza_ocorrencia.class).list();

		//HELPDESK - GP 
		List<Empresa> listaEmpresa = dao.getSession().createCriteria(Empresa.class).list();

		List<Unidade> listaUnidades = dao.getSession().createCriteria(Unidade.class).list();

		//List<Permissao_acesso> listaPermissaoAcesso = dao.getSession().createCriteria(Permissao_acesso.class).list();

//		List<Grupo_formulario> listaGrupo_formulario = dao.getSession().createCriteria(Grupo_formulario.class).list();
//		List<Campo_formulario> listaCampo_formulario = dao.getSession().createCriteria(Campo_formulario.class).list();
//		List<Config_campo_formulario> listaConfig_campo_formulario = dao.getSession().createCriteria(Config_campo_formulario.class).list();
//		List<Resposta_campo_formulario> listaResposta_campo_formulario = dao.getSession().createCriteria(Resposta_campo_formulario.class).list();
//		List<Acao_corretiva_formulario> listaAcao_corretiva_formulario = dao.getSession().createCriteria(Acao_corretiva_formulario.class).list();

		List<Setor> listaSetor = dao.getSession().createCriteria(Setor.class).list();

		CrudGenericBO crud = new CrudGenericBO();
		for (Formulario formulario : listaFormulario) {
			crud.buscarObjetoCompleto(formulario);
		}

		listas.add(listaAtividades);
		listas.add(listaPrograma_qualidade);
		listas.add(listaRevisao_programa_qualidade);
		listas.add(listaProfissao);
		listas.add(listaPessoa);
		listas.add(listaUsuario);
		listas.add(listaPeriodicidade);
		listas.add(listaAcao_corretiva);
		listas.add(listaAcao_preventiva);
		listas.add(listaFormulario);
		listas.add(listaSetor);

		listas.add(listaClassificacao);
		listas.add(listaNaturezaOcorrencia);

		listas.add(listaEmpresa);
		listas.add(listaUnidades);

		List<Permissao_acesso> listaPermissaoAcesso = new ArrayList<>();
		for (Usuario u : listaUsuario) {
			Permissao_acesso p = new Permissao_acesso();
			p.setCd_modulo(3);
			p.setCd_usuario(u.getCd_usuario());
			p.setCd_programa(1);
			p.setCd_unidade(1);
			listaPermissaoAcesso.add(p);
		}

		listas.add(listaPermissaoAcesso);

		return listas; 
	}

	@SuppressWarnings("unchecked")
	public List<Object> sincronizarDicionario() throws Exception {
		GenericDAO dao = new GenericDAO();
		List<Object> listas = new ArrayList<>();

		List<Modulo> listaModulos = dao.getSession().createCriteria(Modulo.class).list();
		List<Tabela> listaTabelas = dao.getSession().createCriteria(Tabela.class).list();
		List<Programa> listaProgramas = dao.getSession().createCriteria(Programa.class).list();
		List<Menu> listaMenus = dao.getSession().createCriteria(Menu.class).list();
		List<Campo> listaCampos = dao.getSession().createCriteria(Campo.class).list();
		List<CamposPesquisa> listaCamposPesquisa = dao.getSession().createCriteria(CamposPesquisa.class).list();
		List<CodigosFixos> listaCodigosFixos = dao.getSession().createCriteria(CodigosFixos.class).list();
		List<Tipo_turno> listaTipo_turno = dao.getSession().createCriteria(Tipo_turno.class).list();

		listas.add( listaModulos );
		listas.add( listaTabelas );
		listas.add( listaProgramas );
		listas.add( listaMenus );
		listas.add( listaCampos );
		listas.add( listaCamposPesquisa );
		listas.add( listaCodigosFixos );
		listas.add( listaTipo_turno );

		return listas;
	}

	@SuppressWarnings("unchecked")
	public List<Object> gerarExecucao_formulario(Integer mes) throws Exception {

		GenericDAO dao = new GenericDAO();

		List<Object> listas = new ArrayList<>();

		List<Execucao_formulario> listaExecucao = dao.getSession().createCriteria(Execucao_formulario.class).list();

		CrudGenericBO crud = new CrudGenericBO();
		int dia = 1;
		for (Execucao_formulario execucao_formulario : listaExecucao) {
			crud.buscarObjetoCompleto(execucao_formulario);
		}

		while(dia < 31) {

			//crud = new CrudGenericBO();
			for (Execucao_formulario execucao_formulario : listaExecucao) {
				crud = new CrudGenericBO();

				this.limparChaves(execucao_formulario);

				LogPrimum.mensagem("EXECU��O: " + execucao_formulario.getCd_execucao_formulario());
				for (Resposta_exec_formulario resposta : execucao_formulario.getListaRespostas_execucao_formulario()) {
					if ((resposta.getTp_resposta_exec_formulario() != null) && (resposta.getTp_resposta_exec_formulario() == 0)) {
						if (resposta.getPlano_acao_formulario() != null) {
							try {
								LogPrimum.mensagem("SALVAR PLANO DE A��O");
								crud.salvarObjetoCompleto(resposta.getPlano_acao_formulario().getPlano_acao());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							resposta.getPlano_acao_formulario().setCd_plano_acao(resposta.getPlano_acao_formulario().getPlano_acao().getCd_plano_acao());
						}
					}
				}

				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(execucao_formulario.getDt_execucao_formulario());
				cal.set(cal.DAY_OF_MONTH, dia);
				cal.set(cal.MONTH, mes);
				execucao_formulario.setDt_execucao_formulario(cal.getTime());
				if ( (cal.get(cal.DAY_OF_WEEK) > 1) && (cal.get(cal.DAY_OF_WEEK) < 6) ) {
					crud.salvarObjetoCompleto(execucao_formulario);
				}
				crud.fecharTransacao();
			}
			dia++;
		}

		return listas; 
	}

	public void copiarFormulario(Integer cd_formulario) {

		try {
			GenericDAO dao = new GenericDAO();
			Formulario formulario = (Formulario) dao.getSession().createCriteria(Formulario.class).add(Restrictions.eq("cd_formulario", cd_formulario)).uniqueResult();

			CrudGenericBO crud = new CrudGenericBO();
			//for (Formulario f : listaFormulario) {
			crud.buscarObjetoCompleto(formulario);
			//}

			crud = new CrudGenericBO();
			SincronizadorBO s = new SincronizadorBO();
			s.limparChaves(formulario);
			crud.salvarObjetoCompleto(formulario);

			LogPrimum.mensagem("Teste fim...");
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void criarArquivoSQLite() {

		HttpServletRequest request = null;//FlexContext.getHttpRequest();
    	String nomeSchema="public";

    	if (request != null) {
        	String s = (String) request.getAttribute("SCHEMAPRIMUM");
        	if ( (s != null) && (s != "") &&  (s != "public") ) {
        		nomeSchema = s;
        	} 
    	} 

		this.criarEstrutura(this.criarBaseSQLite(nomeSchema));
	}

	public String criarBaseSQLite() {

		HttpServletRequest request = null;//FlexContext.getHttpRequest();
    	String nomeSchema="public";

    	if (request != null) {
        	String s = (String) request.getAttribute("SCHEMAPRIMUM");
        	if ( (s != null) && (s != "") &&  (s != "public") ) {
        		nomeSchema = s;
        	} 
    	} 

		return this.criarBaseSQLite(nomeSchema);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<List<?>> getListasDicionario(String caminho_banco) {
		
		PesquisaPadraoBO pesquisa = new PesquisaPadraoBO();
		GenericDAO daoPara 	= new GenericDAO();
		
		List<List<?>> listaListasBase1 = new ArrayList<>();
		List<List<?>> listaListasBase2 = new ArrayList<>();
		List<List<?>> listaListasBases = new ArrayList<>();
		List<List<List<?>>> listaTotal = new ArrayList<>();
		
		List<Programa> listaProgramas1 = ( List<Programa> ) daoPara.getSession().createCriteria(Programa.class).addOrder((Order.asc("cd_programa"))).list();
		//List<Menu> listaMenu1 = ( List<Menu> ) dao.getSession().createCriteria(Menu.class).addOrder((Order.asc("cd_menu"))).list();
		List<Tabela> listaTabela1 = ( List<Tabela> ) daoPara.getSession().createCriteria(Tabela.class).addOrder((Order.asc("cd_tabela"))).list();
		//List<Campo> listaCampo1 = ( List<Campo> ) dao.getSession().createCriteria(Campo.class).addOrder((Order.asc("cd_campo"))).list();
		//List<CamposPesquisa> listaCamposPesquisa1 = ( List<CamposPesquisa> ) dao.getSession().createCriteria(CamposPesquisa.class).addOrder((Order.asc("cd_campospesquisa"))).list();
		//List<CodigosFixos> listaCodigosFixos1 = ( List<CodigosFixos> ) dao.getSession().createCriteria(CodigosFixos.class).addOrder((Order.asc("cd_codigosfixos"))).list();
		List<Modulo> listaModulo1 = ( List<Modulo> ) daoPara.getSession().createCriteria(Modulo.class).addOrder((Order.asc("cd_modulo"))).list();   		 

		for( Programa p : listaProgramas1 ){
			pesquisa.buscarObjetoCompleto( p );
		}

		for( Tabela t : listaTabela1 ){
			pesquisa.buscarObjetoCompleto( t );
		}

		listaListasBase1.add( listaProgramas1 ); 
		//listaListasBase1.add( listaMenu1 );
		listaListasBase1.add( listaTabela1 );
		//listaListasBase1.add( listaCampo1 );
		//listaListasBase1.add( listaCamposPesquisa1 );
		//listaListasBase1.add( listaCodigosFixos1 );
		listaListasBase1.add( listaModulo1 );


		GenericDAO daoDe 	= new GenericDAO(caminho_banco);

		List<Programa> listaProgramas2 = ( List<Programa> ) daoDe.getSession().createCriteria(Programa.class).addOrder((Order.asc("cd_programa"))).list();
		//List<Menu> listaMenu2 = ( List<Menu> ) dao.getSession2().createCriteria(Menu.class).addOrder((Order.asc("cd_menu"))).list();
		List<Tabela> listaTabela2 = ( List<Tabela> ) daoDe.getSession().createCriteria(Tabela.class).addOrder((Order.asc("cd_tabela"))).list();
		//List<Campo> listaCampo2 = ( List<Campo> ) dao.getSession2().createCriteria(Campo.class).addOrder((Order.asc("cd_campo"))).list();
		//List<CamposPesquisa> listaCamposPesquisa2 = ( List<CamposPesquisa> ) dao.getSession2().createCriteria(CamposPesquisa.class).addOrder((Order.asc("cd_campospesquisa"))).list();
		//List<CodigosFixos> listaCodigosFixos2 = ( List<CodigosFixos> ) dao.getSession2().createCriteria(CodigosFixos.class).addOrder((Order.asc("cd_codigosfixos"))).list();
		List<Modulo> listaModulo2 = ( List<Modulo> ) daoDe.getSession().createCriteria(Modulo.class).addOrder((Order.asc("cd_modulo"))).list();

		for( Programa p : listaProgramas2 ){
			pesquisa.buscarObjetoCompleto( p );
		}

		for( Tabela t : listaTabela2 ){
			pesquisa.buscarObjetoCompleto( t );
		}

		listaListasBase2.add( listaProgramas2 );
		//listaListasBase2.add(listaMenu2 ); 
		listaListasBase2.add(listaTabela2 );
		//listaListasBase2.add(listaCampo2 );
		//listaListasBase2.add(listaCamposPesquisa2 );
		//listaListasBase2.add(listaCodigosFixos2 );
		listaListasBase2.add(listaModulo2 );

		listaListasBases.add( listaListasBase1 );
		listaListasBases.add( listaListasBase2 );
		//listaTotal.add( listaListasBases );

		return listaListasBases;
	}

	public boolean salvarBaseAtual(List<Modulo> listaModulo, List<Tabela> listaTabela, List<Programa> listaPrograma) {

		CrudGenericBO crud = new CrudGenericBO();
		crud.salvaObjetosLista(listaModulo, false);
		crud.salvaObjetosLista(listaTabela, true);
		crud.salvaObjetosLista(listaPrograma, false);

		return true;
	}

	public boolean salvarBaseExterna(List<Modulo> listaModulo, List<Tabela> listaTabela, List<Programa> listaPrograma) {

		GenericDAO daoDe 	= new GenericDAO("");
		CrudGenericBO crud = new CrudGenericBO();
		crud.setDao(daoDe);
		crud.salvaObjetosLista(listaModulo, false);
		crud.salvaObjetosLista(listaTabela, true);
		crud.salvaObjetosLista(listaPrograma, false);

		return true;
	}
}