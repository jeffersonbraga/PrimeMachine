package br.com.lsd.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.lsd.bo.sincronizar.GeradorHtml;
import br.com.lsd.bo.sincronizar.SincronizadorBO;

/**
 * Servlet implementation class AndroidServlet
 */
public class HTMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HTMLServlet() {
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

		GeradorHtml s = new GeradorHtml();
		//String b = s.criarBaseSQLite("public");
/*
	    File file = new File(ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.db");
//	    File file2 = new File(ContextoAplicacao.CAMINHO_APLICACAO + "\\Mobile\\base_primum.txt");
	    byte[] b = new byte[(int) file.length()];
        String retorno = "";
        
	    try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            retorno = new String(b, "UTF-8");
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
*/
		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		//oos.writeObject(b);
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
