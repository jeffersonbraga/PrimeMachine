package br.com.lsd.servlet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import br.com.primum.ferramentas.ObjetoVirtual;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.resposta_exec_formulario.Resposta_exec_formulario;
import br.com.primum.service.crud.DBSearchService;
import br.com.lsd.service.formulario.FormularioService;

/**
 * Servlet implementation class AndroidServlet
 */
public class DbSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbSearchServlet() {
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
        ObjetoVirtual obj = new ObjetoVirtual();
		try {
			obj = (ObjetoVirtual) ois.readObject();
			System.out.println("teste");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		DBSearchService dbs = new DBSearchService();
		List<Resposta_exec_formulario> lst = null;
		try {
			lst = dbs.buscarDadosSpinner(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		oos.writeObject(lst);
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
}
