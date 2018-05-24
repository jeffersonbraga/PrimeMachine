package br.com.lsd.service.usuario;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.criterion.Restrictions;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.lsd.bo.usuario.UsuarioBO;
import br.com.lsd.modelo.User;
import br.com.lsd.modelo.UserDTO;
import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.bo.pesquisa.PesquisaPadraoBO;
import br.com.primum.hibernate.GenericDAO;
import br.com.primum.modelo.pessoa.Pessoa;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.modelo.usuario_grupo_usuario.Usuario_grupo_usuario;
import br.com.primum.reflection.Reflection;

@RestController
@RequestMapping("api")
public class UsuarioService {

    @Inject
    Environment env;
    
    @Inject
    private UsuarioBO usuarioBO;

	public UsuarioBO getUsuarioBO() {
		return usuarioBO;
	}

	public void setUsuarioBO(UsuarioBO usuarioBO) {
		this.usuarioBO = usuarioBO;
	}

	//@RequestMapping(method = RequestMethod.POST, value = "/authentication")
	public Usuario efetuarLoginChamado(Integer cd_usuario){
		//this.setUsuarioBO(new UsuarioBO());
		Usuario u = this.getUsuarioBO().efetuarLoginChamado( cd_usuario );
		return u;
	}

	public boolean efetuarLoginApp(Usuario usuario) throws Exception {        
		/*System.out.println("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
        
		this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().efetuarLogin(usuario);*/
		return true;
	}

	public Usuario efetuarLogin(Usuario usuario) throws Exception {
		if (this.getUsuarioBO() == null) {
			this.setUsuarioBO(new UsuarioBO());
		}
		return this.getUsuarioBO().efetuarLogin(usuario);
	}
	
	
	public Boolean criptografaSenha( Integer cd_usuario, String senha_atual ){
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().criptografaSenha( cd_usuario, senha_atual );
	}
	
	public Boolean validarSenhaETrocar(Usuario usuario, String senha_atual, String nova_senha) throws Exception {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().validarSenhaETrocar(usuario, senha_atual, nova_senha);
	}
	
	public List<Object> buscarUsuario() throws Exception {

		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().buscarUsuario();
	}

	public List<Object> buscarListaColaboradorProfissao(Integer cd_profissao) throws Exception {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().buscarListaColaboradorProfissao(cd_profissao);
	}	
	
	public Pessoa buscarUsuario_Fiscal_pelaPessoa(Pessoa pessoa){
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().buscarUsuario_Fiscal_pelaPessoa(pessoa);
	}
	public Usuario buscarUsuarioPorId(Integer cd_usuario){
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().buscarUsuarioPorId(cd_usuario);
	}

	public List<Usuario_grupo_usuario> buscarUsuarioGrupoUsuario() {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().buscarUsuarioGrupoUsuario();
	}

	public List<Usuario_grupo_usuario> setaNm_grupoPertence(Integer cd_unidade) {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().setaNm_grupoPertence(cd_unidade);
	}

	public Usuario verificaSeUsuarioCurtiu(String id_usuario){
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().verificaSeUsuarioCurtiu(id_usuario);
	}

	public Usuario insereUsuarioFace(String nm_usuario,  String id_usuario){
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().insereUsuarioFace(nm_usuario, id_usuario);
	}

	public Boolean deletaConfiguracoesPesquisa(Integer cd_usuario, Integer cd_programa) throws Exception {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().deletaConfiguracoesPesquisa(cd_usuario, cd_programa);
	}

	public Boolean salvarPermissoesPadrao( Integer cd_usuario, Integer cd_unidade) throws Exception {
		//this.setUsuarioBO(new UsuarioBO());
		return this.getUsuarioBO().salvarPermissoesPadrao( cd_usuario , cd_unidade);
	}

    @CrossOrigin
    @RequestMapping("/profile-info")
    public ProfileInfoResponse getActiveProfiles() {
        return new ProfileInfoResponse(env.getActiveProfiles(), null);
    }

   class ProfileInfoResponse {

        public String[] activeProfiles;
        public String ribbonEnv;

        ProfileInfoResponse(String[] activeProfiles,String ribbonEnv) {
            this.activeProfiles=activeProfiles;
            this.ribbonEnv=ribbonEnv;
        }
    }

   /**
    * GET  /account : get the current user.
    *
    * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
    */
   /*@RequestMapping(value = "/account",
       method = RequestMethod.GET,
       produces = MediaType.APPLICATION_JSON_VALUE)*/
   public ResponseEntity<UserDTO> getAccount(@Valid @RequestBody Object objPersistir) {
		try {
	    	
			Gson gsonDeserializer = new Gson();
	    	LinkedHashMap<String, Object> mpa = (LinkedHashMap) objPersistir;
	    	Object entidade = Class.forName((String) mpa.get("classeJava")).newInstance();
			CrudGenericBO crud = new CrudGenericBO();

	    	Reflection.montarObjetoAbstrato(entidade, (LinkedHashMap<String, Object>) mpa.get("objetoJson"));
	    	//recupera campo por campo com o método get() e imprime cada um
			crud.salvarObjetoCompleto(entidade);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//this.setUsuarioBO(new UsuarioBO());

		return Optional.ofNullable(new User())	  
           .map(user -> new ResponseEntity<>(new UserDTO(this.getUsuarioBO().buscarUsuarioJHipster(((Usuario) objPersistir).getCd_user())), HttpStatus.OK))
           .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
   }

public User getByEmail(String userName) {

	PesquisaPadraoBO p = new PesquisaPadraoBO();
	p.setDao(new GenericDAO());
	User u = null;
	try {
		u = (User) p.getDao().getSession().createCriteria(User.class).add(Restrictions.eq("login", userName))
				.uniqueResult();
	} catch (Exception e) {
		// TODO: handle exception
	}
	return u;
}

   /**
    * GET  /authenticate : check if the user is authenticated, and return its login.
    *
    * @param request the HTTP request
    * @return the login if the user is authenticated
    
   @RequestMapping(value = "/api/authentication",
       method = RequestMethod.GET,
       produces = MediaType.APPLICATION_JSON_VALUE)
   public String isAuthenticated(HttpServletRequest request) {
	   Object o = request.getRemoteUser();
       return request.getRemoteUser();
   }*/
}