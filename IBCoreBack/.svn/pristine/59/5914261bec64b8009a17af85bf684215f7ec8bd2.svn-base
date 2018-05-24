//package br.com.opsocial.server.services;
//
//import java.io.IOException;
//import java.util.Date;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import br.com.opsocial.server.entity.Users;
//import br.com.opsocial.server.utils.UtilFunctions;
//import br.com.maxmobile.opsocialejb.das.MaintenanceUserRemote;
//import br.com.uol.pagseguro.domain.AccountCredentials;
//import br.com.uol.pagseguro.domain.Item;
//import br.com.uol.pagseguro.domain.Transaction;
//import br.com.uol.pagseguro.exception.PagSeguroServiceException;
//import br.com.uol.pagseguro.service.TransactionSearchService;
//
//public class PaymentServlet extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		
//		//----- System.out.println("chegou no payment servlet");
//		
//		String transactionId = req.getParameter("transaction_id");
//		
//		Transaction transaction = null;  
//		
//		try {  
//		    // Realiza a busca  
//		    transaction = TransactionSearchService.searchByCode(  
//		        new AccountCredentials(
//		    		"sam@grupomaxima.net", UtilFunctions.PAG_SEGURO_TOKEN),
//		    		transactionId  
//		        );  
//		
//		
//		for (int i = 0; i < transaction.getItems().size(); i++) {  
//    		Item item = (Item) transaction.getItems().get(i);
//    		
//
//    		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) 
//					new InitialContext().lookup("br.com.maxmobile.opsocialejb.das.MaintenanceUserRemote");
//			
//			Users user = userRemote.getById(Long.valueOf(item.getId()));
//			
//			if(user.getTransactions() == null) {
//				user.setTransactions(transactionId + ";");
//			} else {
//				user.setTransactions(user.getTransactions() + transactionId + ";");
//			}
//			
//			user.setPaymentStatus(Users.FALSE);
//			
//			Date expireDate = new Date();
//    		expireDate.setDate(expireDate.getDate() + 2);
//    		user.setTranscationExpire(expireDate);
//			
//			userRemote.save(user);
//			
//			resp.sendRedirect("www.opsocial.com.br");	
//		}
//			
//	        
//		} catch (PagSeguroServiceException e) {  
//		    System.err.println(e.toString());  
//		} catch (NamingException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		super.doPost(req, resp);
//	}
//
//}
