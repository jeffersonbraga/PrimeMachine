package br.com.lsd.bo.login;

import br.com.lsd.bo.exception.LoginException;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.usuario.Usuario;

public class LoginBO {
	
	private GenericDAO dao;
	
	public Object checkIn(Usuario usuario) throws Exception {
		
		this.setDao(new GenericDAO());
		Usuario user;
		/*user = (Usuario) this.getDao().getSession().createCriteria(Usuario.class)
				.add(	Restrictions.eq("ds_senha", usuario.getDs_senha())	)
				.add(	Restrictions.eqProperty("nm_usuario", usuario.getNm_usuario())	);
		*/
		user = null;
		if ( (user == null) || (user.getCd_usuario() == null) || (!usuario.getCd_usuario().equals(user.getCd_usuario()))){
			//TODO GRAVAR LOG
			throw new Exception(LoginException.LOGIN_EXCEPTION_MENSAGEM);
		}
		
		return user;
	}
	
	public GenericDAO getDao() {
		return dao;
	}

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}
}