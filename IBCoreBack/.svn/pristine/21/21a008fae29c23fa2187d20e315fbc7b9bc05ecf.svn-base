//package br.com.opsocial.server.actions.sets;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.http.HttpSession;
//
//import br.com.opsocial.client.entity.UsersDTO;
//import br.com.opsocial.server.entity.Users;
//import br.com.opsocial.server.utils.UtilFunctions;
//import br.com.maxmobile.opsocialejb.das.MaintenanceUserRemote;
//import br.com.uol.pagseguro.domain.AccountCredentials;
//import br.com.uol.pagseguro.domain.Item;
//import br.com.uol.pagseguro.domain.Transaction;
//import br.com.uol.pagseguro.domain.TransactionStatus;
//import br.com.uol.pagseguro.exception.PagSeguroServiceException;
//import br.com.uol.pagseguro.service.TransactionSearchService;
//
//public class PaymentsController {
//	
//	private Timer timer;
//	
//	public PaymentsController(HttpSession session) {	
//		timer = new Timer();
//		timer.schedule(new Task(), 0, 1000 * 60 * 60);
//	}
//	
//    class Task extends TimerTask {
//        
//    	public void run() {
//    		
//    		MaintenanceUserRemote userRemote;
//    		
//			try {
//				userRemote = (MaintenanceUserRemote) 
//						new InitialContext().lookup("br.com.maxmobile.opsocialejb.das.MaintenanceUserRemote");
//				
//				List<Users> users = userRemote.getAll();
//			
//				for(Users user : users) {
//					if(user.getPaymentStatus().equals(Users.FALSE)) {
//						String arrayTransactions[] = user.getTransactions().split(";");
//						
//						for(String transactionCode : arrayTransactions) {
//				    		  
//					        Transaction transaction = null;  
//					        try {  
//					            // Realiza a busca  
//					            transaction = TransactionSearchService.searchByCode(  
//					                new AccountCredentials(
//					                		"sam@grupomaxima.net", UtilFunctions.PAG_SEGURO_TOKEN),
//					                    transactionCode  
//					                );  
//					        } catch (PagSeguroServiceException e) {  
//					            System.err.println(e.toString());  
//					        }  
//					  
//					        if (transaction != null) {  
//					            if(transaction.getStatus().equals(TransactionStatus.PAID)) {
//					            	
//					            	Integer months = 0;
//					            	
//					            	for (int i = 0; i < transaction.getItems().size(); i++) {  
//					            		Item item = (Item) transaction.getItems().get(i);
//					            		
//					            		if(user.getPlanType().equals(UsersDTO.OPLAY)) {
//					            			months = item.getAmount().intValue() / UsersDTO.OPLAY_PRICE;
//					    				} else if(user.getPlanType().equals(UsersDTO.OPOWER)) {
//					    					months = item.getAmount().intValue() / UsersDTO.OPOWER_PRICE;
//					    				} else if(user.getPlanType().equals(UsersDTO.OPRIME)) {
//					    					months = item.getAmount().intValue() / UsersDTO.OPRIME_PRICE;
//					    				}
//					                }
//	//				        		
//					        		Date expireDate = new Date();
//					        		expireDate.setDate(expireDate.getDate() + 31*months);
//					        		user.setExpireDate(expireDate);
//					        		
//					        		user.setTransactions(null);
//					        		user.setExpireDate(null);
//					        		user.setPaymentStatus(Users.TRUE);
//					            }
//					        }
//						}
//	
//					}
//				}	
//			} catch (NamingException e1) {
//				e1.printStackTrace();
//			}
//       	}
//    }
//}
// 
