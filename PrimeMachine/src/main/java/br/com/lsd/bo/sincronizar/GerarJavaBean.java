package br.com.lsd.bo.sincronizar;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.primum.bo.criar_bean.Criar_beanBO;
import br.com.primum.hibernate.GenericDAO;

@RestController
@RequestMapping("/lsd")
public class GerarJavaBean {

	@SuppressWarnings("unchecked")
    @CrossOrigin
	@RequestMapping(value= "/criarJavaBean", method = RequestMethod.GET)
	public void criarBaseSQLite() throws FileNotFoundException {

		String sqlTabelas = "SELECT TABLE_NAME AS \"tablename\" FROM all_tables where owner = 'VIASOFTMCP' and TABLESPACE_NAME IS NOT NULL";
		GenericDAO dao = new GenericDAO();
		Object o = dao.getSession().createSQLQuery(sqlTabelas).list();

		for (String nm_tabela : (ArrayList<String>) o) {

			Criar_beanBO c = new Criar_beanBO();
			try {
				c.Criar_arquivos(nm_tabela);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
}