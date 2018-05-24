package br.com.lsd.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.primum.bo.crud.CrudGenericBO;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;
import br.com.primum.modelo.usuario.Usuario;
import br.com.lsd.service.usuario.UsuarioService;

/**
 * Servlet implementation class ExecucaoServlet
 */
@RestController
@RequestMapping("mobile")
public class ExecucaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecucaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
		Object obj = null;
        Execucao_formulario retorno = null;
		try {
			obj = (Object) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			CrudGenericBO crud = new CrudGenericBO();
			crud.salvarObjetoCompleto(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @CrossOrigin
    @RequestMapping(value = "/execucaoServlet",
            method = RequestMethod.POST)
    public void buscarAtividades(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {

		ObjectInputStream ois 	= new ObjectInputStream(request.getInputStream());
		Object obj 				= null;
		try {
			obj = (Object) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			CrudGenericBO crud = new CrudGenericBO();
			crud.salvarObjetoCompleto(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}