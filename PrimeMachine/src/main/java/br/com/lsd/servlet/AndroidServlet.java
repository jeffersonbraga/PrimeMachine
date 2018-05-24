package br.com.lsd.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.lsd.service.formulario.FormularioService;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.formulario.Formulario;
import br.com.primum.modelo.programa_qualidade.Programa_qualidade;
import br.com.primum.modelo.usuario.Usuario;

/**
 * Servlet implementation class AndroidServlet
 */
@RestController
@RequestMapping("mobile")
public class AndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @CrossOrigin
    @RequestMapping(value = "/getAtividades",
            method = RequestMethod.POST)
    public void buscarAtividades(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException {

    	try {

			ObjectInputStream ois 	= new ObjectInputStream(request.getInputStream());
			Usuario obj 				= null;
			Gson gsonDeserializer = new Gson();
			try {
				obj = (Usuario) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FormularioService frmService = new FormularioService();
			List<Object> lst = frmService.buscarCronogramaAtividadesMobile(obj);
			List<Object> retorno = new ArrayList<Object>();

			for (Object o : lst) {

				if (o instanceof ArrayList) {

					for (Object oAux : (ArrayList<Object>) o) {

						if (oAux instanceof Formulario) {
							 try {
								 retorno.add(oAux);
								} catch (Exception e) {
									//TODO
						        }
						} else if (oAux instanceof Escalonamento_usuario) {
							try {

								retorno.add(oAux);
							} catch (Exception e) {
								//TODO
							}					
						} 
					}
				} else if (o instanceof Programa_qualidade) {
					try {

						retorno.add(o);
					} catch (Exception e) {
						//TODO
					}
				}
			}

			ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
			oos.writeObject(retorno);
			oos.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
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

		Map<String, String> example = new HashMap<String, String>(); 

		ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
		Usuario obj = null;
		try {
			obj = (Usuario) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FormularioService frmService = new FormularioService();
		List<Object> lst = frmService.buscarCronogramaAtividadesMobile(obj);
		List<Object> retorno = new ArrayList<Object>();

		for (Object o : lst) {

			if (o instanceof ArrayList) {

				for (Object oAux : (ArrayList<Object>) o) {

					if (oAux instanceof Formulario) {
						 try {

							 retorno.add(oAux);
							 //example.put( ("Formulario" + ((Formulario) oAux).getCd_formulario()), new String(((Formulario) oAux).getNm_formulario()));
					        } catch (Exception e) {
					            //e.printStackTrace();
					        }
					} else if (oAux instanceof Escalonamento_usuario) {
						try {
							retorno.add(oAux);
							/*
							GregorianCalendar cal = new GregorianCalendar();
							cal.setTime(((Escalonamento_usuario) oAux).getHr_execucao());
							String horario = (cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE));
							
							example.put( ("Escalonamento_usuario" + ((Escalonamento_usuario) oAux).getCd_escalonamento_usuario()), new String(((Escalonamento_usuario) oAux).getEscalonamento_formulario().getFormulario().getNm_formulario() + " - " + horario));
							*/
						} catch (Exception e) {
							//e.printStackTrace();
						}					
					} 
				}
			} else if (o instanceof Programa_qualidade) {
				try {

					retorno.add(o);
					 //example.put( ("Programa_qualidade" + ((Programa_qualidade) o).getCd_programa_qualidade()) , new String(((Programa_qualidade) o).getNm_programa_qualidade()));
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
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
}
