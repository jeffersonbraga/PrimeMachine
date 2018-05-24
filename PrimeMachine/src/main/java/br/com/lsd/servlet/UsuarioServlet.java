package br.com.lsd.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.ferramentas.ObjetoVirtual;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.modelo.usuario.Usuario;
import br.com.primum.reflection.Reflection;
import br.com.primum.service.crud.DBSearchService;
import br.com.lsd.bo.pesquisa.PesquisaBO;
import br.com.lsd.service.formulario.FormularioService;
import br.com.lsd.service.usuario.UsuarioService;
import br.com.lsd.util.HeaderUtil;

/**
 * Servlet implementation class AndroidServlet
 */
@RestController
@RequestMapping("mobile")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
		Object obj = null;
        Usuario retorno = null;
		try {
			obj = (Object) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			UsuarioService dbs = new UsuarioService();
			retorno = dbs.efetuarLogin((Usuario) obj);
			if (retorno != null) {
				retorno.setListaPermissao_menu_indicadores(null);
				retorno.setListaConfig_pesquisa(null);
				retorno.setListaGrupoPermissao(null);
				retorno.setListaPermissao_acesso(null);
				retorno.setListaUnidades_acesso(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		oos.writeObject(retorno);
		oos.close();
	}

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/binary");

//        try {
//            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
//            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//
//            ObTeste obTeste = (ObTeste) ois.readObject();
//            obTeste.setTeste("respondendo");
//
//            oos.writeObject(obTeste);
//
//            ois.close();
//            oos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/usuarioServlet",
        method = RequestMethod.POST)
    public void salvarObjeto(HttpServletRequest request, HttpServletResponse response) {
        Usuario retorno = null;
		Gson gsonDeserializer = new Gson();
		try {

			ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			Object obj = null;
			try {
				obj = (Object) ois.readObject();
				retorno = (Usuario) gsonDeserializer.fromJson(obj.toString(), Usuario.class);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				UsuarioService dbs = new UsuarioService();
				retorno = dbs.efetuarLogin(retorno);
				if (retorno != null) {
					retorno.setListaPermissao_menu_indicadores(null);
					retorno.setListaConfig_pesquisa(null);
					retorno.setListaGrupoPermissao(null);
					retorno.setListaPermissao_acesso(null);
					retorno.setListaUnidades_acesso(null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(retorno);
			oos.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//return new ResponseEntity<>(gsonDeserializer.toJson(retorno), HttpStatus.OK);
    }
}
