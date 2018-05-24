package br.com.primum.hibernate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.modelo.dbconf.Dbconf;
import br.com.primum.reflection.Reflection;
@SuppressWarnings("deprecation")
public class HibernateSessionFactory {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static Configuration configuration;
    private static Configuration configurationCompararDicionario;    
    private static org.hibernate.SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
    	
    	try {
    		/**
    		 * PARA ENCONTRAR TODAS AS CLASSES QUE EXTENDEM OU IMPLEMENTAM A CLASSE PARAMETRO.
    		 * NO CASO PARA PERSISTENCIA PROCURAMOS POR Entity (javax.persistence.Entity), ANOTAÇÃO 
    		 * UTILIZADA PARA OBJETOS PERSISTENTES.
    		 * 
				ClassPathScanningCandidateComponentProvider scanner =
				new ClassPathScanningCandidateComponentProvider(<DO_YOU_WANT_TO_USE_DEFALT_FILTER>);

				scanner.addIncludeFilter(new AnnotationTypeFilter(<TYPE_YOUR_ANNOTATION_HERE>.class));

				for (BeanDefinition bd : scanner.findCandidateComponents(<TYPE_YOUR_BASE_PACKAGE_HERE>))
				    System.out.println(bd.getBeanClassName());
    		 */
			ClassPathScanningCandidateComponentProvider scanner =
			new ClassPathScanningCandidateComponentProvider(false);

			scanner.addIncludeFilter(new AnnotationTypeFilter(javax.persistence.Entity.class));

    		configuration = new Configuration();
    		lerAquivoConfiguracao(configuration);
			for (BeanDefinition bd : scanner.findCandidateComponents("br.com")) {
				try{
					configuration.addAnnotatedClass(Class.forName(bd.getBeanClassName())); 
				} catch( Exception e) {
					System.out.println("ERRO AO INSERIR CLASSE NO CONJUNTO DE ANOTAÇÕES PERSISTENTES.");
					e.printStackTrace();
				}
			}
			/*
            configuration.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/lifesoftware");  
    		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");  
    		configuration.setProperty("hibernate.connection.username", "postgres");  
    		configuration.setProperty("hibernate.connection.password","masterkey");  
    		configuration.setProperty("hibernate.dialect","br.com.primum.dialect.PostgresqlDialect_ext");

			 <property name="hibernate.connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
			 <property name="hibernate.connection.url">jdbc:oracle:thin:@127.0.0.1:1521:MKYONG</property>
			 <property name="hibernate.connection.username">mkyong</property>
			 <property name="hibernate.connection.password">password</property>
			 <property name="hibernate.dialect">org.hibernate.dialect.Oracle10gDialect</property>
			 <property name="hibernate.default_schema">MKYONG</property>
			 <property name="show_sql">true</property>
*/
			
			//PADRAO ORACLE
			/*
    		configuration.setProperty("hibernate.connection.driver_class","oracle.jdbc.driver.OracleDriver");  
    		configuration.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@//192.168.100.9:1521/xe");  
    		configuration.setProperty("hibernate.connection.username", "VIASOFTMCP");  
    		configuration.setProperty("hibernate.connection.password","VIASOFTMCP");  
    		configuration.setProperty("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
    		*/
/*
            configuration.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/lifesoftware");  
    		configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");  
    		configuration.setProperty("hibernate.connection.username", "postgres");  
    		configuration.setProperty("hibernate.connection.password","masterkey");  
    		configuration.setProperty("hibernate.dialect","br.com.primum.dialect.PostgresqlDialect_ext");
*/
    		configuration.setInterceptor( new Interceptador() );
    		try {
        		sessionFactory = configuration.buildSessionFactory();  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         return sessionFactory;
      } catch (Throwable ex) {

         System.err.println("Initial SessionFactory creation failed." + ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

	   public static void shutdown()
	   {
	      getSessionFactory().close();
	   }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void lerAquivoConfiguracao(Configuration conf) {

		Dbconf dbconf = null;
		try {
			//String dir = "D:\\jboss-4.2.3.GA\\server\\default\\deploy\\Builder.war\\WEB-INF\\classes\\dbconf.txt";
			
			String dir = ContextoAplicacao.CAMINHO_APLICACAO + "//dbconf.txt";
			BufferedReader in = new BufferedReader(new FileReader(dir));

			dbconf = new Dbconf();            
            while (in.ready()) {

            	String str = null; 
            	str = in.readLine();

            	String campo = "";
            	String valor = "";
            	
            	if ((str.split("&") != null) && (str.split("&").length < 2) ) {
            		campo = str.split("&")[0];
            	} else if ((str.split("&") != null) && (str.split("&").length > 1) ) {
            		campo = str.split("&")[0];
            		valor = str.split("&")[1];
            	}

                Reflection.setBeanConfiguracao(dbconf, campo, valor);
            }
            in.close();
	    } catch (IOException e) {

	    	System.out.println("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
	    	e.printStackTrace();
	    	System.out.println("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
	    } catch (Exception e) {

	    	System.out.println("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
			e.printStackTrace();
	    	System.out.println("ATEN플O CHECAR O CONTEXTO DA APLICA플O: " + ContextoAplicacao.CAMINHO_APLICACAO);
		}

		if (dbconf != null) {
			configuration.setProperty("hibernate.connection.url",dbconf.getCaminhoconexao());  
			configuration.setProperty("hibernate.connection.driver_class", dbconf.getDriverconexao());  
			configuration.setProperty("hibernate.connection.username", dbconf.getUsuarioconexao());  
			configuration.setProperty("hibernate.connection.password",dbconf.getSenhaconexao());  
			configuration.setProperty("hibernate.dialect",dbconf.getDialetoconexao());
		}
		
	}

	public static void lerAquivoConfiguracaoCompararDicionario(Configuration conf, String nome_base) {

	}

    private HibernateSessionFactory() {
    }

	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {

				sessionFactory = buildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSessionCompararDicionario(String caminho_banco) throws HibernateException {

        return null;
    }

	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getNovaSession() throws HibernateException {
 
        Session session = null;
		session = sessionFactory.openSession();

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		//HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}
}